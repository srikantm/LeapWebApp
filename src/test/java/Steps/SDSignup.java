package Steps;

import Base.BaseUtility;
import Pages.Home;
import Pages.Login;
import Pages.SignUp;
import Utility.Helper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Random;

public class SDSignup {

    private final WebDriver _driver;
    SignUp signUp;
    private String UserName;
    private String Password;

    public SDSignup(BaseUtility utility){
        this._driver=utility._driver;
    }
    @Given("LeapReward url is up and running")
    public void UrlIsUpAndRunning() throws IOException, ParserConfigurationException, SAXException {
        Helper.NavigateToApp(_driver, Helper.GetNodeValue("Environment"));
        UserName="automationrun18+w"+new Random().nextInt() +"@gmail.com";
        Password="Test@1234";
    }

    @When("try to signup with valid data")
    public void EnterData() throws InterruptedException {
        Home homePage=new Home(_driver);
        signUp = homePage.NavigateToSignUp();
        signUp.RegisterUser(UserName,Password);
        signUp.Verify();
    }
    @Then("I should able to sin up successfully")
    public void ValidateSignup() throws IOException, ParserConfigurationException, SAXException {
        System.out.println("Verified");
        //_driver.close();
        Helper.NavigateToApp(_driver, Helper.GetNodeValue("Environment"));
        Home home=new Home(_driver);
        home.NavigateToLogin();
        Login logIn=new Login(_driver);
        logIn.SignIn(UserName,Password);
        while(!_driver.getCurrentUrl().contains("dashboard")){}
        Assert.assertTrue("Login Should done sucessfully",_driver.getTitle().equals("Shop For Good Shop, Earn, Save, and Do Good | Leap Rewards"));
    }
}
