package auction;

import java.util.ArrayList;
import java.util.List;

public class Auction {
    private int cash;
    private int remainingQuantity;
    private Bidder bidder1;
    private Bidder bidder2;
    private int gainedQuantityBidder1;
    private int gainedQuantityBidder2;
    private int cashBidder1;
    private int cashBidder2;

    private List<Integer> bidHistoryBidder1;
    private List<Integer> bidHistoryBidder2;

    public Auction (int cash, int quantity, Bidder bidder1, Bidder bidder2) {
        if (cash <= 0 || quantity <= 0) {
            throw new IllegalArgumentException("Cash and quantity must be positive.");
        }
        this.cash = cash;
        this.remainingQuantity = quantity;
        this.cashBidder1 = cash;
        this.cashBidder2 = cash;
        this.bidder1 = bidder1;
        this.bidder2 = bidder2;
        this.bidHistoryBidder1 = new ArrayList<>();
        this.bidHistoryBidder2 = new ArrayList<>();
    }

    public void run() {
        while(remainingQuantity > 1) {
            System.out.println();
            System.out.println("Gained Quantity Bidder 1: " + gainedQuantityBidder1);
            System.out.println("Gained Quantity Bidder 2: " + gainedQuantityBidder2);
            System.out.println("Remaining Quantity: " + remainingQuantity);

            int bid1 = bidder1.placeBid();
            int bid2 = bidder2.placeBid();
            System.out.println("Bidder 1 bids " + bid1 + ", Bidder 2 bids " + bid2);

            evaluateBids(bid1, bid2);
            bidder1.bids(bid1, bid2);
            bidder2.bids(bid2, bid1);
        }
        printResult();
    }
    private void evaluateBids(int bid1, int bid2){
        cashBidder1 -= bid1;
        cashBidder2 -= bid2;
        if (cashBidder1 < 0 || cashBidder2 < 0) {
            throw new IllegalArgumentException("Bidder1 or Bidder2 has not enough MU to place his bid.");
        }
        if (bid1 > bid2) {
            gainedQuantityBidder1 += 2;
            System.out.println("Bidder 1 wins the round.");
        } else if (bid2 > bid1) {
            gainedQuantityBidder2 += 2;
            System.out.println("Bidder 2 wins the round.");
        } else {
            gainedQuantityBidder1++;
            gainedQuantityBidder2++;
            System.out.println("It's a tie. Both bidders win 1 quantity.");
        }
        remainingQuantity -= 2;
        bidHistoryBidder1.add(bid1);
        bidHistoryBidder2.add(bid2);
    }
    private void printResult() {
        System.out.println("Remaining Quantity: " + getRemainingQuantity());
        System.out.println("Bid History Bidder 1: " + bidHistoryBidder1);
        System.out.println("Bid History Bidder 2: " + bidHistoryBidder2);
        System.out.println("Final Result Bidder 1 " +
                "\nGained Quantity Bidder 1: " + gainedQuantityBidder1 +
                "\nCash Bidder 1: " + cashBidder1) ;
        System.out.println("Final Result Bidder 2 " +
                "\nGained Quantity Bidder 2: " + gainedQuantityBidder2 +
                "\nCash Bidder 2: " + cashBidder2) ;
        if (gainedQuantityBidder1 > gainedQuantityBidder2) {
            System.out.println("Bidder 1 wins the auction!");
        } else if (gainedQuantityBidder2 > gainedQuantityBidder1) {
            System.out.println("Bidder 2 wins the auction!");
        } else {
            if (cashBidder1 > cashBidder2) {
                System.out.println("Bidder 1 wins the auction!");
            } else if (cashBidder2 > cashBidder1) {
                System.out.println("Bidder 2 wins the auction!");
            }
            System.out.println("It's a tie!");
        }
    }
    public int getRemainingQuantity() {
        return remainingQuantity;
    }
}
