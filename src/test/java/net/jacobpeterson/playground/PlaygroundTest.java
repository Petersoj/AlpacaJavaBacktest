package net.jacobpeterson.playground;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Test;

import static spark.Spark.init;
import static spark.Spark.staticFileLocation;

/**
 * This class is used for manual/playground testing purposes.
 * <p>
 * You should put the <code>alpaca.properties</code> and <code>polygon.properties</code> files in the
 * <code>test/resources</code> directory if desired.
 */
public class PlaygroundTest {

    private static final Logger LOGGER = LogManager.getLogger();

    static {
        Configurator.setRootLevel(Level.ALL);
    }

    @Test
    public void playgroundTest() throws Exception {
        staticFileLocation("/");
        init();

        Thread.sleep(Long.MAX_VALUE);
    }
}
