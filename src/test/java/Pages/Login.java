package Pages;


import Utility.ConfigFile;
import Utility.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import static Utility.Helper.WaitForElement;


public class Login {
    private WebDriver driver;
    private ConfigFile config;
    @FindBy(xpath = "//a[@href='sign-up']")
    public WebElement lnkCreateAccount;
    @FindBy(xpath = "//input[@id='email']")
    public WebElement txtEmail;
    @FindBy(xpath = "//input[@id='password']")
    public WebElement txtPassword;


    @FindBy(xpath = "//button[text()='Sign in']")
    public WebElement btnSignIn;

    public Login(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);

    }

    public void navCreateAccount() {
        while(driver.findElements(By.xpath("//img[@alt='loading']")).stream().count()>0){}
        Helper.click(driver,lnkCreateAccount);
    }

    public Dashboard SignIn(String userId, String passWord) {
        Helper.EnterText(driver,txtEmail,userId);
        Helper.EnterText(driver,txtPassword,passWord);
        Helper.click(driver,btnSignIn);
        while(driver.findElements(By.xpath("//p[text()='Discover products']")).size()==0){}
        return new Dashboard(driver);
    }

}