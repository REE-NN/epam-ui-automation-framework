package driver;

import config.ConfProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Optional;

public class DriverFactory {

    public static WebDriver createDriver() {
        //недавно узнала про Optional, понравилось, пытаюсь применять
        String browser = Optional.ofNullable(ConfProperties.getProperty("browser"))
                .filter(b -> !b.isBlank())
                .orElse("firefox");

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Unknown browser: " + browser);
        }
    }
}
