package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageGeneratorManager {

    public static QATestSurveyPageObject openQATestSurveyPage(WebDriver driver){
        return  new QATestSurveyPageObject(driver);
    }

    public static QATestSuccessfullyPageObject openQATestSuccessfulPage(WebDriver driver){
        return  new QATestSuccessfullyPageObject(driver);
    }
}
