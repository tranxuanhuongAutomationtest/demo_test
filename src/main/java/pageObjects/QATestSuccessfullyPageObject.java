package pageObjects;

import common.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUIs.QATestSuccessfullyPageUI;

public class QATestSuccessfullyPageObject extends AbstractPage {
    private WebDriver driver;

    public QATestSuccessfullyPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isTextDisplayed(){
        waitElementVisible(driver, QATestSuccessfullyPageUI.HAVE_NICE_DAY_TEXT);
        return isElementDisplayed(driver, QATestSuccessfullyPageUI.HAVE_NICE_DAY_TEXT);
    }
}
