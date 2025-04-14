# tradingbot
This project simulates an auction environment where multiple bidders compete to acquire a fixed quantity of items using a limited amount of cash. The primary focus of this project is the MyBidder class, which implements a custom bidding strategy.

## Overview
This project simulates an auction environment where multiple bidders compete to acquire a fixed quantity of items using a limited amount of cash. The project is implemented in Java and uses Maven for dependency management. It includes several bidder implementations, including a custom bidder (`MyBidder`), a Nash equilibrium-based bidder (`NashBidder`), and a random bidder (`RandomBidder`).

The primary focus of this documentation is the `MyBidder` class, which implements a custom bidding strategy.

---

## Features
- **Auction Simulation**: Simulates an auction where bidders compete for items.
- **Custom Bidding Strategies**: Includes multiple bidder implementations with unique strategies.
- **Extensibility**: Easily add new bidder implementations by adhering to the `Bidder` interface.

---

## MyBidder Class

### Purpose
The `MyBidder` class is a custom implementation of the `Bidder` interface. It uses a strategic approach to maximize the bidder's gains while considering the opponent's behavior and the auction's state.

### Key Features
- Implements a **dynamic bidding strategy** based on the current round, remaining quantity, and cash.
- Handles **edge cases** such as the first and last rounds, and scenarios where the opponent has more cash.
- Provides **state management** for the bidder's cash, gained quantity, and bid history.

### Constructor
```java
public MyBidder(int quantity, int cash)
```
- **Parameters**:
  - `quantity`: The total quantity of items available in the auction.
  - `cash`: The total cash available to the bidder.
- **Throws**: `IllegalArgumentException` if `quantity` or `cash` is non-positive.

### Methods

#### `init(int quantity, int cash)`
Initializes the bidder with the given quantity and cash.
- **Parameters**:
  - `quantity`: The total quantity of items available in the auction.
  - `cash`: The total cash available to the bidder.
- **Throws**: `IllegalArgumentException` if `quantity` or `cash` is non-positive.

#### `placeBid()`
Determines the bid amount for the current auction round based on the game state and opponent analysis.
- **Returns**: The bid amount (an integer between 0 and the bidder's remaining cash).
- **Throws**: `IllegalStateException` if there is no remaining quantity to bid on.

#### `bids(int own, int other)`
Updates the state of the bidder after both bidders have placed their bids.
- **Parameters**:
  - `own`: The bid placed by this bidder.
  - `other`: The bid placed by the opponent.
- **Throws**: `IllegalArgumentException` if `own` or `other` is negative.

#### State Management Methods
- `setRemainingQuantity(int remainingQuantity)`
- `setOwnCash(int cash)`
- `setOpponentsCash(int cash)`
- `setOwnGainedQuantity(int gainedQuantity)`
- `setOpponentsGainedQuantity(int gainedQuantity)`
- `setOwnBidHistory(List<Integer> bidHistory)`
- `setOpponentsBidHistory(List<Integer> bidHistory)`
- `getOwnCash()`
- `getOpponentsCash()`
- `getOwnGainedQuantity()`
- `getOpponentsGainedQuantity()`
- `getRemainingQuantity()`

These methods allow for fine-grained control and retrieval of the bidder's state.

---

### Bidding Strategy
The `MyBidder` class employs a strategic approach to bidding:
1. **First Round**: Bids proportionally to the total cash divided by the expected number of rounds.
2. **Last Round**: Bids aggressively to outbid the opponent by 1 monetary unit (MU), capped by the bidder's remaining cash.
3. **Opponent Cash Deficit**: Bids 0 to preserve cash if the opponent has more remaining cash.
4. **Default Logic**: Bids the minimum of:
   - The bidder's remaining cash.
   - The opponent's remaining cash + 1 (to outbid).
   - The opponent's last bid + 1 (to counter previous behavior).

---

## How to Run
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd tradingbot
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the simulation:
   ```bash
   java -cp target/tradingbot-1.0-SNAPSHOT.jar org.example.Main
   ```

---

## Testing
The project includes a comprehensive test suite for the `MyBidder` class. To run the tests:
```bash
mvn test
```

---

## Contributing
Contributions are welcome! To add a new bidder:
1. Implement the `Bidder` interface.
2. Add the new bidder to the `Main` class for simulation.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.
