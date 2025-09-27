package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MailPage extends BasePage {

    public MailPage(WebDriver driver) {
        super(driver);
    }

    Actions actions = new Actions(driver);

    @FindBy(css = ".mail-MessageSnippet-Item_subject:nth-of-type(1) > *")
    private WebElement firstLetterSubject;

    public WebElement getFirstLetter() {
        return firstLetterSubject;
    }

    public String getFirstLetterSubject() {
        return firstLetterSubject.getText();
    }

    public MailPage openLetter(WebElement webElement) {
        actions.click(webElement).perform();
        return new MailPage(driver);
    }

    public MailPage dragAndDrop(WebElement draggable, WebElement target) {
        actions.dragAndDrop(draggable, target).perform();
        return this;
    }

    public MailPage openLeftMenuItem(WebElement item) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // дождались кликабельности и прокрутили к элементу
        wait.until(ExpectedConditions.elementToBeClickable(item));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", item);

        // обычный клик, с запасным вариантом через JS
        try {
            item.click();
        } catch (ElementNotInteractableException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", item);
        }

        return new MailPage(driver);
    }
}
