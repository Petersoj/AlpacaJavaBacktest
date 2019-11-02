package net.jacobpeterson.alpacajavabacktest.broker;

import io.github.mainstringargs.alpaca.domain.Account;
import net.jacobpeterson.alpacajavabacktest.broker.portfolio.BacktestPortfolio;

/**
 * This is used as a simulated broker to submit orders on a stock exchange. These orders are compared against bid/ask
 * prices as well as traded prices of an equity. This ensures that the backtesting is most accurate to what would happen
 * in a live market.
 */
public class BacktestBroker {

    private BacktestPortfolio backtestPortfolio;
    private long currentTimeMilliseconds;

    /**
     * Instantiates a new Backtest broker with defaults:
     * <ul>
     *     <li>Equity: $100,000</li>
     * </ul>
     */
    public BacktestBroker() {
        Account defaultAccount = BacktestPortfolio.createDefaultAccount(100_000, 4);
        this.backtestPortfolio = new BacktestPortfolio(defaultAccount);
    }

    /**
     * Instantiates a new Backtest broker.
     *
     * @param backtestPortfolio the backtest portfolio
     */
    public BacktestBroker(BacktestPortfolio backtestPortfolio) {
        this.backtestPortfolio = backtestPortfolio;
    }

    /**
     * Gets backtest portfolio.
     *
     * @return the backtest portfolio
     */
    public BacktestPortfolio getBacktestPortfolio() {
        return backtestPortfolio;
    }

    /**
     * Sets backtest portfolio.
     *
     * @param backtestPortfolio the backtest portfolio
     */
    public void setBacktestPortfolio(BacktestPortfolio backtestPortfolio) {
        this.backtestPortfolio = backtestPortfolio;
    }

    /**
     * Gets current time milliseconds.
     *
     * @return the current time milliseconds
     */
    public long getCurrentTimeMilliseconds() {
        return currentTimeMilliseconds;
    }
}
