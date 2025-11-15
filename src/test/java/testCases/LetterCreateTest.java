package testCases;

import dataSource.DataProviderSource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.Assert.assertTrue;
import static testCases.LoginTest.mailPage;
import static testCases.LoginTest.userMenu;
import static testCases.LoginTest.wrightLetter;

public class LetterCreateTest {

    private static final Logger log = LogManager.getLogger(LetterCreateTest.class);
    @BeforeMethod(
            groups = {"open new letter"},
            onlyForGroups = {"create letter"})
    void openNewLetter() {
        log.info("Открытие формы нового письма через меню");
        mailPage.openLeftMenuItem(userMenu.getNewLetterBtn());

        boolean isPresent = mailPage.isElementPresent();
        log.info("Форма создания письма отображается: {}", isPresent);
        assertTrue(isPresent, "Opening new letter form - works wrong");
    }

    @Test(
            dataProvider = "letterFromCSV",
            dataProviderClass = DataProviderSource.class,
            groups = {"create letter"},
            dependsOnGroups = {"enter menu"})

    public void createLetter(String inAddress, String inSubject, String inBody) {
        log.info("Создание письма: адрес='{}', тема='{}'", inAddress, inSubject);
        wrightLetter.wrightLetter(inAddress, inSubject, inBody);

        // saveStatus в яндексе убрали, так что эту часть проверки убираю,
        // в дальнейшем заменю на проверку писем в папке "черновики"
        //String saveStatus = wrightLetter.getSaveStatus();
        //assertTrue(saveStatus.contains("сохранено"), "The email was not saved");
    }

    @AfterMethod(
            onlyForGroups = {"create letter"})
    void closeLetter() {
        log.info("Закрытие формы нового письма");
        wrightLetter.closeLetter();
    }
}
