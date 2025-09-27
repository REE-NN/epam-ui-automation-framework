package pages;

import config.ConfProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static dataSource.StaticSource.LOGIN;
import static dataSource.StaticSource.PASS;

public class LoginPage extends BasePage {

    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @FindBy(css = "#passp-field-login")
    private WebElement loginField;

    @FindBy(css = "button[type='submit'], button#passp\\:sign-in")
    private WebElement loginButton;

    @FindBy(css = "#passp-field-passwd")
    private WebElement passwdField;


    //не удалять, это относится к logout
//    public WebElement getPasswdField() {
//        Wait<WebDriver> waiter = new FluentWait<>(driver);
//        return waiter
//                .until(ExpectedConditions
//                        .visibilityOfElementLocated(By.cssSelector("#passp-field-passwd"))
//                );
//    }

    /** вводим логин и жмём далее — без Actions */
    public LoginPage inputLogin() {
        wait.until(ExpectedConditions.visibilityOf(loginField)).clear();
        loginField.sendKeys(ConfProperties.getProperty(LOGIN)); // или "login"
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return this;
    }

    /** вводим пароль и подтверждаем — без quit/exit */
    public LoginPage inputPasswd() {
        wait.until(ExpectedConditions.visibilityOf(passwdField)).clear();
        passwdField.sendKeys(ConfProperties.getProperty(PASS)); // или "password"
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return this;
    }

    public MailPage doLogin() {
        inputLogin();
        inputPasswd();
        return new MailPage(driver);
    }
}