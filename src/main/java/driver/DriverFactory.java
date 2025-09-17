package driver;

import config.ConfProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    public static WebDriver createDriver() {
        Browser browser = Browser.from(ConfProperties.getProperty("browser"));

        // TODO: поддержка Chrome/Edge — пока тестируется только Firefox
        switch (browser) {
            case CHROME:
//                WebDriverManager.chromedriver().setup();
//                return new ChromeDriver();
                throw new UnsupportedOperationException("Chrome пока не поддерживается в этом проекте");
            case EDGE:
//                WebDriverManager.edgedriver().setup();
//                return new EdgeDriver();
                throw new UnsupportedOperationException("Edge пока не поддерживается в этом проекте");
            case FIREFOX:
            default:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
        }
    }
}
