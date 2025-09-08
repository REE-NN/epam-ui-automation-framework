package driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.ConfProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;

import static dataSource.StaticSource.*;

public class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class);
    public static WebDriver driver;

    public static WebDriver loadDriver() {
        if (driver == null) {
            System.setProperty(GECKO, ConfProperties.getProperty("geckodriver"));
            driver = new FirefoxDriver();
            log.info(LOAD_DRIVER_MESSAGE);
        }
        return driver;
    }

    public static void unloadDriver() {
        if (driver != null) {
            try {
                driver.quit();
                driver = null;
                log.info(UNLOAD_DRIVER_MESSAGE);
            } catch (WebDriverException e) {
                log.error("Ошибка при завершении драйвера", e);
            } finally {
                driver = null;
            }
        }
    }
}