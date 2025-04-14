package auction.bidderImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the state of a bidder in an auction, including the cash available, the quantity gained,
 * and the history of bids made.
 */
public class AuctionState {
    private int cash; // Remaining cash available for bidding
    private int gainedQuantity; // Quantity gained from the auction
    private List<Integer> bidHistory; // List to store the history of bids

    public AuctionState(int cash) {
        this.cash = cash;
        this.gainedQuantity = 0;
        this.bidHistory = new ArrayList<>();
    }
    public void update(int ownBid, int otherBid) {
        this.cash -= ownBid;
        this.bidHistory.add(ownBid);

        if (ownBid > otherBid) {
            this.gainedQuantity = this.gainedQuantity + 2;
        }else if (otherBid == ownBid) {
            this.gainedQuantity = this.gainedQuantity + 1;
        }
    }
    public int getCash() {
        return cash;
    }
    public void setCash(int cash) {
        this.cash = cash;
    }
    public int getGainedQuantity() {
        return gainedQuantity;
    }
    public void setGainedQuantity(int gainedQuantity) {
        this.gainedQuantity = gainedQuantity;
    }
    public List<Integer> getBidHistory() {
        return bidHistory;
    }
    public void setBidHistory(List<Integer> bidHistory) {
        this.bidHistory = bidHistory;
    }

}
