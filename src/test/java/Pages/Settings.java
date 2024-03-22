package Pages;

import Utility.Helper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.InvalidApplicationException;
import java.time.Duration;
import java.util.Objects;

public class Settings {

    private final WebDriver driver;

    @FindBy(xpath = "//a[@class='hover:underline block translate' and text()='Terms & Conditions']")
    public WebElement lblTermsConditions;

    @FindBy(xpath = "//a[@class='hover:underline block translate' and text()='Privacy Policy']")
    public WebElement lblPrivacyPolicy;

    public Settings(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public void VerifyChangePassword(String password, String newPassword) {
        try {
            while (Helper.findElements(driver, By.xpath("//h3[text()='Account']"), 20).size() == 0) {
            }
            Helper.clickItem(driver, Helper.findElement(driver, By.xpath("//div[text()='Change Password']"), 20));
            while (driver.findElements(By.xpath("//h3[normalize-space()='Change password']")).size() == 0) {
            }
            Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='currentpassword']"), 10), password);
            Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='password']"), 10), newPassword);
            Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='confirmPassword']"), 10), newPassword);
            Helper.clickItem(driver, Helper.findElement(driver, By.xpath("//button[text()='Update']"), 20));
            Thread.sleep(3000);
            Assert.assertTrue(driver.findElements(By.xpath("//div[text()='Password is successfully reset']")).size() == 1);
            Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='currentpassword']"), 10), newPassword);
            Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='password']"), 10), password);
            Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='confirmPassword']"), 10), password);
            Helper.clickItem(driver, Helper.findElement(driver, By.xpath("//button[text()='Update']"), 20));
        } catch (InvalidApplicationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    public void ValidateTermsConditions() {
//        Helper.click(driver, lblTermsConditions);
//        Helper.waitForPageLoad(driver);
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        wait.until(ExpectedConditions.visibilityOf(Helper.findElement(driver, By.xpath("//h1[normalize-space()='Leap Rewards TERMS & CONDITIONS']"), 10)));
//        Assert.assertTrue("Expected Page should Navigate to Terms & Conditions Page",
//                driver.getCurrentUrl().contains("terms-and-conditions"));
//        driver.navigate().back();
//    }
    public void ValidateTermsConditions() {
        Helper.click(driver, lblTermsConditions);
        Helper.waitForPageLoad(driver);
        while (!Objects.equals(driver.getTitle(), "Terms and conditions | Leap Rewards")) {}
        try {
            Helper.WaitForElementToExistAndVisible(Helper.findElement(
                    driver,By.xpath("//span[normalize-space()='Terms and conditions']"),60
            ));
        } catch (InvalidApplicationException e) {
            throw new RuntimeException(e);
        }
        while (driver.findElements(By.xpath("//img[@alt='loading animation']")).size() > 0) {}
        Assert.assertTrue("Expected Page should Navigate to Terms & Conditions Page",
                driver.getCurrentUrl().contains("terms-and-conditions"));
        driver.navigate().back();
    }

//    public void ValidatePrivacyPolicy() {
//
//        Helper.click(driver, lblPrivacyPolicy);
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        wait.until(ExpectedConditions.visibilityOf(Helper.findElement(driver, By.xpath("//h1[normalize-space()='UNILEVER PRIVACY PRINCIPLES']"), 10)));
//        Assert.assertTrue("Expected Page should Navigate to Privacy Policy Page",
//                driver.getCurrentUrl().contains("privacy-policy"));
//        driver.navigate().back();
//    }

    public void ValidatePrivacyPolicy() {

        Helper.click(driver, lblPrivacyPolicy);
        while (!Objects.equals(driver.getTitle(), "Privacy Policy | Leap Rewards")) {}
        try {
            Helper.WaitForElementToExistAndVisible(Helper.findElement(
                    driver,By.xpath("//span[normalize-space()='Privacy policy']"),60
            ));
        } catch (InvalidApplicationException e) {
            throw new RuntimeException(e);
        }
        while (driver.findElements(By.xpath("//img[@alt='loading animation']")).size() > 0) {}

        Assert.assertTrue("Expected Page should Navigate to Privacy Policy Page",
                driver.getCurrentUrl().contains("privacy-policy"));
        driver.navigate().back();
    }
    public void VerifyChangeName(String firstName, String lastName) {
        while (driver.findElements(By.xpath("//h3[text()='Account']")).size() == 0) {
        }
        Helper.retryingFindClick(driver, By.xpath("//img[@alt='arrow']"));
        while (driver.findElements(By.xpath("//h3[text()='Name']")).size() == 0) {
        }
        Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='firstName']"), 20), "");
        Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='firstName']"), 20), firstName);
        Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='lastName']"), 20), "");
        Helper.EnterText(driver, Helper.findElement(driver, By.xpath("//input[@id='lastName']"), 20), lastName);
        try {
            Helper.clickItem(driver, Helper.findElement(driver, By.xpath("//button[text()='Update']"), 20));
        } catch (InvalidApplicationException e) {
            throw new RuntimeException(e);
        }
        while (Helper.findElements(driver, By.xpath("//h3[text()='Account']"), 20).size() == 0) {
        }
        Assert.assertTrue("Expected name should be changed",
                Helper.findElement(driver, By.xpath("//label[text()='Name']/parent::div//input"), 20).getAttribute("value").trim().equals(firstName + " " + lastName));


    }
}
