package net.jacobpeterson.trading.backtest;

import io.github.mainstringargs.alpaca.domain.Order;
import io.github.mainstringargs.alpaca.domain.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used as the backtesting portfolio. It contains a list of orders, positions, equity, buying power, etc.
 * The stuff you usually might see in an investment portfolio.
 */
public class BacktestPortfolio {

    private int buyingPower;
    private int equity;
    private List<Order> orders;
    private List<Position> positions;
    private long lastUpdatedTimestamp;

    /**
     * Instantiates a new Backtest portfolio.
     *
     * @param buyingPower the buying power
     */
    public BacktestPortfolio(int buyingPower) {
        this.buyingPower = buyingPower;
        this.equity = 0;
        this.orders = new ArrayList<>();
        this.positions = new ArrayList<>();
    }

    /**
     * Gets buying power.
     *
     * @return the buying power
     */
    public int getBuyingPower() {
        return buyingPower;
    }

    /**
     * Sets buying power.
     *
     * @param buyingPower the buying power
     */
    public void setBuyingPower(int buyingPower) {
        this.buyingPower = buyingPower;
    }

    /**
     * Gets equity.
     *
     * @return the equity
     */
    public int getEquity() {
        return equity;
    }

    /**
     * Sets equity.
     *
     * @param equity the equity
     */
    public void setEquity(int equity) {
        this.equity = equity;
    }

    /**
     * Gets orders.
     *
     * @return the orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Sets orders.
     *
     * @param orders the orders
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * Gets positions.
     *
     * @return the positions
     */
    public List<Position> getPositions() {
        return positions;
    }

    /**
     * Sets positions.
     *
     * @param positions the positions
     */
    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    /**
     * Gets last updated timestamp.
     *
     * @return the last updated timestamp
     */
    public long getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    /**
     * Sets last updated timestamp.
     *
     * @param lastUpdatedTimestamp the last updated timestamp
     */
    public void setLastUpdatedTimestamp(long lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }
}
