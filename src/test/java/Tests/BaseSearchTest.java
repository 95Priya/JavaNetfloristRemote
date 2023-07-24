package Tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Utilities.SeleniumUtility;
import dataProvider.ConfigFileReader;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CheckoutPage;
import pages.ProductSearchHomePage;

public class BaseSearchTest extends ConfigFileReader{

	private WebDriver driver;
	private ProductSearchHomePage productSearch;
	private CheckoutPage checkout;
	private SeleniumUtility S1;
	WebDriverWait wait;
	private ConfigFileReader configFileReader;
	private String productCode;
	private String username;
	private String password;

	 @BeforeMethod
	public void setup() 
	 {
	    WebDriverManager.chromedriver().setup();
	    ChromeOptions options = new ChromeOptions();
	    options.setPageLoadStrategy(PageLoadStrategy.EAGER);
	    driver = new ChromeDriver(options);
	    driver.manage().window().maximize();
	    S1 = new SeleniumUtility();
	    configFileReader = new ConfigFileReader();
	    String baseURL = configFileReader.getApplicationUrl();

	    driver.get(baseURL);
	    productSearch = new ProductSearchHomePage(driver); 
	    checkout = new CheckoutPage(driver);
	}

	
	public void SearchProduct(String productCode, String address , String addressType) throws InterruptedException, TimeoutException {
	
			productSearch.clickOnSearch(productCode);
			productSearch.selectProduct();
			productSearch.addToBasket();
			
			username = configFileReader.getProperty("username");
			password = configFileReader.getProperty("password");
			productSearch.performLogin(username, password);

			String firstname = configFileReader.getProperty("Firstname");
			String lastname = configFileReader.getProperty("LastName");
			String phone = configFileReader.getProperty("Phone");

			productSearch.fillRecipientDetails(firstname, lastname, phone);

			
			String[] addressParts = address.split(",");
			String strtName = addressParts[0].trim();
			String apartment = addressParts[1].trim();
			String suburb = addressParts[2].trim();
			

			productSearch.DeliveryDetails(strtName, apartment, suburb, addressType);
			Thread.sleep(2000);

			productSearch.nextDate();

			String desiredDate = configFileReader.getProperty("desiredDate");
			productSearch.SelectDateFromCalendar(driver, desiredDate);
			productSearch.nextDeliveryType();
			productSearch.AddToFinalBasket();
			checkout.clickcheckOutButton();
			checkout.clickOnDeliveryInfo();
			checkout.clickOnPaymentOptions();
			checkout.clickOnPayWithCard();

			String cardnum = configFileReader.getProperty("CardNum");
			String cardHolder = configFileReader.getProperty("CardHolderName");
			String securityCode = configFileReader.getProperty("SecurityCode");
			String expireMonth = configFileReader.getProperty("ExpirationMonth");
			String expireYear = configFileReader.getProperty("ExpirationYear");

			checkout.fillCardDetails(cardnum, expireMonth, expireYear, cardHolder, securityCode);
			
		}
	
	
	  @AfterMethod
	public void teardown() {
		// Close the WebDriver after the tests are complete
		driver.quit();
	}
}
