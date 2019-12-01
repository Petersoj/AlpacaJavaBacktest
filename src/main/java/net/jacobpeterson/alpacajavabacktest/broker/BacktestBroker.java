package net.jacobpeterson.alpacajavabacktest.broker;

import io.github.mainstringargs.alpaca.enums.OrderSide;
import io.github.mainstringargs.alpaca.enums.OrderTimeInForce;
import io.github.mainstringargs.alpaca.enums.OrderType;
import io.github.mainstringargs.domain.alpaca.account.Account;
import io.github.mainstringargs.domain.alpaca.order.Order;
import io.github.mainstringargs.domain.alpaca.position.Position;
import net.jacobpeterson.alpacajavabacktest.data.BacktestData;

import java.util.ArrayList;

/**
 * This is used as a simulated broker to submit orders, get account data, get orders, get positions, etc. The stuff you
 * usually might see in an investment portfolio. These orders are compared against bid/ask prices as well as traded
 * prices of an equity. This ensures that the backtesting is most accurate to what would happen in a live market.
 */
public class BacktestBroker {

    private final BacktestData backtestData;
    private Account account;
    private ArrayList<Order> orders;
    private ArrayList<Position> positions;

    /**
     * Instantiates a new Backtest broker with the following defaults:
     * <ul>
     *     <li>Equity: $100,000</li>
     *     <li>Buying Power Multiplier: 4</li>
     * </ul>
     *
     * @param backtestData the backtest data
     */
    public BacktestBroker(BacktestData backtestData) {
        this(backtestData, createDefaultAccount(100_000, 4));
    }

    /**
     * Instantiates a new Backtest portfolio.
     *
     * @param backtestData the backtest data
     * @param account      the account
     */
    public BacktestBroker(BacktestData backtestData, Account account) {
        this.backtestData = backtestData;
        this.account = account;
        this.orders = new ArrayList<>();
        this.positions = new ArrayList<>();
    }

    /**
     * Creates a default account.
     *
     * @param equity                the equity
     * @param buyingPowerMultiplier the buying power multiplier
     *
     * @return the account
     */
    public static Account createDefaultAccount(double equity, double buyingPowerMultiplier) {
        Account account = new Account();
        account.setEquity(String.valueOf(equity));
        account.setMultiplier(String.valueOf(buyingPowerMultiplier));
        account.setBuyingPower(String.valueOf(equity * buyingPowerMultiplier));
        return account;
    }

    /**
     * Submit an order.
     *
     * @param ticker           the ticker
     * @param quantity         the quantity
     * @param orderSide        the order side
     * @param orderType        the order type
     * @param orderTimeInForce the order time in force
     * @param limitPrice       the limit price
     * @param stopPrice        the stop price
     */
    public void submitOrder(String ticker, Integer quantity, OrderSide orderSide, OrderType orderType,
            OrderTimeInForce orderTimeInForce, Double limitPrice, Double stopPrice) {
        // TODO
    }

    /**
     * Cancel an order.
     *
     * @param order the order
     */
    public void cancelOrder(Order order) {
        // TODO
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets account.
     *
     * @param account the account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Gets orders.
     *
     * @return the orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Sets orders.
     *
     * @param orders the orders
     */
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /**
     * Gets positions.
     *
     * @return the positions
     */
    public ArrayList<Position> getPositions() {
        return positions;
    }

    /**
     * Sets positions.
     *
     * @param positions the positions
     */
    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }
}
