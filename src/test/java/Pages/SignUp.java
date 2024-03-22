package Pages;

import Services.GmailRead;
import Utility.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class SignUp {
    private final WebDriver driver;
    @FindBy(xpath = "//*[@id='email']")
    public WebElement inputEmail;

    @FindBy(xpath = "//*[@id='password']")
    public WebElement inputPassword;

    @FindBy(xpath = "//*[@id='repassword']")
    public WebElement inputRepassword;

    @FindBy(xpath = "//*[@id='promocode']")
    public WebElement inputPromocode;

    @FindBy(xpath = "//*[text() = 'Next']")
    public WebElement buttonNext;

    @FindBy(id = "firstname")
    public WebElement inputFirstname;

    @FindBy(xpath = "//*[@id='last-name']")
    public WebElement inputLastName;

    @FindBy(xpath = "//*[@id='postal-code']")
    public WebElement inputPostalCode;

    @FindBy(xpath = "//input[@placeholder='YYYY-MM-DD']")
    public WebElement inputDateBirth;

    @FindBy(xpath = "//input[@name='agreeToTerms']")
    public WebElement inputAgreeTerms;

    @FindBy(xpath = "//input[@name='receiveUpdates']")
    public WebElement inputReceiveUpdates;

    @FindBy(xpath = "//*[text() = 'Register']")
    public WebElement buttonRegister;

    @FindBy(xpath = "//button[text()='Verify']")
    public WebElement buttonVerify;

    public SignUp(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void RegisterUser(String userName, String Password) throws IOException {
        Helper.EnterText(driver, inputEmail, userName);
        Helper.EnterText(driver, inputPassword, Password);
        Helper.EnterText(driver, inputRepassword, Password);
        System.out.println(userName);
        Properties props = new Properties();
        props.put("newuserId", userName);
        props.put("passWord", Password);
        String path = "//Users//srikantmahapatro//Automation//ULwebapp//webleapautomation//credential.properties";
        FileOutputStream outputStrem = new FileOutputStream(path);
        props.store(outputStrem, "updated the value in config file");
        //System.out.println("Properties file created......");
        Helper.EnterText(driver, inputPromocode, "");
        Helper.click(driver, buttonNext);
        while (driver.findElements(By.xpath("//label[text()='First Name']")).size() == 0) {
        }
        Helper.EnterText(driver, inputFirstname, "Test");
        Helper.EnterText(driver, inputLastName, "Web");
        Helper.EnterText(driver, inputPostalCode, "A1A1A1");
        Helper.EnterText(driver, inputDateBirth, "1982-11-05");
        inputDateBirth.sendKeys(Keys.TAB);
        Helper.click(driver, inputAgreeTerms);
        Helper.click(driver, inputReceiveUpdates);
        Helper.click(driver, buttonRegister);
        while (driver.findElements(By.xpath("//h1[text()='Enter Code']")).size() == 0) {
        }

    }

    public void Verify() {
        GmailRead readGmail = new GmailRead();
        String otpCode = readGmail.getVerificationCode();
        List<WebElement> item = driver.findElements(By.xpath("//input[@class='custom-input text-black']"));
        for (int i = 0; i < item.size(); i++) {
            Helper.EnterText(driver, item.get(i), String.valueOf(otpCode.charAt(i)));
        }
        while (!buttonVerify.isEnabled()) {
        }
        Helper.click(driver, buttonVerify);

    }

    public void CompleteRegistration() {
        while (driver.findElements(By.xpath("//label[text()='First name']")).size() == 0) {
        }
        Helper.EnterText(driver, inputFirstname, "Test");
        Helper.EnterText(driver, inputLastName, "Web");
        Helper.EnterText(driver, inputPostalCode, "A1A1A1");
        Helper.EnterText(driver, inputDateBirth, "1982-11-05");
        inputDateBirth.sendKeys(Keys.TAB);
        Helper.click(driver, inputAgreeTerms);
        Helper.click(driver, inputReceiveUpdates);
        Helper.click(driver, buttonRegister);
        while (driver.findElements(By.xpath("//h1[text()='Enter Code']")).size() == 0) {
        }

    }
}