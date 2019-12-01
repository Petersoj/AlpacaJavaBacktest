package net.jacobpeterson.alpacajavabacktest.website;

import static spark.Spark.*;

/**
 * The type Backtest website.
 */
public class BacktestWebsite {

    /**
     * Instantiates a new Backtest website.
     */
    public BacktestWebsite() {
    }

    /**
     * Initialize website.
     */
    public void initializeWebsite() {
        configure();
        createRoutes();
    }

    /**
     * Configure.
     */
    private void configure() {
        threadPool(4, 1, Integer.MAX_VALUE);
        port(80);
    }

    /**
     * Create routes.
     */
    private void createRoutes() {
        get("/", (request, response) -> "<h1>AlpacaJavaBacktest</h1>");
    }
}
