package Pages;

import Utility.Helper;
import jdk.jfr.Threshold;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.InvalidApplicationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Dashboard {
    private final WebDriver _driver;

    public Dashboard(WebDriver driver) {
        this._driver=driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//span[text()='Dashboard']")
    public WebElement lblDashBoard;
    @FindBy(xpath = "//div[@class='inline-block align-bottom']/div")
    public WebElement lblProfile;
    @FindBy(xpath = "//div[contains(@class,'font-unilevershillingMedium')]//a[text()='Rewards']")
    public WebElement lnkRewards;
    @FindBy(xpath = "//a[text()='Donate a meal']")
    public WebElement lnkDonateMeal;
    @FindBy(xpath = "//button[text()='DONATE A MEAL']")
    public WebElement btnDonateAMeal;
    @FindBy(xpath = "//button[text()='Donate a Meal']")
    public WebElement btnDonateMeal;
    @FindBy(xpath = "//a[text()='Plant a tree']")
    public WebElement lnkPlantTree;
    @FindBy(xpath = "//button[text()='Plant A Tree']")
    public WebElement btnPlantTree;
    @FindBy(xpath = "//button[text()='PLANT A TREE']")
    public WebElement btnPlantATree;
    @FindBy(xpath = "//button[text()='OK']")
    public WebElement btnOK;
    @FindBy(xpath = "//img[@alt='Upload Receipt']")
    public WebElement imgUpload;
    @FindBy(xpath = "//button[text()='Browse for file']")
    public WebElement btnBrowse;
    @FindBy(xpath = "//button[text()='Use Image']")
    public WebElement btnUseImg;
    @FindBy(xpath = "//button[@id='couponstab']")
    public WebElement tabCoupons;
    //label[text()='Claim a $1 coupon']




    public Home LogOut() throws InvalidApplicationException, InterruptedException {
        while(!_driver.getCurrentUrl().contains("dashboard")){}
        Actions action = new Actions(_driver);
        action.moveToElement(lblProfile).click().build().perform();
        Thread.sleep(5000);
        action.moveToElement(_driver.findElement(By.xpath("//a[text()='Logout']")))
                .click().build().perform();
        while(_driver.findElements(By.xpath("//div[@class='modal-body ']")).size()==0){}
        Helper.clickItem(_driver,Helper.findElement(_driver,By.xpath("//button[text()='LOG OUT']"),10));
        return new Home(_driver);
    }

    public void DonateMeal() throws InvalidApplicationException, InterruptedException {
        while(!_driver.getCurrentUrl().contains("dashboard")){}
        Helper.clickItem(_driver,lnkRewards);
        WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(10));
        WebElement MealDonate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Donate a meal']")));
        while(_driver.findElements(By.cssSelector("#rewardstab")).size()==0){}
        Helper.click(_driver,MealDonate);
        while(_driver.findElements(By.xpath("//button[text()='Donate a Meal']")).size()==0){}
        Helper.clickItem(_driver,btnDonateMeal);
        while(_driver.findElements(By.xpath("//div[@class='flex justify-center items-center h-screen']")).size()==0){}
        Helper.clickItem(_driver,btnDonateAMeal);
        while(_driver.findElements(By.xpath("//h1[text()='Meal donated']")).size()==0){}
        Assert.assertTrue("Expected  'You have more rewards to claim' should Visible",
                _driver.findElements(By.xpath("//h2[text()='You have more rewards to claim']")).size()==1);

    }

    public void PlantTree() throws InvalidApplicationException, InterruptedException {
        while(!_driver.getCurrentUrl().contains("dashboard")){}
        Helper.clickItem(_driver,lnkRewards);
        while(_driver.findElements(By.cssSelector("#rewardstab")).size()==0){}
        Helper.click(_driver,lnkPlantTree);
        Helper.WaitForElementToExistAndVisible(btnPlantTree);
        Helper.clickItem(_driver,btnPlantTree);
        while(_driver.findElements(By.xpath("//div[@class='flex justify-center items-center h-screen']")).size()==0){}
        Helper.clickItem(_driver,btnPlantATree);
        while(_driver.findElements(By.xpath("//div[@class='flex justify-center items-center h-screen']")).size()==0){}
        Helper.click(_driver,btnOK);


    }
    public void UploadScript() throws InvalidApplicationException, InterruptedException, AWTException, IOException {
        while(!_driver.getCurrentUrl().contains("dashboard")){}
        Thread.sleep(5000);
        while(!Helper.IsElementExist(_driver,By.xpath("//img[@alt='Upload Receipt']"))){}
        _driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[1]/article/div/div[2]/div[2]/div[1]/div[1]/img")).click();

        //Helper.clickItem(_driver,imgUpload);
        while(_driver.findElements(By.xpath("//div[@class='flex justify-center items-center h-screen']")).size()==0){}
        Helper.click(_driver,btnBrowse);
        File filePath=new File("Utility/FileUpload.scpt");
        String pathLoc=filePath.getAbsolutePath();
        System.out.println(pathLoc);
        //_driver.findElement(By.xpath("//button[text()='Browse for file']/../input")).sendKeys(pathLoc);
        Runtime.getRuntime().exec("osascript "+"/Users/srikantmahapatro/automation/LeapWebAutomation/src/test/resources/FileUpload.scpt");
//        Helper.fileUpload(pathLoc);
       //Helper.uploadFile(pathLoc);
        //Helper.EnterText(_driver,btnBrowse,pathLoc);
//        _driver.switchTo()
//                .activeElement()
//                .sendKeys(filePath.getAbsolutePath());
        _driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Helper.click(_driver,btnUseImg);

    }

    public void VerifyReceiptSubmit(){
        while(_driver.findElements(By.cssSelector("div.modal-body")).size()==0){}
        Assert.assertTrue("Expected 'Receipt Submitted' msg should display",
                _driver.findElements(By.xpath("//h3[text()='Receipt Submitted']")).size()==1);
        Helper.click(_driver,btnOK);
    }

    public void ClaimCoupons() throws InvalidApplicationException, InterruptedException {
        while(!Helper.IsElementExist(_driver,By.xpath("//img[@alt='Upload Receipt']"))){}

        //Helper.clickItem(_driver,lnkRewards);
        _driver.findElement(By.xpath("//a[text()='Rewards']")).click();

        while(!Helper.IsElementExist(_driver,By.xpath("//*[@id=\"couponstab\"]"))){}
        tabCoupons.click();
        Thread.sleep(10000);
        //while(_driver.findElements(By.xpath("//label[text()='Claim a $1 coupon']/input")).size()==0){}
        //Helper.clickItem(_driver,_driver.findElements(By.xpath("//label[text()='Claim a $1 coupon']/input")).get(1));
        _driver.findElement(By.xpath("/html/body/div/div/main/article/div/div[2]/div[1]/article/div/div/article/article/div[1]/label[1]")).click();
        //_driver.findElements(By.xpath("//label[text()='Claim a $2 coupon']/input")).get(1).click();
        _driver.findElement(By.xpath("//*[@id=\"__next\"]/div/main/article/div/div[2]/div[1]/article/div/div/article/div[2]/button")).click();
        _driver.findElement(By.xpath("//*[@id=\"__next\"]/div/main/article/div/div[2]/div[2]/article[2]/section/main/div[1]/article/div[1]/button")).click();
    }

    public void VerifyClaim() throws InterruptedException {
        while(!Helper.IsElementExist(_driver,By.xpath("//button[text()='Claim Your Coupon']"))){}
        Thread.sleep(10000);
        _driver.findElement(By.xpath("//*[@id=\"__next\"]/div/main/article/div/div[2]/div[2]/article[2]/section/div/div/div/div/div[2]/div/button[1]")).click();
        Thread.sleep(10000);
        Assert.assertTrue("Expected Coupon use message should displayed",
                _driver.findElements(By.xpath("//h1[text()='You Claimed a Coupon']")).size()==1);
    }

}
