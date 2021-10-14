package pageObjects;

import common.AbstractPage;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Screenshots;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pageUIs.QATestSuccessfullyPageUI;

public class QATestSuccessfullyPageObject extends AbstractPage {
    @Managed
    private WebDriver driver;

    public QATestSuccessfullyPageObject(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public boolean isTextDisplayed(){
        waitElementVisible(driver, QATestSuccessfullyPageUI.HAVE_NICE_DAY_TEXT);
        return isElementDisplayed(driver, QATestSuccessfullyPageUI.HAVE_NICE_DAY_TEXT);
    }
}
