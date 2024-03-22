package Steps;

import Base.BaseUtility;
import Pages.Dashboard;
import Pages.Home;
import Pages.Login;
import Pages.ProductList;
import Utility.ConfigFile;
import Utility.CredentialFile;
import Utility.Helper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.InvalidApplicationException;
import java.net.MalformedURLException;
import java.time.Duration;

public class SDSignIn {

    private final WebDriver _driver;

    private CredentialFile Credential;

    Login login;
    Dashboard dashboard;
    ConfigFile config;

    public SDSignIn(BaseUtility base) {

        this._driver = base._driver;
        config = new ConfigFile();
        Credential = new CredentialFile();

    }

    @When("try to sign in with user id and password")
    public void try_to_sign_in_with_user_id_and_password() {
        Home homePage = new Home(_driver);
        login = homePage.NavigateToLogin();
        //dashboard = login.SignIn(config.GetProperty("id"), config.GetProperty("passWord"));
        dashboard = login.SignIn(Credential.GetProperty("newuserId"), Credential.GetProperty("passWord"));

    }

    @Then("I should able to logged in to the application")
    public void i_should_able_to_logged_in_to_the_application() throws InvalidApplicationException, InterruptedException {
        System.out.println("Login Success");
        /*WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='tooltip show bs-tooltip-auto']")));
        Helper.clickItem(_driver,_driver.findElement(By.xpath("//*[@class='tooltip-inner']//button")));*/
        dashboard.LogOut();
    }

}
