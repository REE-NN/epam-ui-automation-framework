package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static dataSource.StaticSource.WAIT_TIMEOUT_SECONDS_30;

public class UserMenu extends BasePage {
    private static final Logger log = LogManager.getLogger(UserMenu.class);

    private final WebDriverWait wait;

    public UserMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /**
     * Аватар виден на странице профиля
     */
    public boolean isAvatarVisible() {
        return wait.until(ExpectedConditions.visibilityOf(leftAvatar)).isDisplayed();
    }

//    @FindBy(css = ".user-pic__image")
//    private WebElement userMenu;

    @FindBy(css = ".UserID-Avatar")
    private WebElement leftAvatar;

    @FindBy(css = ".personal-info-login__text_decorated")
    private WebElement emailAddress;

    @FindBy(css = ".mail-ComposeButton")
    private WebElement newLetterBtn;

    @FindBy(css = ".legouser__menu-item_action_mail")
    private WebElement mailPageBtn;

    @FindBy(css = ".legouser__menu-item_action_exit")
    private WebElement logoutBtn;

    @FindBy(xpath = "//span[contains(text(),'Черновики')]")
    private WebElement draftFolder;

    @FindBy(xpath = "//span[contains(text(),'Отправленные')]")
    private WebElement sentFolder;

    @FindBy(xpath = "//span[contains(text(),'Удалённые')]")
    private WebElement trashFolder;

    @FindBy(xpath = "//a[@href='https://mail.yandex.ru/']//span[text()='Почта']")
    private WebElement mailLink;

    public WebElement getDraftFolder() {
        return draftFolder;
    }

    public WebElement getSentFolder() {
        return sentFolder;
    }

    public WebElement getTrashFolder() {
        return trashFolder;
    }

    public WebElement getNewLetterBtn() {
        return newLetterBtn;
    }

    public WebElement getMailPage() {
        return mailPageBtn;
    }

    public WebElement getLogout() {
        return logoutBtn;
    }

    public String getEmailAddress() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_30))
                .until(ExpectedConditions.visibilityOf(emailAddress))
                .getText();
    }

    public void openUserMenu() {
        new Actions(driver)
                .moveToElement(leftAvatar)
                .pause(Duration.ofMillis(500)) // можно убрать
                .click()
                .perform();

        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_30))
                .until(ExpectedConditions.elementToBeClickable(leftAvatar));
    }

    @FindBy(xpath = "//a[@data-testid='mail']//span[text()='Почта']")
    //@FindBy(xpath = "//a[@data-testid='mail']")
    private WebElement mailPage;

    public MailPage openMailFromMenu() {
        long totalStart = System.currentTimeMillis();

        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(leftAvatar));

            try {
                leftAvatar.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", leftAvatar);
            }

            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".menu-popup")));

            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.elementToBeClickable(mailPage));

            try {
                mailPage.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", mailPage);
            }

        } catch (Exception e) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File dest = new File("target/failure_openMailFromMenu.png");
                //FileUtils.copyFile(screenshot, dest);
            } catch (Exception ignored) {
            }
            throw e;
        }

        return new MailPage(driver);
    }
}