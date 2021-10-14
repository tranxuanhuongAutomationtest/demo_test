package pageObjects;
import common.AbstractPage;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Screenshots;
import org.openqa.selenium.WebDriver;
import pageUIs.QATestSurveyUI;
import net.thucydides.core.annotations.Step;
public class QATestSurveyPageObject extends AbstractPage {
    @Managed
    private  WebDriver driver;

    public QATestSurveyPageObject(WebDriver driver) {
        this.driver= driver;
    }
    @Step
    @Screenshots(beforeAndAfterEachStep = true)
    public void clickToGoodRadioButton(){
        Serenity.takeScreenshot();
        waitElementClickable(driver, QATestSurveyUI.GOOD_RADIO_TEXT);
        clickToElement(driver, QATestSurveyUI.GOOD_RADIO_BUTTON);
    }

    @Step
    @Screenshots(beforeAndAfterEachStep = true)
    public void clickToNextButton(){
        Serenity.takeScreenshot();
        waitElementClickable(driver, QATestSurveyUI.NEXT_BUTTON);
        clickToElementByJS(driver, QATestSurveyUI.NEXT_BUTTON);
    }


}
