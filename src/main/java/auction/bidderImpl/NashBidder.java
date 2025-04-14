package auction.bidderImpl;

import auction.Bidder;

public class NashBidder implements Bidder {
    private int remainingQuantity;
    private AuctionState ownState;
    private AuctionState opponentsState;

    public NashBidder(int quantity, int cash) {
        init(quantity, cash);
    }

    @Override
    public void init(int quantity, int cash) {
        if (quantity <= 0 || cash <= 0) {
            throw new IllegalArgumentException("Quantity and cash must be positive.");
        }
        this.remainingQuantity = quantity;
        this.ownState = new AuctionState(cash);
        this.opponentsState = new AuctionState(cash);
    }

    @Override
    public int placeBid() {
        int remainingRounds = remainingQuantity / 2;
        if (remainingQuantity <= 1) {
            throw new IllegalStateException("Not enough remaining quantity to bid.");
        }
        return Math.max(0, ownState.getCash() / remainingRounds);
    }

    @Override
    public void bids(int own, int other) {
        this.ownState.update(own, other);
        this.opponentsState.update(other, own);
        this.remainingQuantity = this.remainingQuantity - 2;
    }
}
