package survey.stepDefinitions;

import common.AbstractTest;
import common.GlobalConstants;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Screenshots;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class ServiceHooks extends AbstractTest {
	// Run for many thread
	@Managed
	private static WebDriver driver;
	private static final Logger log = Logger.getLogger(ServiceHooks.class.getName());

	@Before
	public synchronized void  beforeHook() {
		log.info("before every scenario");
		String browser = System.getProperty("BROWSER");
		System.out.println("Browser name run by command line = " + browser);
		//driver = WebBase.creatDriver("chrome");
		driver = getDriverBroswerUseThreadLocal("chrome", GlobalConstants.WEBADMIN_URL);
	}
	public static WebDriver openBrowser(){
		if(driver==null)
			throw new NullPointerException("webdriver is null");
		return driver;
	}
	@Screenshots(beforeAndAfterEachStep = true)
	@After
	public void afterHook(Scenario scenario){
		log.info("after every scenario");
		driver.close();

	}


}