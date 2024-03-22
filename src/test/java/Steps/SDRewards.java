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

public class SDRewards {
    private final WebDriver _driver;
    private Login login;
    private Dashboard dashboard;

    private CredentialFile Credential;

    ConfigFile config;

    public SDRewards(BaseUtility base) {

        this._driver = base._driver;
        //config = new ConfigFile();
        Credential = new CredentialFile();

    }

    private Dashboard NavigateToDashBoard(WebDriver driver) {
        Home homePage = new Home(driver);
        login = homePage.NavigateToLogin();
        //return login.SignIn(config.GetProperty("id"), config.GetProperty("passWord"));
        return login.SignIn(Credential.GetProperty("newuserId"), Credential.GetProperty("passWord"));

    }

    @When("try to donate a meal in Do Good Rewards section")
    public void tryToDonateAMealInDoGoodRewardsSection() {

        dashboard = NavigateToDashBoard(_driver);
    }

    @Then("I should able to do donate a meal")
    public void iShouldAbleToDoDonateAMeal() throws InvalidApplicationException, InterruptedException {
        dashboard.DonateMeal();
    }

    @When("try to plant a tree for the rewards")
    public void tryToPlantATreeForTheRewards() {
        dashboard = NavigateToDashBoard(_driver);
    }

    @Then("I should able use plant a tree")
    public void iShouldAbleUsePlantATree() throws InvalidApplicationException, InterruptedException {
        dashboard.PlantTree();
    }
}
