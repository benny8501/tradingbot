package auction.bidderImpl;

import auction.Bidder;

import java.util.List;

public class MyBidder implements Bidder {
    private int remainingQuantity;
    private AuctionState ownState;
    private AuctionState opponentsState;

    public MyBidder(int quantity, int cash) {
        init(quantity, cash);
    }

    /**
     * Initializes the bidder with the given quantity and cash.
     * @param quantity the quantity
     * @param cash     the cash limit
     */
    @Override
    public void init(int quantity, int cash) {
        if (quantity <= 0 || cash <= 0) {
            throw new IllegalArgumentException("Quantity and cash must be positive.");
        }
        this.remainingQuantity = quantity;
        this.ownState = new AuctionState(cash);
        this.opponentsState = new AuctionState(cash);
    }

    /**
     * Determines the bid amount for the current auction round based on game state and opponent analysis.
     *
     * <p><b>Bidding Strategy:</b>
     * <ul>
     *   <li><b>Default Logic:</b> Bids the minimum of:
     *     <ol>
     *       <li>Own remaining cash</li>
     *       <li>Opponent's remaining cash + 1 (to outbid)</li>
     *       <li>Opponent's last bid + 1 (to counter previous behavior)</li>
     *     </ol>
     *   </li>
     *   <li><b>Final Round:</b> Bids aggressively to outbid the opponent by 1 MU, capped by own cash.</li>
     *   <li><b>First Round:</b> Bids proportionally to total cash divided by total expected rounds.</li>
     *   <li><b>Cash Deficit:</b> Bids 0 to preserve cash if opponent has more remaining cash.</li>
     * </ul>
     *
     * @return the bid amount between 0 and the bidder's remaining cash (inclusive)
     * @throws IllegalStateException if called when no quantity remains to auction
     */
    @Override
    public int placeBid() {
        if (remainingQuantity <= 0) {
            throw new IllegalStateException("Not enough remaining quantity to bid.");
        }
        if (isLastRound()) {
            return calculateLastRoundBid();
        }
        if (isFirstRound()) {
            return calculateFirstRoundBid();
        }
        if (opponentHasMoreCash()) {
            return 0; // Save cash for later rounds
        }
        return calculateDefaultBid();
    }
    private boolean isLastRound() {
        return getRoundsLeft() <= 1;
    }
    private boolean isFirstRound() {
        return getRoundsPlayed() == 0;
    }
    private boolean opponentHasMoreCash() {
        return ownState.getCash() < opponentsState.getCash();
    }
    private int calculateLastRoundBid() {
        return Math.max(0, Math.min(ownState.getCash(), opponentsState.getCash() + 1));
    }
    private int calculateFirstRoundBid() {
        return Math.max(0, ownState.getCash() / getRoundsLeft());
    }
    private int calculateDefaultBid() {
        return Math.max(
            0,
            Math.min(
                ownState.getCash(),
                Math.min(opponentsState.getCash() + 1, getOpponentsLastBid() + 1)
            )
        );
    }
    private int getRoundsPlayed() {
        if (ownState.getBidHistory().size() != opponentsState.getBidHistory().size()) {
            throw new IllegalStateException("Bid history sizes do not match.");
        }
        return ownState.getBidHistory().size();
    }
    private int getRoundsLeft() {
        return remainingQuantity / 2;
    }
    private int getOpponentsLastBid() {
        List<Integer> opponentsBidHistory = opponentsState.getBidHistory();
        return opponentsBidHistory.isEmpty() ? 0 : opponentsBidHistory.get(opponentsBidHistory.size() - 1);
    }

    /**
     * Updates the state of the bidder after both bidders have placed their bids.
     * @param own   the bid of this bidder
     * @param other the bid of the other bidder
     */
    @Override
    public void bids(int own, int other) {
        if (own < 0 || other < 0) {
            throw new IllegalArgumentException("Bids must be non-negative.");
        }
        this.ownState.update(own, other);
        this.opponentsState.update(other, own);
        this.remainingQuantity = this.remainingQuantity - 2;
    }
    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
    public void setOwnCash(int cash) {
        this.ownState.setCash(cash);
    }
    public void setOpponentsCash(int cash) {
        this.opponentsState.setCash(cash);
    }
    public void setOwnGainedQuantity(int gainedQuantity) {
        this.ownState.setGainedQuantity(gainedQuantity);
    }
    public void setOpponentsGainedQuantity(int gainedQuantity) {
        this.opponentsState.setGainedQuantity(gainedQuantity);
    }
    public void setOwnBidHistory(List<Integer> bidHistory) {
        this.ownState.setBidHistory(bidHistory);
    }
    public void setOpponentsBidHistory(List<Integer> bidHistory) {
        this.opponentsState.setBidHistory(bidHistory);
    }
    public int getOwnCash() {
        return ownState.getCash();
    }
    public int getOpponentsCash() {
        return opponentsState.getCash();
    }
    public int getOwnGainedQuantity() {
        return ownState.getGainedQuantity();
    }
    public int getOpponentsGainedQuantity() {
        return opponentsState.getGainedQuantity();
    }
    public int getRemainingQuantity() {
        return remainingQuantity;
    }

}
