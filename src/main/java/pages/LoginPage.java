package pages;

import config.ConfProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static dataSource.StaticSource.*;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#passp-field-login")
    private WebElement loginField;

    //@FindBy(css = "#passp\\:sign-in")
    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;


    //@FindBy(xpath = "//*[@id='passp-field-passwd']")
    @FindBy(css = "#passp-field-passwd")
    //@FindBy(name = "passwd")
    private WebElement passwdField;

    public WebElement getPasswdField() {
        Wait<WebDriver> waiter = new FluentWait<>(driver);
        return waiter
                .until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector("#passp-field-passwd"))
                );
    }

    Actions actions = new Actions(driver);

    public LoginPage inputLogin() {
        actions.sendKeys(loginField, ConfProperties.getProperty(LOGIN))
                .click(loginButton)
                .perform();
        return this;
    }

    public void inputPasswd() {
        try {
            WebElement passwdField = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#passp-field-passwd")));

            passwdField.sendKeys(ConfProperties.getProperty("password"));

            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.elementToBeClickable(loginButton))
                    .click();

        } catch (TimeoutException e) {
            System.out.println("Парольное поле не появилось — возможно, открылся QR-код. Завершаем выполнение.");
            driver.quit();
            System.exit(1);
        }
    }

    public MailPage doLogin() {
        inputLogin();
        inputPasswd();
        return new MailPage(driver);
    }
}