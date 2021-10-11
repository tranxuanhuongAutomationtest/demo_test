package pageObjects;
import common.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUIs.QATestSurveyUI;

public class QATestSurveyPageObject extends AbstractPage {
    private  WebDriver driver;
    public QATestSurveyPageObject(WebDriver driver) {
        this.driver= driver;
    }

    public void clickToGoodRadioButton(){
        waitElementClickable(driver, QATestSurveyUI.GOOD_RADIO_TEXT);
        clickToElementByJS(driver, QATestSurveyUI.GOOD_RADIO_BUTTON);
    }


    public void clickToNextButton(){
        waitElementClickable(driver, QATestSurveyUI.NEXT_BUTTON);
        clickToElementByJS(driver, QATestSurveyUI.NEXT_BUTTON);
    }


}
