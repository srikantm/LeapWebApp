package Utility;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ElementUtil {
    private WebDriver driver;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }


    public WebElement getElement(String locatorType, String locatorValue) {
        return driver.findElement(getBy(locatorType, locatorValue));
    }

    public By getBy(String locatorType, String locatorValue) {

        By locator = null;

        switch (locatorType.toLowerCase()) {
            case "id":
                locator = By.id(locatorValue);
                break;
            case "name":
                locator = By.name(locatorValue);
                break;
            case "xpath":
                locator = By.xpath(locatorValue);
                break;
            case "css":
                locator = By.cssSelector(locatorValue);
                break;
            case "linktext":
                locator = By.linkText(locatorValue);
                break;

            default:
                break;
        }

        return locator;

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
                    System.out.println(String.format("%s: Waited for %d milliseconds.[%s]", new java.util.Date().toString(), i + iSleepTime, by));
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        String sException = String.format("Can't find %s after %d milliseconds.", by, timeout);
        throw new RuntimeException(sException);
    }

}
