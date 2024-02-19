package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

// page_url = https://dev.leaprewards.com/drafts/products-list
public class ProductList {

    WebDriver driver;
    @FindBy(xpath = "//*[text() = 'Accept']")
    public WebElement buttonEvidonDecline2;

    @FindBy(xpath = "//div[@class='image-container']/parent::div")
    public List<WebElement> ImgProducts;


    public ProductList(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public void AcceptCookies(){
        buttonEvidonDecline2.click();
    }

    public List<String> GetLinks() throws MalformedURLException {

        return driver.findElements(By.xpath("//main//a"))
                .stream()
                .map(e -> e.getAttribute("href"))
                .collect(Collectors.toList());
    }

    public List<String> GetProductLinks() throws MalformedURLException {

        return driver.findElements(By.xpath("//div[@class='mb-4']//a"))
                .stream()
                .map(e -> e.getAttribute("href"))
                .collect(Collectors.toList());
    }

    public Boolean checkLinks(WebElement Im) {
        int Response = 0;
        String Source = "";
        try {
            Source = Im.getAttribute("src");
            if (Source.equals("#"))
                return false;
            HttpURLConnection Connect = (HttpURLConnection) (new URL(Source)).openConnection();
            Response = Connect.getResponseCode();
            System.out.println("Responsecode: " + Response);
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (!((Response + "").startsWith("4") || (Response + "").startsWith("5"))) {
            System.out.println("Valid Source:" + ((Source == null) ? "null" : Source));
            return true;
        } else {
            System.out.println("Invalid Source:" + ((Source == null) ? "null" : Source));
            return false;
        }
    }


}