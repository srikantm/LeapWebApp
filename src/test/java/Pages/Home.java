package Pages;

import Utility.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.InvalidApplicationException;
import java.time.Duration;
import java.util.List;

public class Home {

    private final WebDriver _driver;
    private final WebDriverWait wait;

    public Home(WebDriver driver) {
        this._driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(_driver, Duration.ofSeconds(10));
    }


    private void AcceptCookies() {
        Helper.waitForPageLoad(_driver);
        while (_driver.findElements(By.xpath("//img[@alt='loading animation']")).size() > 0) {
        }
        List<WebElement> items = _driver.findElements(By.xpath("//*[text() = 'Accept']"));
        if (!items.isEmpty()) {
            items.get(0).click();
            while(_driver.findElements(By.xpath("//*[text() = 'Accept']")).size()!=0){}
            if (!_driver.findElements(By.xpath("//div[@id='_evidon_banner']")).isEmpty())
                Helper.retryingFindClick(_driver,By.xpath("//button[@id='_evh-ric-c']"));
                //_driver.findElement(By.xpath("//button[@id='_evh-ric-c']")).click();
        }
    }

    public Login NavigateToLogin() {
        AcceptCookies();
        while(_driver.findElements(By.xpath("//span[text()=' Log in']")).size()==0){}
        Helper.retryingFindClick(_driver,By.xpath("//span[text()=' Log in']"));
        while (_driver.findElements(By.xpath("//h1[text()='Sign in']")).isEmpty()) {}
        return new Login(_driver);
    }
    public SignUp NavigateToSignUp() {
        AcceptCookies();
        Helper.doubleClick(_driver, Helper.findElement(_driver, By.xpath("//a[text()='CREATE AN ACCOUNT']"), 10));


        while (_driver.findElements(By.xpath("//h1[text()='SIGN UP & ']")).isEmpty()) {}
        return new SignUp(_driver);
    }
}
