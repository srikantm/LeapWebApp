package Utility;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import java.util.List;
import java.util.NoSuchElementException;

import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.management.InvalidApplicationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import com.opencsv.CSVWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class Helper {


	public static boolean isPresentInDropDown(String name,List<WebElement> li){

		for (WebElement element : li) {
			if (element.getText().equals(name)) 
				return true;
		}
		return false;
	}
	public static Wait<WebDriver> WaitForElement(WebDriver driver){
		return new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(60L))
				.pollingEvery(Duration.ofSeconds(5L))
				.ignoring(NoSuchElementException.class);
	}

	public static void NavigateToApp(WebDriver _driver, String envType) throws IOException {
		String url = getUrl(envType);
		_driver.navigate().to(url);
	}

	private static String getUrl(String envType) {
		ConfigFile config = new ConfigFile();
		if(envType.equals("QA"))
			return config.GetProperty("QaUrl").toString();
		else if (envType.equals("PROD"))
			return config.GetProperty("ProdUrl").toString();
		else if (envType.equals("DEV"))
			return config.GetProperty("DevUrl").toString();
		else
			return  null;
	}

	public static WebElement FindElement(WebDriver webDriver, By by, int timeout) {
		int iSleepTime = 1000;
		for (int i = 0; i < timeout; i += iSleepTime) {
			List<WebElement> oWebElements = webDriver.findElements(by);
			if (oWebElements.size() > 0) {
				return oWebElements.get(0);
			} else {
				try {
					Thread.sleep(iSleepTime);
					System.out.printf("%s: Waited for %d milliseconds.[%s]%n", new java.util.Date().toString(), i + iSleepTime, by);
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
			}
		}

		String sException = String.format("Can't find %s after %d milliseconds.", by, timeout);
		throw new RuntimeException(sException);
	}

	public static void SelectCheckBox(WebDriver driver, WebElement element) {

		if(!element.getAttribute("value").equals("on"))
		{
			Actions action = new Actions(driver);
			action.moveToElement(element).click().perform();
		}
	}


	public static void scrollAndClick(WebDriver driver, WebElement element) {

		try {
			scrollClick(driver,element);
		} catch (ElementClickInterceptedException ex) {
			Wait<WebDriver> wait =WaitForElement(driver); 
			wait.until(ExpectedConditions.visibilityOf(element));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		} catch (StaleElementReferenceException ex) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}
	}

	public static void scrollClick(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
		element.click();		
	}

	public static void selectFromDDn(WebDriver driver, String Value, WebElement element) {
		Wait<WebDriver> wait =WaitForElement(driver);
		wait.until(ExpectedConditions.visibilityOf(element));
		Select se = new Select(element);
		se.selectByValue(Value);
	}

	public static void selectFromDDn( WebElement element,String Value) {
		Select se = new Select(element);
		se.selectByValue(Value);
	}

	public static void selectFromDDn(WebDriver driver, WebElement element, String name) {
		List<WebElement> items=element.findElements(By.tagName("li"))
				.stream()
				.filter( s -> s.getText().trim().equals(name))
				.collect(Collectors.toList());

		if(items.size()==0)
			System.out.println("PLease eneter valid input");
		else
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",items.get(0));

	}

	public static void selectFromDDn(String name, List<WebElement> items) {
		for (WebElement element : items) {

			if (element.getText().equals(name)) {
				if (!element.isSelected())
					element.click();
				break;
			}
		}
	}

	public static void selectFromDDn_IgnoreCase(String name, List<WebElement> items) {		
		for (WebElement element : items) {			
			if (element.getText().equalsIgnoreCase(name)) {				
				if (!element.isSelected())				
					element.click();
				break;
			}
		}
	}

	public static void click(WebDriver driver, WebElement element) {
		Wait<WebDriver> wait =WaitForElement(driver);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			Actions action = new Actions(driver);
			action.moveToElement(element).click().perform();
		} catch (ElementClickInterceptedException ex) {
			wait.until(ExpectedConditions.visibilityOf(element));
			Actions action = new Actions(driver);
			action.moveToElement(element).click().perform();
		}
	}

	public static void clickItem(WebDriver driver, WebElement element) throws  InvalidApplicationException {
		Wait<WebDriver> wait =WaitForElement(driver);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Actions action = new Actions(driver);
			action.moveToElement(element).click().perform();
		} catch (StaleElementReferenceException ex) {
			scrollAndClick(driver, element);
		}
	}

	public static void doubleClick(WebDriver driver, WebElement element)  {
		Wait<WebDriver> wait =WaitForElement(driver);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			Actions action = new Actions(driver);
			action.doubleClick(element).perform();
		} catch (StaleElementReferenceException ex) {
			scrollAndClick(driver, element);
		}
	}


	public static boolean retryingFindClick(WebDriver driver, By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				findElement(driver, by, 10).click();
				//driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	public void HandleBadge(WebDriver driver){
		if(driver.findElements(By.xpath("//div[@class='flex justify-center items-center h-screen']")).size()>0){
			driver.findElement(By.xpath("//button[text()='OK']")).click();
		}
	}
	public static void EnterText(WebDriver driver, WebElement element, String value) {
		if(!value.isEmpty()){
			Wait<WebDriver> wait =WaitForElement(driver);

			try {
				element.clear();
				wait.until(ExpectedConditions.visibilityOf(element));
				element.sendKeys(value);
			} catch (ElementClickInterceptedException ex) {
				click(driver, element);
				element.sendKeys(value);
			}
		}
	}

	public static void EnterText(WebElement element, String value) {
		if(!value.isEmpty()){
			try {
				element.clear();
				element.sendKeys(value);
			} catch (ElementClickInterceptedException ex) {
				element.sendKeys(value);
			}
		}
	}
	public static String GetNodeValue(String nodeValue) throws IOException, SAXException, ParserConfigurationException {
		File file = new File("config.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		return  document.getElementsByTagName(nodeValue).item(0).getTextContent();

	}
	public static void scrollDownPage(WebDriver driver, int timesToScroll){
		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_DOWN);
		for (int i = 0; i < timesToScroll; i++) {
			action.perform();
		}
	}

	public static void downKeyOnPage(WebDriver driver, int timesToScroll) {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ARROW_DOWN);
		for (int i = 0; i < timesToScroll; i++) {
			action.perform();

		}
	}

	public static void WaitForPageLoad(WebDriver _driver, int timeOutSec) {
		Wait<WebDriver> wait =WaitForElement(_driver);
		wait.until(wd -> ((JavascriptExecutor) _driver)
				.executeScript("return document.readyState").equals("complete"));

	}

	public static void WaitForPageToLoad(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}

	public static void WaitForElementToExistAndVisible(WebElement element) throws InvalidApplicationException {
		int ct = 0;
		do {
			++ct;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		} while (!element.isDisplayed() && ct < 200);
		if (element.isDisplayed() && !element.isEnabled()) {
			throw new InvalidApplicationException(element + " must be visible and Enable");
		}
	}

	public static boolean IsElementExist(WebDriver _driver, By by) {
		List<WebElement> elements = _driver.findElements(by);
		return (elements.size() >= 1) ? true : false;
	}

	public static WebElement findElementIfExist(WebDriver _driver, By by) {
		List<WebElement> elements = _driver.findElements(by);
		return (elements.size() >= 1) ? elements.get(1) : null;
	}

	public static WebElement findElement(WebDriver _driver, By by, int timeOutInSec) {
		if (timeOutInSec <= 0) return _driver.findElement(by);
		Wait<WebDriver> wait =WaitForElement(_driver);
		return wait.until(drv -> drv.findElement(by));
	}

	public static void waitForPageLoad(WebDriver _driver) {

		Wait<WebDriver> wait = new WebDriverWait(_driver, Duration.ofSeconds(10));
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return String
						.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}

	public static List<WebElement> findElements(WebDriver _driver, By by, int timeOutInSec) {

		if (timeOutInSec <= 0) return _driver.findElements(by);
		Wait<WebDriver> wait =WaitForElement(_driver);
		return wait.until(drv -> drv.findElements(by));
	}

//	public static String getUrl() throws IOException {
//		return  configProperties.initializeProperty().get("url").toString();
//	}

	public static void SetValue(WebDriver driver, WebElement element, String value) {
		Wait<WebDriver> wait =WaitForElement(driver);
		try {
			element.clear();
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys("value",value);
		} catch (ElementClickInterceptedException ex) {
			click(driver, element);
			element.sendKeys("value",value);
		}
	}

	public static void JsSetValue(WebDriver driver, WebElement element, String value) {
		Wait<WebDriver> wait =WaitForElement(driver);

		try {

			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].value='"+value+"';", element);
		} catch (ElementClickInterceptedException ex) {
			click(driver, element);
			element.sendKeys("value",value);
		}
	}

	public static void selectFromList(String name, List<WebElement> items) {
		for (WebElement element : items) {

			if (element.getText().equals(name)) {
				if (!element.isSelected())
					element.click();
				break;
			}
		}
	}





	public static boolean isAttributePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null){
				result = true;
			}
		} catch (Exception e) {}

		return result;
	}

	public static void CreateCSV() throws IOException {
		String csvFile = "TestCases.csv";
		CSVWriter cw = new CSVWriter(new FileWriter(csvFile));
		String[] line = {"LinkID", "Url","Response"};
		cw.writeNext(line);
		cw.close();
	}

	public static void WriteCSV(String LinkId, String Url, String response) throws IOException {
		String csvFile = "TestCases.csv";
		CSVWriter cw = new CSVWriter(new FileWriter(csvFile, true));
		String[] line = {LinkId, Url,response};
		cw.writeNext(line);
		cw.close();
	}

	public static void WriteToCSV(String Url, String response) throws IOException {
		String csvFile = "TestCases.csv";
		CSVWriter cw = new CSVWriter(new FileWriter(csvFile, true));
		String[] line = { Url,response};
		cw.writeNext(line);
		cw.close();
	}

	public static void WriteCSV(String LinkId, String Url) throws IOException {
		String csvFile = "TestCases.csv";
		CSVWriter cw = new CSVWriter(new FileWriter(csvFile, true));
		String[] line = {LinkId, Url};
		cw.writeNext(line);
		cw.close();
	}

	public static Boolean checkLinks(String Source) {
		int Response = 0;
		try {
			if (Source.equals("#"))
				return false;
			HttpURLConnection Connect = (HttpURLConnection) (new URL(Source)).openConnection();
			Response = Connect.getResponseCode();
			System.out.println("Responsecode: " + Response);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!((Response + "").startsWith("4") || (Response + "").startsWith("5"))) {
			System.out.println("Valid Source:" + ((Source == null) ? "null" : Source));
			return true;
		} else {
			System.out.println("Invalid Source:" + ((Source == null) ? "null" : Source));
			return false;
		}
	}
	public static void fileUpload (String path) throws AWTException {

		StringSelection strSelection = new StringSelection(path);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(strSelection, null);
		Robot robot = new Robot();
		robot.delay(300);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(200);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}

	public static void setClipboardData(String string) {
		//StringSelection is a class that can be used for copy and paste operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public static void uploadFile(String fileLocation) {
		try {
			//Setting clipboard with file location
			setClipboardData(fileLocation);
			//native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);


			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
	

}

