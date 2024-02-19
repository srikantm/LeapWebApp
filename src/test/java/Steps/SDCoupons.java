package Steps;

import Base.BaseUtility;
import Pages.Dashboard;
import Pages.Home;
import Pages.Login;
import Utility.ConfigFile;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import javax.management.InvalidApplicationException;

public class SDCoupons {
    private final WebDriver _driver;
    private ConfigFile config;
    private  Dashboard dashboard;

    public SDCoupons(BaseUtility base) {

        this._driver=base._driver;
        config= new ConfigFile();
    }

    private Dashboard NavigateToDashBoard(WebDriver driver){
        Home homePage=new Home(driver);
        Login login = homePage.NavigateToLogin();
        return login.SignIn(config.GetProperty("id"),config.GetProperty("passWord") );
    }

    @When("try to claim coupons")
    public void try_to_claim_coupons() throws InvalidApplicationException, InterruptedException {
        dashboard=NavigateToDashBoard(_driver);
        dashboard.ClaimCoupons();
    }
    @Then("I should able to claim coupons sucessfully")
    public void i_should_able_to_claim_coupons_sucessfully() throws InterruptedException {
        dashboard.VerifyClaim();
    }
}
