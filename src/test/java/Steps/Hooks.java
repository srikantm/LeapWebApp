package Steps;


import Base.BaseUtility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.ByteArrayInputStream;
import java.util.Collections;

public class Hooks  extends BaseUtility {

    BaseUtility _utility;
    WebDriver _driver;
    public Hooks(BaseUtility utility){
        this._utility=utility;
    }
    public WebDriver InitDriver() {
        String _browserName="Chrome";
        switch(_browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY);
                options.addArguments("enable-automation");
                //options.addArguments("--headless");
                //options.addArguments("--window-size=1920,1080");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-extensions");
                options.addArguments("--dns-prefetch-disable");
                options.addArguments("--disable-gpu");
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                _driver = new ChromeDriver(options);
                _driver.manage().deleteAllCookies();
                break;

            default:
                System.out.println("Please pass correct Browser name");
                break;
        }
        _driver.manage().window().maximize();
        return _driver;
    }
    @Before
    public void Initialize(){
        _utility._driver=InitDriver();

    }

    @After
    public void TearDown(Scenario scenario){
        if(scenario.isFailed()){
            byte[] screenshot=((TakesScreenshot)_utility._driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(scenario.getName(),new ByteArrayInputStream(screenshot));
        }
        if(_driver!=null)
            _driver.quit();
    }
}
