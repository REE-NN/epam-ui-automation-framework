package testCases;

import driver.DriverManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static testCases.LoginTest.loginPage;
import static testCases.LoginTest.userMenu;

public class LogoutTest {

    @Test(
            groups = {"logout"},
            dependsOnGroups = "check sent letter",
            alwaysRun = true)
    public void logoutCheck() {
        userMenu.openMailFromMenu();
        assertTrue(loginPage.getPasswdField().isDisplayed());
    }

    @AfterSuite(alwaysRun = true)
    public void closeDriver() {
        DriverManager.unloadDriver();
    }
}