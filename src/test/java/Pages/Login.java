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

import javax.management.InvalidApplicationException;

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
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public void navCreateAccount() {
        while (driver.findElements(By.xpath("//img[@alt='loading animation']")).stream().count() > 0) {
        }
        Helper.click(driver, lnkCreateAccount);
    }

    public Dashboard SignIn(String userId, String passWord) {
        Helper.EnterText(txtEmail, userId);
        Helper.EnterText(txtPassword, passWord);
        Helper.click(driver, btnSignIn);

        Helper.waitForPageLoad(driver);
        while (!driver.findElements(By.xpath("//img[@alt='loading animation']")).isEmpty()) {}
        while(driver.findElements(By.xpath("//span[text()=' Log in']")).size()>0){}
        while (driver.findElements(By.xpath("//img[@alt='Upload Receipt']")).isEmpty()) {}
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!Helper.findElements(driver, By.xpath("//button[normalize-space()='Got it']"), 60).isEmpty()) {
            Helper.click(driver,driver.findElement( By.xpath("//button[normalize-space()='Got it']")));
        }
        return new Dashboard(driver);
    }

    public Dashboard firstSignIn(String userId, String passWord) throws InterruptedException {
        Helper.EnterText(txtEmail, userId);
        Helper.EnterText(txtPassword, passWord);
        Helper.click(driver, btnSignIn);
        Helper.waitForPageLoad(driver);
        //new SignUp(driver).CompleteRegistration();
        while (!driver.findElements(By.xpath("//img[@alt='loading animation']")).isEmpty() ){}
        while(driver.findElements(By.xpath("//span[text()=' Log in']")).size()>0){}


        while (driver.findElements(By.xpath("//p[normalize-space()='Selected by Leap Rewards']")).size()==0) {}
        Wait<WebDriver> wait =WaitForElement(driver);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[normalize-space()='Selected by Leap Rewards']"))));
        if (!Helper.findElements(driver, By.xpath("//button[normalize-space()='Got it']"), 60).isEmpty()) {
            Helper.click(driver,driver.findElement( By.xpath("//button[normalize-space()='Got it']")));
        }
        Thread.sleep(5000);
//        try {
//            Helper.WaitForElementToExistAndVisible(Helper.findElement(driver, By.xpath("//div[@class='inline-block align-bottom']"), 10));
//        } catch (InvalidApplicationException e) {
//            throw new RuntimeException(e);
//        };

        return new Dashboard(driver);
    }

}