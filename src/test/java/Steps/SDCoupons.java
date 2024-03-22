package Steps;

import Base.BaseUtility;
import Pages.Dashboard;
import Pages.Home;
import Pages.Login;
import Utility.ConfigFile;
import Utility.CredentialFile;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import javax.management.InvalidApplicationException;

public class SDCoupons {
    private final WebDriver _driver;

    private CredentialFile Credential;

    private Dashboard dashboard;

    public SDCoupons(BaseUtility base) {

        this._driver = base._driver;
        //config = new ConfigFile();
        Credential = new CredentialFile();
    }

    public Dashboard NavigateToDashBoard(WebDriver driver) {
        Home homePage = new Home(driver);
        Login login = homePage.NavigateToLogin();
        return login.SignIn(Credential.GetProperty("newuserId"), Credential.GetProperty("passWord"));
    }

    @When("try to claim coupons")
    public void try_to_claim_coupons() throws InvalidApplicationException, InterruptedException {
        dashboard = NavigateToDashBoard(_driver);
        dashboard.ClaimCoupons();
    }

    @When("try to claim coupons for 1Dollar")
    public void try_to_claim_coupons_for_1$() throws InvalidApplicationException, InterruptedException {
        dashboard = NavigateToDashBoard(_driver);
        dashboard.ClaimCoupons("1");
    }

    @When("try to claim coupons for 2Dollar")
    public void try_to_claim_coupons_for_2$() throws InvalidApplicationException, InterruptedException {
        dashboard = NavigateToDashBoard(_driver);
        dashboard.ClaimCoupons("2");
    }

    @Then("I should able to claim coupons successfully")
    public void i_should_able_to_claim_coupons_successfully() throws InterruptedException {
        dashboard.VerifyClaim();
    }
}
