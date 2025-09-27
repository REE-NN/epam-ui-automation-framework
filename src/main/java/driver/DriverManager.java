package driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import static dataSource.StaticSource.*;

public final class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class);
    public static WebDriver driver;
    private DriverManager() { }

    public static WebDriver loadDriver() {
        if (driver == null) {
            driver = DriverFactory.createDriver();
            log.info(LOAD_DRIVER_MESSAGE, driver.getClass().getSimpleName());
        }
        return driver;
    }

    public static void unloadDriver() {
        if (driver != null) {
            try {
                driver.quit();
                log.info(UNLOAD_DRIVER_MESSAGE);
            } catch (WebDriverException e) {
                log.error(DRIVER_QUIT_ERROR, e.getMessage(), e);
            } finally {
                driver = null;
            }
        }
    }
}