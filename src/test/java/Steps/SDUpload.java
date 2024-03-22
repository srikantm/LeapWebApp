package Steps;

import Base.BaseUtility;
import Pages.Dashboard;
import Pages.Home;
import Pages.Login;
import Pages.SignUp;
import Utility.ConfigFile;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import javax.management.InvalidApplicationException;
import java.awt.*;
import java.io.IOException;

public class SDUpload {
    private final WebDriver _driver;
    private ConfigFile config;
    private Dashboard dashboard;

    public SDUpload(BaseUtility utility) {

        this._driver = utility._driver;
        config = new ConfigFile();
    }

    private Dashboard NavigateToDashBoard(WebDriver driver) {
        Home homePage = new Home(driver);
        Login login = homePage.NavigateToLogin();
        return login.SignIn(config.GetProperty("id"), config.GetProperty("passWord"));
    }

    @When("try to upload a script")
    public void try_to_upload_a_script() throws InvalidApplicationException, InterruptedException, AWTException, IOException {
        dashboard = NavigateToDashBoard(_driver);
        dashboard.UploadScript();
    }

    @Then("I should able to upload script sucessfully")
    public void i_should_able_to_upload_script_sucessfully() {
        dashboard.VerifyReceiptSubmit();
    }
}
