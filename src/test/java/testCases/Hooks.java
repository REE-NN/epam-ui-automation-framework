package testCases;

import driver.DriverManager;
import org.testng.annotations.AfterSuite;

public class Hooks {
    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        DriverManager.unloadDriver(); // внутри уже есть try/finally и логирование
    }
}