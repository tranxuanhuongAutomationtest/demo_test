package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTest{
	String userDir = GlobalConstants.ROOT_FOLDER;
	@Managed
	protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
	
	//getDriverBrowser use threadlocal
	protected WebDriver getDriverBroswerUseThreadLocal(String browserName, String url) {
		Browser browser = Browser.valueOf(browserName.toUpperCase());
		switch ( browser) {
		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			setDriver(new FirefoxDriver());
			break;
		case CHROME:
			WebDriverManager.chromedriver().setup();
			setDriver(new ChromeDriver());
			break;
		case EDGE:
			WebDriverManager.edgedriver().setup();
			setDriver(new EdgeDriver());
			break;
		case IE:
			WebDriverManager.iedriver().arch32().setup();
			setDriver(new InternetExplorerDriver());
			break;
		case COCCOC:
			WebDriverManager.chromedriver().driverVersion("90.0.4430.24").setup();
			ChromeOptions options = new ChromeOptions();
			//options.setBinary("C:\\Program Files (x86)\\CocCoc\\Browser\\Application\\browser.exe");
			options.setBinary(GlobalConstants.COCCOC_URL);
			setDriver(new ChromeDriver(options));
			break;
			
		default:
			throw new RuntimeException("Please input your browser");
		}
		threadLocalDriver.get().manage().window().maximize();
		threadLocalDriver.get().get(url);
		threadLocalDriver.get().manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
		return threadLocalDriver.get();
	}
	

	
	// remove driver in threadlocalDriver
	protected void removeDriver() {
		threadLocalDriver.get().quit();
		threadLocalDriver.remove();
	}

//	public static WebDriver getDriver() {
//		return threadLocalDriver.get();
//	}
	
	private void setDriver(WebDriver driver) {
		threadLocalDriver.set(driver);
	}
	protected int randomInt() {
		Random ran = new Random();
		return ran.nextInt(100000) + 1000;
	}
	
	public void sleepSeconds(long timeout) {
		try {
			Thread.sleep(1000 * timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
