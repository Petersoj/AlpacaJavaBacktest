package net.jacobpeterson.alpacajavabacktest.website;

import net.jacobpeterson.alpacajavabacktest.AlpacaJavaBacktest;

import java.util.ArrayList;

/**
 * This uses {@link spark.Spark} to host an internal website for viewing various financial charts and information on a
 * backtest and such.
 * <p>
 * This is completely thread safe!
 */
public class BacktestWebsite {

    static {
        // Set headless to true so their isn't a java app icon that pops up in the dock when using AWT
        System.setProperty("java.awt.headless", "true");
    }

    private final AlpacaJavaBacktest alpacaJavaBacktest;
    private final ArrayList<SimpleChartLine> simpleChartLines;

    /**
     * Instantiates a new Backtest website.
     *
     * @param alpacaJavaBacktest the alpaca java backtest
     */
    public BacktestWebsite(AlpacaJavaBacktest alpacaJavaBacktest) {
        this.alpacaJavaBacktest = alpacaJavaBacktest;
        this.simpleChartLines = new ArrayList<>();
    }

    /**
     * Initialize the website.
     */
    public void initialize() {
        configure();
        createRoutes();

        // Service.ignite()
        // init();
        // initExceptionHandler(e -> {
        //
        // });
    }

    /**
     * Configure the website.
     */
    private void configure() {
        // threadPool(4, 1, Integer.MAX_VALUE);
        // port(80);
    }

    /**
     * Create routes for the website.
     */
    private void createRoutes() {
        // get("/", (request, response) -> "<h1>AlpacaJavaBacktest</h1>");
    }
}
