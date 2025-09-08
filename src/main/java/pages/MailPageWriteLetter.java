package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static dataSource.StaticSource.WAIT_TIMEOUT_SECONDS_90;

public class MailPageWriteLetter extends MailPage {

    public MailPageWriteLetter(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.composeYabbles")
    private WebElement addressLetter;

    @FindBy(css = ".composeTextField")
    private WebElement subjectLetter;

    @FindBy(css = ".cke_wysiwyg_div")
    private WebElement bodyLetter;

    @FindBy(xpath = "//span[starts-with(text(), 'сохранено')]")
    private WebElement saveLetterStatus;

    @FindBy(css = ".Button2_view_default")
    private WebElement sendLetterBtn;

    @FindBy(css = ".composeHeader-Buttons:nth-child(2) .ControlButton_button_close > .ControlButton-Button")
    private WebElement closeLetterBtn;

    public MailPageWriteLetter wrightLetter(String inAddress, String inSubject, String inBody) {
        actions.sendKeys(addressLetter, inAddress)
                .click(subjectLetter)
                .sendKeys(subjectLetter, inSubject)
                .sendKeys(bodyLetter, inBody).build().perform();
        return this;
    }

    public String getSaveStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS_90));
        return wait.until(ExpectedConditions.visibilityOf(saveLetterStatus)).getText();
    }

    public MailPage sendLetter() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", sendLetterBtn);
        return new MailPage(driver);
    }

    public MailPage closeLetter() {
        closeLetterBtn.click();
        return new MailPage(driver);
    }
}
