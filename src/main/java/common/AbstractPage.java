package common;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPage {
	private Alert alert;
	private WebElement element;
	private Select select;

	private WebDriverWait explicitWait;
	private JavascriptExecutor js;
	private List<WebElement> itemList;
	private Actions action;


	// WebBrowser
	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	public void setImplicitWait(WebDriver driver, long timeout) {
		driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageCurrentURL(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void acceptAlert(WebDriver driver) {
		waitAlertPresence(driver);
		alert = driver.switchTo().alert();
		alert.accept();
	}

	public void cancelAlert(WebDriver driver) {
		waitAlertPresence(driver);
		alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public String getTextAlert(WebDriver driver) {
		waitAlertPresence(driver);
		alert = driver.switchTo().alert();
		return alert.getText();
	}

	public void sendKeyToAlert(WebDriver driver, String key) {
		waitAlertPresence(driver);
		alert = driver.switchTo().alert();
		alert.sendKeys(key);
	}

	public void switchWindowById(WebDriver driver, String parentID) {
		Set<String> currentWindowHandle = driver.getWindowHandles();

		for (String childWindow : currentWindowHandle) {
			if (!childWindow.equals(parentID)) {

				driver.switchTo().window(childWindow);
				break;
			}
		}
	}

	public void switchWindowByTitle(WebDriver driver, String title) {
		Set<String> runWindows = driver.getWindowHandles();
		for (String childWindow : runWindows) {
			driver.switchTo().window(childWindow);
			String titleCurrentWindow = driver.getTitle();
			if (titleCurrentWindow.equals(title)) {
				break;
			}
		}

	}

	public void closeAllWindowsWithoutParent(WebDriver driver, String parentId) {
		Set<String> runWindows = driver.getWindowHandles();
		for (String child : runWindows) {
			if (!child.equals(parentId)) {
				driver.switchTo().window(child);
				driver.close();
			}
		}
		driver.switchTo().window(parentId);
	}

	// WebElement
	public By byLocator(String locatorPath) {
		return By.xpath(locatorPath);
	}
	public WebElement find(WebDriver driver, String locatorPath) {
		return driver.findElement(byLocator(locatorPath));
	}
	
	
	public WebElement find(WebDriver driver, String locatorPath, String...values) {
		return driver.findElement(By.xpath(getDynamicLocator(locatorPath, values)));
	}

	public List<WebElement> finds(WebDriver driver, String locatorPath) {
		return driver.findElements(byLocator( locatorPath));
	}
	public List<WebElement> finds(WebDriver driver, String locatorPath, String...values) {
		return driver.findElements(By.xpath(getDynamicLocator(locatorPath, values)));
	}

	public void clickToElement(WebDriver driver, String locatorPath) {
		find(driver, locatorPath).click();
	}
	public void clickToElement(WebDriver driver, String locatorPath, String...values) {
		find(driver, getDynamicLocator(locatorPath, values)).click();
	}

	public void sendKeysToElement(WebDriver driver, String locatorPath, String text) {
		element = find(driver, locatorPath);
		element.clear();
		element.sendKeys(text);
	}

	public void sendKeysToElement(WebDriver driver, String locatorPath, String text, String... values ) {
		element = find(driver, getDynamicLocator(locatorPath, values));
		element.clear();
		element.sendKeys(text);
	}

	public void selectItemInDropdown(WebDriver driver, String locatorPath, String itemValue) {
		select = new Select(find(driver, locatorPath));
		select.selectByVisibleText(itemValue);
	}
	public void selectItemInDropdown(WebDriver driver, String locatorPath, String itemValue, String... values) {
		select = new Select(find(driver, getDynamicLocator(locatorPath, values)));
		select.selectByVisibleText(itemValue);
	}

	public String getSelectedItemInDropdown(WebDriver driver, String locatorPath) {
		select = new Select(find(driver, locatorPath));
		return select.getFirstSelectedOption().getText();
	}
	public String getSelectedItemInDropdown(WebDriver driver, String locatorPath, String... values) {
		select = new Select(find(driver, getDynamicLocator(locatorPath, values)));
		return select.getFirstSelectedOption().getText();
	}

	public boolean isDropdownMutiple(WebDriver driver, String locatorPath) {
		select = new Select(find(driver, locatorPath));
		return select.isMultiple();

	}

	public void selectItemInCustomDropdownList(WebDriver driver, String parentLocator, String childLocator, String itemValue) {
		clickToElement(driver ,parentLocator);
		sleepSeconds(1);
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);

		explicitWait.until(ExpectedConditions.presenceOfElementLocated(byLocator(childLocator)));
		itemList = finds(driver ,childLocator);
		for (WebElement webElement : itemList) {
			if (webElement.getText().equals(itemValue)) {
				js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView(true);", webElement);
				webElement.click();
				sleepSeconds(1);
				break;
			}
		}

	}

	public String getElementAttribute(WebDriver driver, String locatorPath, String attributeName) {
		return find(driver, locatorPath).getAttribute(attributeName);
	}
	public String getElementAttribute(WebDriver driver, String locatorPath, String attributeName, String... values) {
		return find(driver, getDynamicLocator(locatorPath, values)).getAttribute(attributeName);
	}

	public String getElementText(WebDriver driver, String locatorPath) {
		return find(driver, locatorPath).getText();
	}
	public String getElementText(WebDriver driver, String locatorPath, String... values) {
		return find(driver, getDynamicLocator(locatorPath, values)).getText();
	}

	public int countElementNumber(WebDriver driver, String locatorPath) {
		return finds(driver ,locatorPath).size();
	}
	public int countElementNumber(WebDriver driver, String locatorPath, String... values) {
		return finds(driver,getDynamicLocator(locatorPath, values)).size();
	}

	public boolean isElementDisplayed(WebDriver driver, String locatorPath) {
		return find(driver, locatorPath).isDisplayed();
	}
	public boolean isElementDisplayed(WebDriver driver, String locatorPath, String... values) {
		return find(driver, getDynamicLocator(locatorPath, values)).isDisplayed();
	}
	
	public boolean isElementUnDisplayed(WebDriver driver, String locatorPath) {
		overrideImplicitWait(driver, GlobalConstants.SHORT_TIMEOUT);
		itemList = finds(driver, locatorPath);
		overrideImplicitWait(driver, GlobalConstants.LONG_TIMEOUT);
		if(itemList.size() == 0) {	
			return true;
		}else if (itemList.size() != 0 && !itemList.get(0).isDisplayed()) {
			return true;
		}else {
			return false;
		}
	}
	public boolean isElementUnDisplayed(WebDriver driver, String locatorPath, String...values) {
		overrideImplicitWait(driver, GlobalConstants.SHORT_TIMEOUT);
		itemList = finds(driver, locatorPath, values);
		overrideImplicitWait(driver, GlobalConstants.LONG_TIMEOUT);
		if(itemList.size() == 0) {	
			return true;
		}else if (itemList.size() != 0 && !itemList.get(0).isDisplayed()) {
			return true;
		}else {
			return false;
		}
	}
	public boolean isElementEnabled(WebDriver driver, String locatorPath) {
		return find(driver, locatorPath).isEnabled();
	}

	public boolean isElementSelected(WebDriver driver, String locatorPath) {
		return find(driver, locatorPath).isSelected();
	}

	public void checkToCheckbox(WebDriver driver, String locatorPath) {
		element = find(driver, locatorPath);
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void switchToFrameOrIframe(WebDriver driver, String locatorPath) {
		driver.switchTo().frame(find(driver, locatorPath));
	}

	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void hoverToElement(WebDriver driver, String locatorPath) {
		action = new Actions(driver);
		action.moveToElement(find(driver, locatorPath)).perform();
	}

	public void clickAndHoldToRandomElement(WebDriver driver, By locator, List<WebElement> itemList) {
		action = new Actions(driver);
		// action.clickAndHold();
	}

	public void doubleClickToElement(WebDriver driver, String locatorPath) {
		action = new Actions(driver);
		action.doubleClick(find(driver, locatorPath));
	}

	public void rightClickToElement(WebDriver driver, String locatorPath) {
		action = new Actions(driver);
		action.contextClick(find(driver, locatorPath));
	}

	public void sendKeyBroadToElement(WebDriver driver, String locatorPath, Keys key) {
		action = new Actions(driver);
		action.sendKeys(find(driver, locatorPath), key);
	}

	public void dragAndDropHTML4(WebDriver driver ,String dragLocator, String dropLocation) {
		action = new Actions(driver);
		action.dragAndDrop(find(driver ,dragLocator), find(driver,dropLocation));

	}

	// JS

	public Object executeForBrowser(WebDriver driver, String javaScript) {
		js = (JavascriptExecutor) driver;
		return js.executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		js = (JavascriptExecutor) driver;
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		js = (JavascriptExecutor) driver;
		String textActual = (String) js
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(WebDriver driver, String url) {
		js = (JavascriptExecutor) driver;
		js.executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(WebDriver driver, String locatorPath) {
		element = find(driver, locatorPath);
		js = (JavascriptExecutor) driver;
		String originalStyle = element.getAttribute("style");
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepSeconds(1);
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}
	public void highlightElement(WebDriver driver, String locatorPath, String...values) {
		element = find(driver, getDynamicLocator(locatorPath, values));
		js = (JavascriptExecutor) driver;
		String originalStyle = element.getAttribute("style");
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepSeconds(1);
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String locatorPath) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", find(driver,  locatorPath));
	}
	public void clickToElementByJS(WebDriver driver, String locatorPath, String...values) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", find(driver, getDynamicLocator(locatorPath, values)));
	}

	public void scrollToElement(WebDriver driver, String locatorPath) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", find(driver,  locatorPath));
	}

	public void sendkeyToElementByJS(WebDriver driver,  String locatorPath, String value) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('value', '" + value + "')", find(driver,  locatorPath));
	}

	public void removeAttributeInDOM(WebDriver driver,  String locatorPath, String attributeRemove) {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", find(driver,  locatorPath));
	}

	public String getElementValidationMessage(WebDriver driver, String locatorPath) {
		js = (JavascriptExecutor) driver;
		return (String) js.executeScript("return arguments[0].validationMessage;", find(driver,  locatorPath));
	}

	public boolean isImageLoaded(WebDriver driver, String locatorPath) {
		js = (JavascriptExecutor) driver;
		boolean status = (boolean) js.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				find(driver,  locatorPath));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void removeDisableAttributeOfElementByJS(WebDriver driver, String locatorPath) {
		js = (JavascriptExecutor) driver;
		element = find(driver,  locatorPath);
		js.executeScript("arguments[0].removeAttribute('disabled')", element);
	}

	public void waitElementVisible(WebDriver driver, String locatorPath) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byLocator(locatorPath)));
	}
	public void waitElementVisible(WebDriver driver, String locatorPath, String...values) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getDynamicLocator(locatorPath, values))));
	}

	public void waitElementInVisible(WebDriver driver, String locatorPath) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator( locatorPath)));
	}
	public void waitElementInVisible(WebDriver driver, String locatorPath, String...values) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(getDynamicLocator(locatorPath, values))));
	}

	public void waitElementPresence(WebDriver driver, String locatorPath) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(byLocator( locatorPath)));
	}

	public void waitElementClickable(WebDriver driver, String locatorPath) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.elementToBeClickable(byLocator( locatorPath)));
	}
	public void waitElementClickable(WebDriver driver, String locatorPath, String...values) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath(getDynamicLocator(locatorPath, values))));
	}

	public void waitAlertPresence(WebDriver driver) {
		explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void sleepSeconds(long timeout) {
		try {
			Thread.sleep(1000 * timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// type locator: id, class, name, css, xpath


	
	public String getDynamicLocator(String xpathValue, String... values) {
		xpathValue = String.format(xpathValue, (Object[])values);
		return xpathValue;
		
	}
	
	//uploadMutipleFile
//	/public String get
	public String getFolderUpload() {
		String filePath = GlobalConstants.UPLOAD_FILE_FOLDER;
		if(!isWindow()) {
			filePath.replaceAll("\\\\", "/");
		}
		return filePath;
	}
	public void uploadMutipleFiles(WebDriver driver, String...values) {
		String filePath = GlobalConstants.UPLOAD_FILE_FOLDER;
		if(!isWindow()) {
			filePath.replaceAll("\\\\", "/");
		}
		String fullFilePath = "";
		for (String fileName : values) {
			fullFilePath+=filePath+fileName+"\n";
		}
		//find(driver, AbstractPageUI.UPLOAD_FILE_TYPE).sendKeys(fullFilePath.trim());
	}
	
	public boolean isFileExist(String filePath) {
		File checkSaveFileSuccesfull = new File(filePath);
		if (checkSaveFileSuccesfull.exists()) {
			return true;
		}else {
			return false;
		}
	}
	public String getFilePathDownloadFolder() {
		String filePath = GlobalConstants.DOWNLOAD_FILE_FOLDER;
		if(!GlobalConstants.OS_NAME.toLowerCase().contains("win")) {
			filePath = filePath.replaceAll("\\\\", "/");
		}
		
		return filePath;
	}
	public String getFilePathUploadFolder() {
		String filePath = GlobalConstants.UPLOAD_FILE_FOLDER;
		if(!GlobalConstants.OS_NAME.toLowerCase().contains("win")) {
			filePath = filePath.replaceAll("\\\\", "/");
		}
		
		return filePath;
	}

	//check os_name	
	public boolean isWindow() {
		return GlobalConstants.OS_NAME.toLowerCase().contains("win");
	}
	
	//override implicit wait
	public void overrideImplicitWait(WebDriver driver, long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}



}
