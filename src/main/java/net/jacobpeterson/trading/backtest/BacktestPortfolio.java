package net.jacobpeterson.trading.backtest;

import io.github.mainstringargs.alpaca.domain.Order;
import io.github.mainstringargs.alpaca.domain.Position;

import java.util.ArrayList;
import java.util.List;

public class BacktestPortfolio {

    private int buyingPower;
    private int equity;
    private List<Order> orders;
    private List<Position> positions;
    private long lastUpdatedTimestamp;

    public BacktestPortfolio(int buyingPower) {
        this.buyingPower = buyingPower;
        this.equity = 0;
        this.orders = new ArrayList<>();
        this.positions = new ArrayList<>();
    }

    public int getBuyingPower() {
        return buyingPower;
    }

    public void setBuyingPower(int buyingPower) {
        this.buyingPower = buyingPower;
    }

    public int getEquity() {
        return equity;
    }

    public void setEquity(int equity) {
        this.equity = equity;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public long getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(long lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }
}
