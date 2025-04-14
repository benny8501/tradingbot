package auction.bidderImpl;

import auction.Bidder;

public class RandomBidder implements Bidder {
    private int remainingQuantity;
    private AuctionState ownState;
    private AuctionState opponentsState;

    public RandomBidder(int quantity, int cash) {
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
        if (remainingQuantity <= 0) {
            throw new IllegalStateException("Not enough remaining quantity to bid.");
        }
        return (int) (Math.random() * ownState.getCash());
    }

    @Override
    public void bids(int own, int other) {
        this.ownState.update(own, other);
        this.opponentsState.update(other, own);
        this.remainingQuantity = this.remainingQuantity - 2;
    }
}
