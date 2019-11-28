package net.jacobpeterson.alpacajavabacktest.broker.portfolio;

import io.github.mainstringargs.alpaca.domain.Account;
import io.github.mainstringargs.alpaca.domain.Order;
import io.github.mainstringargs.alpaca.domain.Position;
import io.github.mainstringargs.polygon.properties.PolygonProperties;

import java.util.ArrayList;

/**
 * This class is used as the backtesting portfolio. It contains account data, orders, positions, etc. The stuff you
 * usually might see in an investment portfolio.
 */
public class BacktestPortfolio {

    private final Account account;
    private ArrayList<Order> orders;
    private ArrayList<Position> positions;

    /**
     * Instantiates a new Backtest portfolio.
     *
     * @param account the account
     */
    public BacktestPortfolio(Account account) {
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
    public static Account createDefaultAccount(float equity, float buyingPowerMultiplier) {
        String equityString = String.valueOf(equity);
        Account account = new Account(PolygonProperties.KEY_ID_VALUE,
                "ACTIVE",
                "USD",
                String.valueOf(equity * buyingPowerMultiplier),
                equityString,
                equityString,
                false,
                false,
                false,
                false,
                false,
                "0",
                true,
                String.valueOf(buyingPowerMultiplier),
                "0",
                "0",
                equityString,
                equityString,
                "-1", // TODO implement margin calls in later release
                "-1", // TODO implement margin calls in later release
                0,
                "0");
        return account;
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
