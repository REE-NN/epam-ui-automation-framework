package testCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static dataSource.StaticSource.THREAD_SLEEP_4;

import static org.testng.Assert.assertEquals;
import static testCases.LoginTest.mailPage;
import static testCases.LoginTest.userMenu;

public class LetterDeleteTest {



    @Parameters({"draft_Subject"})
    @Test(
            groups = {"check drafts"},
            dependsOnGroups = {"create letter"}
    )
    public void checkDraftSubjectTest(String subjectBody) {
        mailPage.openLeftMenuItem(userMenu.getDraftFolder());
        waitForDraftListToUpdate();

        String firstDraftSubject = mailPage.getFirstLetterSubject();
        assertEquals(firstDraftSubject, subjectBody, "The message with subject \"" +
                firstDraftSubject + "\" is not right");
    }

    @Parameters({"draft_Subject"})
    @Test(
            groups = {"delete drafts"},
            dependsOnGroups = {"check drafts"})
    void deleteFirstDraft(String subjectBody) {
        mailPage.dragAndDrop(mailPage.getFirstLetter(), userMenu.getTrashFolder());

        mailPage.openLeftMenuItem(userMenu.getTrashFolder());
        waitForDraftListToUpdate();
        String firstDeletedLetterSubject = mailPage.getFirstLetterSubject();
        assertEquals(firstDeletedLetterSubject, subjectBody,
                "The message wasn't deleted, or it wasn't the right one");
    }

    private void waitForDraftListToUpdate() {
        try {
            Thread.sleep(THREAD_SLEEP_4); // потом можно заменить на ожидание исчезновения/появления
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for draft list update", e);
        }
    }
}