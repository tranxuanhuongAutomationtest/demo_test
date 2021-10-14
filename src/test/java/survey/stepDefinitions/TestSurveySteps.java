package survey.stepDefinitions;

import common.GlobalConstants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pageObjects.PageGeneratorManager;
import pageObjects.QATestSuccessfullyPageObject;
import pageObjects.QATestSurveyPageObject;

public class TestSurveySteps {
    @Managed
    private WebDriver driver;
    //@Steps
    QATestSurveyPageObject testPage;

   // @Steps
    QATestSuccessfullyPageObject successfulPage;


    @Given("Open web page monkeysurvey successfully")
    public void openWebPageMonkeysurveySuccessfully() {
        this.driver = ServiceHooks.openBrowser();
        driver.get(GlobalConstants.WEBADMIN_URL);
        testPage = PageGeneratorManager.openQATestSurveyPage(driver);
    }

    @When("Click to Good raidobutton")
    public void clickToGoodRaidobutton() {
        testPage.clickToGoodRadioButton();
    }

    @And("Click to Next Button")
    public void clickToNextButton() {
        testPage.clickToNextButton();
    }


    @Then("Verify {string} exists")
    public void verifyExists(String arg0) {
        successfulPage = PageGeneratorManager.openQATestSuccessfulPage(driver);
        Assert.assertTrue(successfulPage.isTextDisplayed());
    }
}
