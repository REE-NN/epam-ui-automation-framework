package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;

import static dataSource.StaticSource.LOAD_DRIVER_MESSAGE;
import static dataSource.StaticSource.UNLOAD_DRIVER_MESSAGE;

public class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class);
    public static WebDriver driver  = DriverFactory.createDriver();

    public static WebDriver loadDriver() {
        if (driver == null) {
            WebDriverManager.firefoxdriver().setup();
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