package Steps;

import Base.BaseUtility;
import Pages.Dashboard;
import Pages.Login;
import Utility.ConfigFile;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class SDHomes {
    private final WebDriver _driver;
    ConfigFile config;
    Dashboard dashboard;
    int itemCount = 0;

    public SDHomes(BaseUtility base) {
        this._driver = base._driver;
        config = new ConfigFile();
    }

    @When("try to navigate to Most popular See All section")
    public void try_to_navigate_to_most_popular_see_all_section() {
        Login login = new Login(_driver);
        dashboard = login.SignIn(config.GetProperty("id"), config.GetProperty("passWord"));
        //dashboard.NavigateToSeeAll_MostPopular();
    }

    @Then("I should able to navigate successfully")
    public void i_should_able_to_navigate_successfully() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
