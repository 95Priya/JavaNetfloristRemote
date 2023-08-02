package Tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
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

public class SearchForProductTest extends ConfigFileReader {

	public WebDriver driver;
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
	    S1 = new SeleniumUtility(driver);
	    configFileReader = new ConfigFileReader();
	    String baseURL = configFileReader.getApplicationUrl();

	    driver.get(baseURL);
	    productSearch = new ProductSearchHomePage(driver); 
	    checkout = new CheckoutPage(driver);
	}

	public void SearchProduct(String productCode, String address, String addressType)
			throws InterruptedException {

		productSearch.clickOnSearch(productCode);
		productSearch.selectAndClickProduct();
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
		
		try
        {
          WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
          wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='popDeliveryDetails']")));

          WebElement unavailablePopUp = driver.findElement(By.xpath("//*[@class='popDeliveryDetails']"));
          String popText = unavailablePopUp.getText();
          // System.out.println(popText);

          if (popText.contains("Not Available")) 
          {
              WebElement productText = driver.findElement(By.xpath("//*[@id='pddErrorPrdName']"));
              String productName = productText.getText();
              WebElement productLocation = unavailablePopUp.findElement(By.xpath("//*[@id='pddErrorArea']/strong[1]"));

              String prodLocationText = productLocation.getText();
              unavailablePopUp.findElement(By.xpath("//*[@id='closeDeliverypop']")).click();
              Thread.sleep(1500);

              System.out.println(productName + "is Unavailable at : " + prodLocationText);
          }
      } 
	  catch (NoSuchElementException e)
	  {
          System.out.println("Popup did not appear");
      } 
	  catch (InterruptedException e) 
	  {
          e.printStackTrace();
     }

		String desiredDate = configFileReader.getProperty("desiredDate");
		productSearch.SelectDateFromCalendar(driver, desiredDate);
		productSearch.nextDeliveryType();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			String alertMessage = alert.getText();
			System.out.println("Alert Message: " + alertMessage);
			alert.accept();

		} catch (Exception e) {
			System.out.println("Alert not found or exception occurred: ");

		}
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
	public void teardown(ITestResult result)
	{
		if (result.getStatus() == ITestResult.FAILURE)
		{
			String methodName = result.getMethod().getMethodName();
			S1.takeScreenshot(methodName);
		}
		driver.quit();
	}
}
