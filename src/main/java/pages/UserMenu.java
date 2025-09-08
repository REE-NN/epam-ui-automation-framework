package pages;

import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.Duration;

import static dataSource.StaticSource.*;

public class UserMenu extends BasePage {
    private static final Logger log = LogManager.getLogger(UserMenu.class);
    private final Actions actions;

    public UserMenu(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
    }

//    @FindBy(css = ".user-pic__image")
//    private WebElement userMenu;

    @FindBy(css = ".UserID-Avatar")
    private WebElement userMenu;

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

//    public MailPage openRightMenuItem(WebElement somePage) {
//        actions
//                .click(userMenu)
//                .pause(WAIT_TIMEOUT_SECONDS_90)
//                .click(somePage)
//                .build().perform();
//        return new MailPage(driver);
//    }

    @FindBy(css = ".UserID-Avatar")
    private WebElement userAvatar;

    public void openUserMenu() {
        new Actions(driver)
                .moveToElement(userAvatar)
                .pause(Duration.ofMillis(500)) // можно убрать
                .click()
                .perform();

        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_30))
                .until(ExpectedConditions.elementToBeClickable(userMenu));
    }

//    @FindBy(css = "a[data-statlog='notifications.mail']")
//    private WebElement mailMenuItem; - не в проекте

//    @FindBy(css = ".MenuItem_mail")
//    private WebElement mailPage;

    @FindBy(xpath = "//a[@data-testid='mail']//span[text()='Почта']")
    //@FindBy(xpath = "//a[@data-testid='mail']")
    private WebElement mailPage;

//    public MailPage openMailFromMenu() {
//        Logger log = LogManager.getLogger(UserMenu.class);
//
//        log.info("⏱ Старт открытия меню пользователя");
//        long totalStart = System.currentTimeMillis();
//
//        // ⏱ Замер времени клика по аватару
//        try {
//            long start = System.currentTimeMillis();
//            log.info("➡ Пытаемся кликнуть по userMenu (аватар)");
//
//            try {
//                new WebDriverWait(driver, Duration.ofSeconds(10))
//                        .until(ExpectedConditions.elementToBeClickable(userMenu));
//                userMenu.click();
//                log.info("✅ Клик по userMenu обычным способом прошёл за " + (System.currentTimeMillis() - start) + " мс");
//            } catch (Exception e) {
//                log.warn("⚠ Обычный клик по userMenu не сработал, пробуем через JavaScript");
//
//                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", userMenu);
//                log.info("✅ JS-клик по userMenu прошёл за " + (System.currentTimeMillis() - start) + " мс");
//            }
//        } catch (Exception e) {
//            log.error("❌ Ошибка при попытке открыть меню пользователя", e);
//            throw e;
//        }
//
//        // ⏱ Ожидаем появление меню
//        try {
//            long start = System.currentTimeMillis();
//            log.info("➡ Ждём появления меню (.menu-popup)");
//            new WebDriverWait(driver, Duration.ofSeconds(30))
//                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".menu-popup")));
//            log.info("✅ Меню появилось за " + (System.currentTimeMillis() - start) + " мс");
//        } catch (TimeoutException e) {
//            log.error("❌ Меню пользователя не появилось", e);
//            throw e;
//        }
//
//        // ⏱ Ожидаем кликабельность кнопки "Почта"
//        try {
//            long start = System.currentTimeMillis();
//            log.info("➡ Ждём кликабельность mailPage");
//
//            new WebDriverWait(driver, Duration.ofSeconds(30))
//                    .until(ExpectedConditions.elementToBeClickable(mailPage));
//
//            log.info("✅ mailPage кликабельна (ждали " + (System.currentTimeMillis() - start) + " мс)");
//        } catch (TimeoutException e) {
//            log.error("❌ mailPage не стал кликабельным", e);
//            throw e;
//        }
//
//        // ⏱ Кликаем по "Почта"
//        try {
//            long start = System.currentTimeMillis();
//            log.info("➡ Кликаем по mailPage");
//
//            try {
//                mailPage.click();
//                log.info("✅ Клик по mailPage прошёл за " + (System.currentTimeMillis() - start) + " мс");
//            } catch (Exception e) {
//                log.warn("⚠ Обычный клик по mailPage не сработал, пробуем JS");
//                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", mailPage);
//                log.info("✅ JS-клик по mailPage прошёл за " + (System.currentTimeMillis() - start) + " мс");
//            }
//
//        } catch (Exception e) {
//            log.error("❌ Ошибка при клике по mailPage", e);
//            throw e;
//        }
//
//        log.info("🏁 Открытие меню и переход заняли всего: " + (System.currentTimeMillis() - totalStart) + " мс");
//        return new MailPage(driver);
//    }

    public MailPage openMailFromMenu() {
        long totalStart = System.currentTimeMillis();

        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(userMenu));

            try {
                userMenu.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", userMenu);
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
            } catch (Exception ex) {
            }
            throw e;
        }

        return new MailPage(driver);
    }

    public MailPage openLeftMenuItem(WebElement webElement) {
        actions.click(webElement).perform();
        return new MailPage(driver);
    }
}