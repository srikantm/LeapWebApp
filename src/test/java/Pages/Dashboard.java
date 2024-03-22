package Pages;

import Utility.Helper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.management.InvalidApplicationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Dashboard {
    private final WebDriver _driver;
    public boolean isCouponAvailable = true;

    public Dashboard(WebDriver driver) {
        this._driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class,'font-unilevershillingMedium')]//a[text()='Rewards']")
    public WebElement lnkRewards;
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
    @FindBy(xpath = "//*[@class='tooltip show bs-tooltip-auto']")
    public WebElement gotit;

    public void NavigateToSettingPage() throws InvalidApplicationException, InterruptedException {
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(30));
       // if (_driver.findElement(By.xpath("//*[@class='text-xs']")).isDisplayed()) {
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='tooltip show bs-tooltip-auto']")));
            //Helper.clickItem(_driver, _driver.findElement(By.xpath("//*[@class='tooltip-inner']//button")));


            Actions action = new Actions(_driver);
            action.moveToElement(Helper.findElement(_driver, By.xpath("//div[@class='inline-block align-bottom']"), 60)).build().perform();
            Thread.sleep(10000);
            Helper.clickItem(_driver, _driver.findElement(By.xpath("//a[@class='mt-4 hover:bg-gray-200 p-2'][normalize-space()='Settings']")));
            while (Helper.findElements(_driver, By.xpath("//h3[text()='Account']"), 60).size() == 0) {
            }

    }


    public void LogOut() throws InvalidApplicationException, InterruptedException {
        Thread.sleep(5000);
        Actions action = new Actions(_driver);
        action.moveToElement(Helper.findElement(_driver, By.xpath("//a[@href='/profile']//div[@class='inline-block align-bottom']"), 10)).build().perform();
        WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(_driver.findElement(By.xpath("//a[@class='mt-4 hover:bg-gray-200 p-2'][normalize-space()='Logout']"))));
        Helper.retryingFindClick(_driver, By.xpath("//a[@class='mt-4 hover:bg-gray-200 p-2'][normalize-space()='Logout']"));
        wait.until(ExpectedConditions.elementToBeClickable(_driver.findElement(By.xpath("//button[text()='LOG OUT']"))));
        Helper.clickItem(_driver, Helper.findElement(_driver, By.xpath("//button[text()='LOG OUT']"), 10));

    }

    public void DonateMeal() throws InvalidApplicationException, InterruptedException {
        WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(30));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='tooltip show bs-tooltip-auto']")));
        //Helper.clickItem(_driver,_driver.findElement(By.xpath("//*[@class='tooltip-inner']//button")));
        while (!_driver.getCurrentUrl().contains("dashboard")) {
        }
        while (!_driver.findElements(By.xpath("//img[@alt='loading animation']")).isEmpty()) {
        }
        //WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'font-unilevershillingMedium')]//a[text()='Rewards']")));
        Helper.clickItem(_driver, Helper.findElement(_driver, By.xpath("//div[contains(@class,'font-unilevershillingMedium')]//a[text()='Rewards']"), 20));
        while (_driver.findElements(By.xpath("//h1[text()='Choose your reward']")).isEmpty()) {
        }
        while (!_driver.findElements(By.xpath("//img[@alt='loading animation']")).isEmpty()) {
        }

        while (_driver.findElements(By.cssSelector("#rewardstab")).isEmpty()) {
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Donate a meal']")));
        Helper.WaitForElementToExistAndVisible(Helper.findElement(_driver, By.xpath("//a[text()='Donate a meal']"), 20));
        Helper.retryingFindClick(_driver, (By.xpath("//a[text()='Donate a meal']")));
        while (_driver.findElements(By.xpath("//button[text()='Donate a Meal']")).isEmpty()) {
        }
        Helper.WaitForElementToExistAndVisible(Helper.findElement(_driver, By.xpath("//button[text()='Donate a Meal']"), 20));
        Helper.retryingFindClick(_driver, By.xpath("//button[text()='Donate a Meal']"));
        while (_driver.findElements(By.xpath("//div[@class='flex justify-center items-center h-screen']")).isEmpty()) {
        }
        Helper.clickItem(_driver, btnDonateAMeal);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='modal-wrapper p-4 lg:py-8 w-4/5 w-11/12 sm:w-7/12 md:w-6/12 lg:w-5/12 xl:w-4/12 2xl:w-2/8 bg-white ']")));
            Helper.clickItem(_driver, _driver.findElement(By.xpath("//*[@class='modal-wrapper p-4 lg:py-8 w-4/5 w-11/12 sm:w-7/12 md:w-6/12 lg:w-5/12 xl:w-4/12 2xl:w-2/8 bg-white ']//button")));

            while (_driver.findElements(By.xpath("//h1[text()='Meal donated']")).isEmpty()) {
            }

            //_driver.findElement(By.xpath("//h1[text()='Meal donated']")).getText().equalsIgnoreCase("Meal donated");
            //_driver.findElement(By.xpath("//div[@class='w-full lg:w-2/5']/p")).getText().contains("You’ve successfully donated a meal");
        //Assert.assertTrue("Expected  'You’ve successfully donated a meal to someone in need through the Second Harvest network. Thank you for making an impact.",
                //_driver.findElements(By.xpath("//*[@class='text-base font-unilevershilling']")).size() == 1);

        Assert.assertTrue("Expected  'You’ve successfully donated a meal to someone in need through the Second Harvest network. Thank you for making an impact.",
                _driver.findElements(By.xpath("//div[@class='w-full lg:w-2/5']/p")).size() == 1);

    }

    public void PlantTree() throws InvalidApplicationException, InterruptedException {
        while (!_driver.getCurrentUrl().contains("dashboard")) {
        }
        Helper.clickItem(_driver, lnkRewards);
        while (_driver.findElements(By.cssSelector("#rewardstab")).isEmpty()) {
        }
        Helper.click(_driver, lnkPlantTree);
        Helper.WaitForElementToExistAndVisible(btnPlantTree);
        Helper.clickItem(_driver, btnPlantTree);
        while (_driver.findElements(By.xpath("//div[@class='flex justify-center items-center h-screen']")).isEmpty()) {
        }
        Helper.clickItem(_driver, btnPlantATree);
        while (_driver.findElements(By.xpath("//div[@class='flex justify-center items-center h-screen']")).isEmpty()) {
        }
        Helper.click(_driver, btnOK);


    }

    public void UploadScript() throws InvalidApplicationException, InterruptedException, AWTException, IOException {
        while (!_driver.getCurrentUrl().contains("dashboard")) {
        }
        Thread.sleep(5000);
        while (!Helper.IsElementExist(_driver, By.xpath("//img[@alt='Upload Receipt']"))) {
        }
        _driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[1]/article/div/div[2]/div[2]/div[1]/div[1]/img")).click();

        //Helper.clickItem(_driver,imgUpload);
        while (_driver.findElements(By.xpath("//div[@class='flex justify-center items-center h-screen']")).isEmpty()) {
        }
        Helper.click(_driver, btnBrowse);
        File filePath = new File("Utility/FileUpload.scpt");
        String pathLoc = filePath.getAbsolutePath();
        System.out.println(pathLoc);
        //_driver.findElement(By.xpath("//button[text()='Browse for file']/../input")).sendKeys(pathLoc);
        Runtime.getRuntime().exec("osascript " + "/Users/srikantmahapatro/automation/LeapWebAutomation/src/test/resources/FileUpload.scpt");
//        Helper.fileUpload(pathLoc);
        //Helper.uploadFile(pathLoc);
        //Helper.EnterText(_driver,btnBrowse,pathLoc);
//        _driver.switchTo()
//                .activeElement()
//                .sendKeys(filePath.getAbsolutePath());
        _driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Helper.click(_driver, btnUseImg);

    }

    public void VerifyReceiptSubmit() {
        while (_driver.findElements(By.cssSelector("div.modal-body")).isEmpty()) {
        }
        Assert.assertTrue("Expected 'Receipt Submitted' msg should display",
                _driver.findElements(By.xpath("//h3[text()='Receipt Submitted']")).size() == 1);
        Helper.click(_driver, btnOK);
    }


    public void ClaimCoupons() throws InvalidApplicationException, InterruptedException {

        Helper.WaitForElementToExistAndVisible(Helper.findElement(_driver, By.xpath("//a[text()='Rewards']"), 20));
        Helper.findElement(_driver, By.xpath("//a[text()='Rewards']"), 20).click();
        WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='tooltip show bs-tooltip-auto']")));
        Helper.clickItem(_driver,_driver.findElement(By.xpath("//*[@class='tooltip-inner']//button")));

        while (_driver.findElements(By.xpath("//h1[text()='Choose your reward']")).isEmpty()) {
        }
        while (!_driver.findElements(By.xpath("//img[@alt='loading animation']")).isEmpty()) {
        }
        Helper.WaitForElementToExistAndVisible(tabCoupons);
        //WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(10));
        WebElement lnkLCoupon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='couponstab']")));
        Helper.retryingFindClick(_driver, By.xpath("//*[@id='couponstab']"));
        Helper.clickItem(_driver, _driver.findElements(By.xpath("//label[text()='Claim a $1 coupon' or text()='Claim a $2 coupon']/input")).get(0));
        Helper.clickItem(_driver, _driver.findElements(By.xpath("//button[text()='Claim']")).get(0));

    }

    public void ClaimCoupons(String value) throws InvalidApplicationException, InterruptedException {

        WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(30));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='tooltip show bs-tooltip-auto']")));
        //Helper.clickItem(_driver,_driver.findElement(By.xpath("//*[@class='tooltip-inner']//button")));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Rewards']")));
        //Helper.WaitForElementToExistAndVisible(Helper.findElement(_driver, By.xpath("//a[text()='Rewards']"), 20));
        Helper.findElement(_driver, By.xpath("//a[text()='Rewards']"), 20).click();
        while (_driver.findElements(By.xpath("//h1[text()='Choose your reward']")).isEmpty()) {
        }
        while (!_driver.findElements(By.xpath("//img[@alt='loading animation']")).isEmpty()) {
        }
        Helper.WaitForElementToExistAndVisible(tabCoupons);
        //WebDriverWait wait = new WebDriverWait(_driver, Duration.ofSeconds(10));
        WebElement lnkLCoupon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='couponstab']")));
        Helper.scrollAndClick(_driver, _driver.findElement(By.xpath("//*[@id='couponstab']")));
        Helper.clickItem(_driver, _driver.findElements(By.xpath("//label[text()='Claim a $" + value + " coupon']/input")).get(0));
        Helper.click(_driver,_driver.findElement(By.xpath("//*[@class='hidden md:hidden lg:block couponstab ']//button[text()='Apply Filters']")));
        Thread.sleep(3000);
        if (Helper.findElements(_driver, By.xpath("//button[text()='Claim']"), 30).size() > 0) {
            Helper.clickItem(_driver, _driver.findElements(By.xpath("//button[text()='Claim']")).get(0));
        } else
            isCouponAvailable = false;


    }

    public void VerifyClaim() throws InterruptedException {
        if (isCouponAvailable) {
            while (!Helper.IsElementExist(_driver, By.xpath("//button[text()='Claim Your Coupon']"))) {
            }
            Thread.sleep(5000);
            try {
                Helper.clickItem(_driver, _driver.findElements(By.xpath("//button[text()='Claim Your Coupon']")).get(0));
            } catch (InvalidApplicationException e) {
                throw new RuntimeException(e);
            }
            Thread.sleep(10000);
            Assert.assertTrue("Expected Coupon use message should displayed",
                    _driver.findElements(By.xpath("//h1[text()='You Claimed a Coupon']")).size() == 1);
            Assert.assertTrue("Expected Confirmation use message should displayed",
                    _driver.findElement(By.xpath("//p[@class='text-base font-unilevershilling']")).getText().contains("Congratulations! You claimed "));
            //Congratulations! You claimed a $1 coupon. Use this coupon at Walmart stores only.
        } else
            System.out.println("No Coupons Available or Available to Claim");
    }


}

