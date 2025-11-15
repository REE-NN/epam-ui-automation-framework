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
import java.util.List;

import static dataSource.StaticSource.WAIT_TIMEOUT_SECONDS_30;

public class UserMenu extends BasePage {
    private static final Logger log = LogManager.getLogger(UserMenu.class);
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    // любой валидный контейнер открытого меню
    private static final By MENU_CONTAINER =
            By.cssSelector("[data-testid='menu-popup'], .menu-popup, [role='menu']");

    @FindBy(css = "button.UserID-Account")
    private WebElement accountButton;

    public UserMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Аватар виден на странице профиля
     */
    public boolean isAvatarVisible() {
        return wait.until(ExpectedConditions.visibilityOf(leftAvatar)).isDisplayed();
    }

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

    private By resolveMenuContainer() {
        // 1) самый надёжный способ — по aria-controls
        String id = leftAvatar.getAttribute("aria-controls");
        if (id != null && !id.isBlank()) {
            return By.id(id);
        }
        // 2) запасные варианты на случай, если атрибута нет
        return By.cssSelector("[data-testid='menu-popup'], .menu-popup, [role='menu']");
    }

    public void openUserMenu() {
        // 0) дождались видимости кнопки
        wait.until(ExpectedConditions.visibilityOf(accountButton));

        // 1) несколько попыток разными способами
        for (int attempt = 0; attempt < 4 && !isMenuOpen(); attempt++) {
            try {
                switch (attempt) {
                    case 0: // обычный клик
                        wait.until(ExpectedConditions.elementToBeClickable(accountButton)).click();
                        break;
                    case 1: // клик через Actions (эмулирует «мышкой»)
                        new Actions(driver)
                                .moveToElement(accountButton)
                                .pause(Duration.ofMillis(150))
                                .click()
                                .perform();
                        break;
                    case 2: // форс-клик JS (если что-то перехватывает)
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", accountButton);
                        break;
                    case 3: // клавиатура как запасной вариант
                        accountButton.sendKeys(Keys.ENTER);
                        break;
                }
            } catch (Exception ignore) {
                // если какой-то способ кинул исключение — просто пробуем следующий
            }
            // дать UI дорисоваться
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }
        }

        // 2) Финальное ожидание факта открытия
        wait.until(d -> isMenuOpen());
    }

    private boolean isMenuOpen() {
        try {
            String expanded = accountButton.getAttribute("aria-expanded");
            if ("true".equalsIgnoreCase(expanded)) return true;
        } catch (StaleElementReferenceException ignored) {
            // если кнопка пересоздалась — позже проверим контейнер
        }
        List<WebElement> list = driver.findElements(MENU_CONTAINER);
        return !list.isEmpty() && list.get(0).isDisplayed();
    }

    // булева проверка для ассерта

    /**
     * Меню открыто?
     */
    public boolean isUserMenuOpen() {
        try {
            if ("true".equalsIgnoreCase(accountButton.getAttribute("aria-expanded"))) return true;
            return wait.until(ExpectedConditions.visibilityOfElementLocated(MENU_CONTAINER)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Открыть меню: клик по кнопке + фоллбэк на JS-клик
     */

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