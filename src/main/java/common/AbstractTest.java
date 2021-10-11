package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTest {
	WebDriver driver;
	String userDir = GlobalConstants.ROOT_FOLDER;
	
	protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
	
	protected WebDriver getDriverBroswer(String browserName) {
		Browser browser = Browser.valueOf(browserName.toUpperCase());
		switch ( browser) {
		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case CHROME:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case EDGE:
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		case IE:
			WebDriverManager.iedriver().arch32().setup();
			driver = new InternetExplorerDriver();
			break;
		case COCCOC:
			WebDriverManager.chromedriver().driverVersion("90.0.4430.24").setup();
			ChromeOptions options = new ChromeOptions();
			//options.setBinary("C:\\Program Files (x86)\\CocCoc\\Browser\\Application\\browser.exe");
			options.setBinary(GlobalConstants.COCCOC_URL);
			driver = new ChromeDriver(options);
			break;
			
		default:
			throw new RuntimeException("Please input your browser");
		}
		driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
		return driver;
	}

	
	
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
		threadLocalDriver.get().get(url);
		threadLocalDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return threadLocalDriver.get();
	}
	
	//getDriverBrowser don't use threadlocal
	protected WebDriver getDriverBroswer(String browserName, String url) {
		Browser browser = Browser.valueOf(browserName.toUpperCase());
		switch ( browser) {
		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case CHROME:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case EDGE:
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		case IE:
			WebDriverManager.iedriver().arch32().setup();
			driver = new InternetExplorerDriver();
			break;
		case COCCOC:
			WebDriverManager.chromedriver().driverVersion("90.0.4430.24").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Program Files (x86)\\CocCoc\\Browser\\Application\\browser.exe");
			//options.setBinary(urlCoccocBrowser);
			driver = new ChromeDriver(options);
			break;

		default:
			throw new RuntimeException("Please input your browser");
		}
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	// remove driver in threadlocalDriver
	protected void removeDriver() {
		threadLocalDriver.get().quit();
		threadLocalDriver.remove();
	}
	
	private WebDriver getDriver() {
		return threadLocalDriver.get();
	}
	
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
	public void getUrl(String url, WebDriver driver) {
		driver.get(url);
	}

}
