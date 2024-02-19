package Pages;

import Utility.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Home {

    private final WebDriver _driver;

    public Home(WebDriver driver) {
        this._driver=driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[@class='relative flex group']//div[contains(text(),'Profile')]")
    public WebElement lnkProfile;
    @FindBy(xpath = "/html/body/div[1]/div/main/article/div/section[1]/article/div/a[2]")
    //a[text()='Already have an account? Log in']
    public WebElement lnkSignIn;
    @FindBy(xpath = "//a[text()='CREATE AN ACCOUNT']")
    public WebElement lnkSignUp;


    private void AcceptCookies(){
        List<WebElement> items=_driver.findElements(By.xpath("//*[text() = 'Accept']"));
        if(items.size()>0)
            items.get(0).click();
    }

    public Login NavigateToLogin(){
        AcceptCookies();
        Helper.click(_driver,lnkSignIn);
        while(_driver.findElements(By.xpath("//h1[text()='Sign in']")).size()==0){}
        return new Login(_driver);
    }

    public SignUp NavigateToSignUp(){
        AcceptCookies();
        Helper.click(_driver,lnkSignUp);
        while(_driver.findElements(By.xpath("//h1[text()='SIGN UP & ']")).size()==0){}
        return new SignUp(_driver);
    }



}
