package Steps;

import Base.BaseUtility;
import Pages.Dashboard;
import Pages.Home;
import Pages.Settings;
import Utility.ConfigFile;
import Utility.CredentialFile;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import javax.management.InvalidApplicationException;

public class SDSettings {

    private final WebDriver _driver;
    private ConfigFile config;

    private CredentialFile Credential;

    private Dashboard dashboard;

    public SDSettings(BaseUtility base) {

        this._driver = base._driver;
        config = new ConfigFile();
        Credential = new CredentialFile();
    }

    private void NavSettingsPage() {
        Home homePage = new Home(_driver);
        var login = homePage.NavigateToLogin();
        dashboard = login.SignIn(Credential.GetProperty("newuserId"), Credential.GetProperty("passWord"));
        try {
            dashboard.NavigateToSettingPage();
        } catch (InvalidApplicationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @When("try to navigate Terms and Conditions in Settings")
    public void try_to_navigate_terms_and_conditions_in_settings() {
        Home homePage = new Home(_driver);
        var login = homePage.NavigateToLogin();
        //dashboard = login.SignIn(config.GetProperty("id"), config.GetProperty("passWord"));
        dashboard = login.SignIn(Credential.GetProperty("newuserId"), Credential.GetProperty("passWord"));
        try {
            dashboard.NavigateToSettingPage();
        } catch (InvalidApplicationException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @When("try to navigate Privacy Policy in Settings")
    public void try_to_navigate_Privacy_Policy_in_settings() {
        Home homePage = new Home(_driver);
        var login = homePage.NavigateToLogin();
        dashboard = login.SignIn(Credential.GetProperty("newuserId"), Credential.GetProperty("passWord"));
        try {
            dashboard.NavigateToSettingPage();
        } catch (InvalidApplicationException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Then("I should able to navigate to the respective Term Condition Page")
    public void i_should_able_to_navigate_to_the_respective_page() {
        var setting = new Settings(_driver);
        setting.ValidateTermsConditions();
        try {
            dashboard.LogOut();
        } catch (InvalidApplicationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("I should able to navigate to the respective Privacy Page")
    public void navigatePrivacyPage() {
        var setting = new Settings(_driver);
        setting.ValidatePrivacyPolicy();
        try {
            dashboard.LogOut();
        } catch (InvalidApplicationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @When("try to change password in Settings")
    public void try_to_change_password_in_settings() {
        Home homePage = new Home(_driver);
        var login = homePage.NavigateToLogin();
        dashboard = login.SignIn(Credential.GetProperty("newuserId"), Credential.GetProperty("passWord"));
        try {
            dashboard.NavigateToSettingPage();
        } catch (InvalidApplicationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("I should able to change the password for the account")
    public void i_should_able_to_change_the_password_for_the_account() {
        var setting = new Settings(_driver);

        setting.VerifyChangePassword(config.GetProperty("passWord"), "P@ssW0rd");
        try {
            dashboard.LogOut();
        } catch (InvalidApplicationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @When("try to change name in Settings")
    public void try_to_change_name_in_settings() {
        NavSettingsPage();

    }

    @Then("I should able to change the name for the account")
    public void i_should_able_to_change_the_name_for_the_account() {
        var setting = new Settings(_driver);

        setting.VerifyChangeName("Srikant", "Mahapatro");
        setting.VerifyChangeName(config.GetProperty("fname"),
                config.GetProperty("lname"));
        try {
            dashboard.LogOut();
        } catch (InvalidApplicationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
