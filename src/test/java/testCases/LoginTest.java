package testCases;

import config.ConfProperties;
import driver.DriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MailPage;
import pages.MailPageWriteLetter;
import pages.UserMenu;

import java.time.Duration;

import static dataSource.StaticSource.WAIT_TIMEOUT_SECONDS_10;
import static driver.DriverManager.driver;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class LoginTest {
    public static LoginPage loginPage;
    public static UserMenu userMenu;
    public static MailPage mailPage;
    public static MailPageWriteLetter wrightLetter;

    @BeforeSuite(description = "load browserDriver")
    public void startMethod() {
        driver = DriverManager.loadDriver();

        loginPage = new LoginPage(driver);
        userMenu = new UserMenu(driver);
        mailPage = new MailPage(driver);
        wrightLetter = new MailPageWriteLetter(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_10)); // Implicit Wait, неявное ожидание
    }

    @Test(groups = "smoke")
    public void canOpenStartPage() {
        String url = ConfProperties.getProperty("startPage");
        driver.get(url);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> {
                    String t = d.getTitle();
                    return t != null && !t.isBlank();
                });

        String title = driver.getTitle();
        assertFalse(title.isBlank(), "Title is blank");
    }

    @Test(groups = "logIn")
    public void loginTest() {
        loginPage.doLogin();
        UserMenu userMenu = new UserMenu(driver);
        assertTrue(userMenu.isAvatarVisible(),
                "Аватар не виден — логин, похоже, не прошёл.");
    }

    @Test(groups = {"enter menu"}, dependsOnGroups = "logIn")
    public void openUserMenuTest() {
        userMenu.openUserMenu();
        assertTrue(userMenu.isUserMenuOpen(), "Меню пользователя не открылось.");
    }
}