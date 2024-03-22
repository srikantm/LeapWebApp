package Steps;

import Base.BaseUtility;
import Pages.ProductList;
import Utility.Helper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class SDProductsDetails {

    private final WebDriver _driver;
    ProductList productList;
    List<String> productsLinks;
    Dictionary<String, String> dictLinks = new Hashtable<>();
    Boolean Results = false;

    public SDProductsDetails(BaseUtility base) {
        this._driver = base._driver;
    }

    @Given("Product url is up and running")
    public void product_url_is_up_and_running() throws MalformedURLException {
        _driver.get("https://dev.leaprewards.com/drafts/products-links");
        Helper.WaitForPageToLoad(_driver);
        productList = new ProductList(_driver);
        productList.AcceptCookies();
        productsLinks = productList.GetLinks();
    }

    @When("try to validate the carousel on each product page")
    public void try_to_validate_the_carousel_on_each_product_page() throws IOException {
        System.out.println("The counts of inks are " + productsLinks.size());
        Helper.CreateCSV();

        for (int i = 0; i <= productsLinks.size(); i++) {
            //   for (int i = 326; i <=490 ; i++) {
            //}
            //for (String lnkItem:productsLinks) {
            _driver.get(productsLinks.get(i));
            //Helper.WaitForPageToLoad(_driver);
            //List<WebElement> items=_driver.findElements(By.xpath("//span[contains(@class,'swiper-pagination-bullet')]"));
            List<WebElement> imgSrc = _driver.findElements(By.xpath("//div[contains(@class,'wiper-slide')]/picture/img"));
            System.out.println("Link no " + i);
            //System.out.println(items.size());

            System.out.println(imgSrc.size());
            for (int j = 0; j < imgSrc.size(); j++) {
                //dictLinks.put(i+"."+j,imgSrc.get(j).getAttribute("src"));
                //String[] lineData = {i+"."+j, imgSrc.get(j).getAttribute("src")};
                Helper.WriteCSV(i + "." + j, imgSrc.get(j).getAttribute("src"));


            }


        }

//        for (String lnkItem:productsLinks) {
//            _driver.navigate().to(lnkItem);
//            Helper.WaitForPageToLoad(_driver);
//            List<WebElement> items=_driver.findElements(By.xpath("//span[contains(@class,'swiper-pagination-bullet')]"));
//            List<WebElement> imgSrc=_driver.findElements(By.xpath("//div[contains(@class,'wiper-slide')]/picture"));
//            System.out.println(items.size());
//            System.out.println(imgSrc.size());
//            _driver.navigate().back();
//            Helper.WaitForPageToLoad(_driver);
//        }
    }

    @Then("image should load as expected")
    public void image_should_load_as_expected() {
//        Enumeration<String> k = dictLinks.keys();
//        while (k.hasMoreElements()) {
//            String key = k.nextElement();
//            System.out.println("Key: " + key + ", Value: "+ dictLinks.get(key));
//        }

        try {

            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader("LinkItems.csv");

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                Helper.WriteCSV(nextRecord[0], nextRecord[1], Helper.checkLinks(nextRecord[1]).toString());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Given("Product list url should up and running")
    public void product_list_url_should_up_and_running() throws MalformedURLException {
        _driver.get("https://dev.leaprewards.com/drafts/products-all-images-list");
        Helper.WaitForPageToLoad(_driver);
        productList = new ProductList(_driver);
        productList.AcceptCookies();
        productsLinks = productList.GetProductLinks();
    }

    @When("trying to access the link")
    public void trying_to_access_the_link() {
        System.out.println(productsLinks.size());
        for (int i = 0; i < productsLinks.size(); i++) {
            System.out.println(productsLinks.get(i));
        }
    }

    @Then("Links should be accessible")
    public void links_should_be_accessible() throws IOException {
        for (int i = 0; i < productsLinks.size(); i++) {
            if (productsLinks.get(i) != "")
                Helper.WriteToCSV(productsLinks.get(i), Helper.checkLinks(productsLinks.get(i)).toString());

        }

    }
}
