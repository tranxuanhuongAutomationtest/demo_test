package core;

import common.GlobalConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

public class WebBase {
    static WebDriver driver;
    //private static final Logger log = Logger.getLogger(WebBase.class.getName());

    public static WebDriver creatDriver(String browser){
        if (driver == null) {

            // Kiem tra BROWSER = null -> gan = chrome/ firefox (browser default for project)
            if (browser == null) {
                // Get browser name from Environment Variable in OS
                browser = System.getenv("BROWSER");
                if (browser == null) {
                    // Set default browser
                    browser = "chrome";
                }
            }

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "hchrome":
//					WebDriverManager.chromedriver().version("78.0.3904.70").setup();
//					ChromeOptions chromeOptions = new ChromeOptions();
//					chromeOptions.addArguments("headless");
//					chromeOptions.addArguments("window-size=1920x1080");
//					driver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                    System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
                    driver = new FirefoxDriver();
                    break;
                case "hfirefox":
                    WebDriverManager.firefoxdriver().setup();
                    System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                    System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setHeadless(true);
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "ie":
                    WebDriverManager.iedriver().arch32().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case "coccoc":
                    WebDriverManager.chromedriver().driverVersion("90.0.4430.24").setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setBinary("C:\\Program Files (x86)\\CocCoc\\Browser\\Application\\browser.exe");
                    //options.setBinary(urlCoccocBrowser);
                    driver = new ChromeDriver(options);
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
            }

            driver.get(GlobalConstants.WEBADMIN_URL);
            driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
            driver.manage().window().maximize();
           // log.info("------------- Started the browser -------------");
        }
        return driver;
    }
}
