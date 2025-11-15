package testCases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static dataSource.StaticSource.THREAD_SLEEP_4;
import static org.testng.Assert.assertEquals;
import static testCases.LoginTest.mailPage;
import static testCases.LoginTest.userMenu;
import static testCases.LoginTest.wrightLetter;

public class LetterSendTest {
    @BeforeMethod(
            onlyForGroups = {"send letter"})
    void openLetter() throws InterruptedException {
        mailPage.openLeftMenuItem(userMenu.getDraftFolder());
        Thread.sleep(THREAD_SLEEP_4);
        mailPage.openLetter(mailPage.getFirstLetter());
    }

    //можно будет добавить действие "зайти в черновики, выбрать нужную тему"
    @Test(
            groups = {"send letter"},
            dependsOnGroups = {"delete drafts"})
    void sendLetter() {
        wrightLetter.sendLetter();
    }

    @Parameters({"sentLetter_Subject"})
    @Test(
            groups = {"check sent letter"},
            dependsOnGroups = {"send letter"})
    void checkSentLetterSubjectTest(String Subject_Body) throws InterruptedException {
        mailPage.openLeftMenuItem(userMenu.getSentFolder());

        String sentLetterSubject = mailPage.getFirstLetterSubject();
        assertEquals(sentLetterSubject, Subject_Body, "The email \"" +
                sentLetterSubject + "\" was not sent right");
        Thread.sleep(THREAD_SLEEP_4);
    }
}