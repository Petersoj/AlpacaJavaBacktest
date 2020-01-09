package net.jacobpeterson.playground;

import io.github.mainstringargs.polygon.PolygonAPI;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Test;

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
        PolygonAPI polygonAPI = new PolygonAPI();
        LOGGER.info(polygonAPI.getLastQuote("AAPL").toString().replace(",", ",\n\t"));
    }
}
