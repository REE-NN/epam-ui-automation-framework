package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

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
}
