package survey.stepDefinitions;

import core.WebBase;

import io.appium.java_client.android.AndroidDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

public class ServiceHooks {
	// Run for many thread
	private static WebDriver driver;
	private static final Logger log = Logger.getLogger(ServiceHooks.class.getName());
	private static  String platform="WEB";

	@Before
	public static void  beforeHook() {
		log.info("before every scenario");
		String browser = System.getProperty("BROWSER");
		System.out.println("Browser name run by command line = " + browser);
		if(platform.toUpperCase().equals("WEB")){
			driver = WebBase.creatDriver(browser);
		}
	}
	public static WebDriver openBrowser(){
		if(driver==null)
			throw new NullPointerException("webdriver is null");
		return driver;
	}
	@After
	public void afterHook(Scenario scenario){
		log.info("after every scenario");
		if(platform.toUpperCase().equals("WEB")){
			if(scenario.isFailed()){
				//scenario.attach(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES),"image/png","image");
				scenario.attach(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES), "image/png","image");
			}
			driver.quit();
		}
	}


}