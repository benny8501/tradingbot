package auction;


import auction.bidderImpl.MyBidder;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;


public class MyBidderTest {
    private MyBidder myBidder;
    @Test
    public void testInstantiateBidderWithZeroQuantity() {
        try {
            myBidder = new MyBidder(0, 100);
        } catch (IllegalArgumentException e) {
            assertEquals("Quantity and cash must be positive.", e.getMessage());
        }
    }
    @Test
    public void testInstantiateBidderWithNegativeQuantity() {
        try {
            myBidder = new MyBidder(-1, 100);
        } catch (IllegalArgumentException e) {
            assertEquals("Quantity and cash must be positive.", e.getMessage());
        }
    }
    @Test
    public void testInstantiateBidderWithZeroCash() {
        try {
            myBidder = new MyBidder(10, 0);
        } catch (IllegalArgumentException e) {
            assertEquals("Quantity and cash must be positive.", e.getMessage());
        }
    }
    @Test
    public void testInstantiateBidderWithNegativeCash() {
        try {
            myBidder = new MyBidder(10, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Quantity and cash must be positive.", e.getMessage());
        }
    }
    @Test
    public void testPlaceBid() {
        myBidder = new MyBidder(10, 100);
        int bid = myBidder.placeBid();
        assert(bid >= 0 && bid <= 100);
    }
    @Test
    public void testPlaceBidWithZeroCash() {
        myBidder = new MyBidder(10, 100);
        myBidder.setOwnCash(0);
        assertEquals(0, myBidder.placeBid());
    }
    @Test
    public void testPlaceBidWithNegativeCash() {
        myBidder = new MyBidder(10, 100);
        myBidder.setOwnCash(-1);
        assertEquals(0, myBidder.placeBid());
    }
    @Test
    public void testPlaceBidWithZeroQuantity() {
        myBidder = new MyBidder(10, 100);
        myBidder.setRemainingQuantity(0);
        try {
            myBidder.placeBid();
        } catch (IllegalStateException e) {
            assertEquals("Not enough remaining quantity to bid.", e.getMessage());
        }
    }
    @Test
    public void testPlaceBidLastRoundMoreCashThanOpponent() {
        myBidder = new MyBidder(10, 100);
        myBidder.setOwnBidHistory(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        myBidder.setOpponentsBidHistory(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        myBidder.setOwnCash(100);
        myBidder.setOpponentsCash(0);
        assertEquals(1, myBidder.placeBid());
    }
    @Test
    public void testPlaceBidLastRoundLessCashThanOpponent() {
        myBidder = new MyBidder(10, 100);
        myBidder.setOwnBidHistory(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        myBidder.setOpponentsBidHistory(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        myBidder.setRemainingQuantity(2);
        myBidder.setOwnCash(10);
        myBidder.setOpponentsCash(100);
        assertEquals(10, myBidder.placeBid());
    }
    @Test
    public void testPlaceBidFirstRound() {
        myBidder = new MyBidder(10, 100);
        assertEquals(20, myBidder.placeBid());
    }
    @Test
    public void testPlaceBidWhenOpponentHasMoreCash() {
        myBidder = new MyBidder(10, 100);
        myBidder.setOwnBidHistory(Arrays.asList(1,2));
        myBidder.setOpponentsBidHistory(Arrays.asList(1, 2));
        myBidder.setOpponentsCash(101);
        assertEquals(0, myBidder.placeBid());
    }

    @Test
    public void testBidsMethod() {
        myBidder = new MyBidder(10, 100);
        myBidder.bids(10, 15);
        assertEquals(90, myBidder.getOwnCash());
        assertEquals(85, myBidder.getOpponentsCash());
        assertEquals(0, myBidder.getOwnGainedQuantity());
        assertEquals(2, myBidder.getOpponentsGainedQuantity());
        assertEquals(8, myBidder.getRemainingQuantity());
    }
    @Test
    public void testBidsMethodTie() {
        myBidder = new MyBidder(10, 100);
        myBidder.bids(1, 1);
        assertEquals(99, myBidder.getOwnCash());
        assertEquals(99, myBidder.getOpponentsCash());
        assertEquals(1, myBidder.getOwnGainedQuantity());
        assertEquals(1, myBidder.getOpponentsGainedQuantity());
        assertEquals(8, myBidder.getRemainingQuantity());
    }
    @Test
    public void testBidsWithInvalidInput() {
        myBidder = new MyBidder(10, 100);
        try {
            myBidder.bids(-10, 15);
        } catch (IllegalArgumentException e) {
            assertEquals("Bids must be non-negative.", e.getMessage());
        }
        try {
            myBidder.bids(10, -15);
        } catch (IllegalArgumentException e) {
            assertEquals("Bids must be non-negative.", e.getMessage());
        }
    }
}
