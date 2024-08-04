package aeterraes.logging;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration class for setting up the logger
 */
@Getter
@Setter
public class LoggerConfig {

    /**
     * The logger instance used for logging messages
     */
    @Getter
    private static final Logger logger = Logger.getLogger(LoggerConfig.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("D:\\ylab\\car-shop-service\\src\\main\\resources\\car-shop-service.log", true);
            fileHandler.setFormatter(new java.util.logging.SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Something went wrong with logger: " + e.getMessage());
        }
    }
}

