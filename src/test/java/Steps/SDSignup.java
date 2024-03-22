package Steps;

import Base.BaseUtility;
import Pages.Home;
import Pages.Login;
import Pages.Settings;
import Pages.SignUp;
import Utility.Helper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.xml.sax.SAXException;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.InvalidApplicationException;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

public class SDSignup {

    private final WebDriver _driver;
    SignUp signUp;
    private String UserName;
    private String Password;

    public SDSignup(BaseUtility utility) {
        this._driver = utility._driver;
    }

    @Given("LeapReward url is up and running")
    public void UrlIsUpAndRunning() throws IOException, ParserConfigurationException, SAXException {
        Helper.NavigateToApp(_driver, Helper.GetNodeValue("Environment"));
        UserName = "automationrun18+w" + new Random().nextInt() + "@gmail.com";
        Password = "Testing$1";
    }

    @When("try to signup with valid data")
    public void EnterData() throws InterruptedException, IOException {
        Home homePage = new Home(_driver);
        signUp = homePage.NavigateToSignUp();
        signUp.RegisterUser(UserName, Password);
        signUp.Verify();

    }

    @Then("I should able to sign up successfully")
    public void ValidateSignup() throws IOException, ParserConfigurationException, SAXException, InterruptedException {

        Helper.NavigateToApp(_driver, Helper.GetNodeValue("Environment"));
        Home home = new Home(_driver);
        home.NavigateToLogin();
        Login logIn = new Login(_driver);
        Settings settings = new Settings(_driver);
        var dashboard = logIn.firstSignIn(UserName, Password);
        //Assert.assertTrue("Login Should be done successfully", _driver.getTitle().equals("Shop For Good Shop, Earn, Save, and Do Good | Leap Rewards"));

        try {
            dashboard.NavigateToSettingPage();
        } catch (InvalidApplicationException e) {
            throw new RuntimeException(e);
        }
        WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(60));
        while (_driver.findElements(By.xpath("//h3[text()='Account']")).size() == 0) {
        }
        Assert.assertTrue("Expected Email should Present in Setting",
                Helper.findElement(_driver, By.xpath("//div[label='Email']//input"), 10).getAttribute("value").trim().equals(UserName));


        while (_driver.findElements(By.xpath("//h3[text()='Account']")).size() == 0) {
        }
        Helper.retryingFindClick(_driver, By.xpath("//img[@alt='arrow']"));
        while (_driver.findElements(By.xpath("//h3[text()='Name']")).size() == 0) {
        }
        Helper.EnterText(_driver, Helper.findElement(_driver, By.xpath("//input[@id='firstName']"), 20), "");
        Helper.EnterText(_driver, Helper.findElement(_driver, By.xpath("//input[@id='firstName']"), 20), "TestWeb");
        Helper.EnterText(_driver, Helper.findElement(_driver, By.xpath("//input[@id='lastName']"), 20), "");
        Helper.EnterText(_driver, Helper.findElement(_driver, By.xpath("//input[@id='lastName']"), 20), "Automation");
        try {
            Helper.clickItem(_driver, Helper.findElement(_driver, By.xpath("//button[text()='Update']"), 20));
        } catch (InvalidApplicationException e) {
            throw new RuntimeException(e);
        }
        while (Helper.findElements(_driver, By.xpath("//h3[text()='Account']"), 20).size() == 0) {
        }
        if (!Helper.findElements(_driver, By.xpath("//button[normalize-space()='Got it']"), 60).isEmpty()) {
            Helper.click(_driver,_driver.findElement( By.xpath("//button[normalize-space()='Got it']")));
        }

        try {
            dashboard.LogOut();
        } catch (InvalidApplicationException e) {
            throw new RuntimeException(e);
        }


    }
}
