package auction;

import auction.*;
import auction.bidderImpl.MyBidder;
import auction.bidderImpl.NashBidder;
import auction.bidderImpl.RandomBidder;

public class Main {
    public static void main(String[] args) {
        int quantity = 21;
        int cash = 200;
        Bidder myBidder = new MyBidder(quantity, cash);
        Bidder nashBidder = new NashBidder(quantity, cash);
        Bidder randomBidder = new RandomBidder(quantity, cash);
        Bidder myBidder2 = new MyBidder(quantity, cash);

        Auction auction = new Auction(cash, quantity, myBidder, randomBidder);

        auction.run();
    }
}
