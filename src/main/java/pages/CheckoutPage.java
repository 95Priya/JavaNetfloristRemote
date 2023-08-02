package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage
{
	private WebDriver driver;
	  
	public CheckoutPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath = "//a[contains(text(),'Checkout')]")
	private WebElement checkOutButton;
	
	 public void clickcheckOutButton() 
	 {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	        WebElement checkOutButtonElemet = wait.until(ExpectedConditions.visibilityOf(checkOutButton));

	        checkOutButton.click();
	    }
	
	
	@FindBy(css = "li.iconDCSprite.deliveryInfo.deliveryTab")
	private WebElement deliveryInfo;
	
	 public void clickOnDeliveryInfo()
	 {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	      

	        WebElement deliveryInfoElement = wait.until(ExpectedConditions.elementToBeClickable(deliveryInfo));

	        deliveryInfoElement.click();
	    }

	 
	 @FindBy(css = "#ProgressBar > ul > li.iconDCSprite.paymentOptions.active.paymentTab")
		private WebElement paymentOptions;


	 public void clickOnPaymentOptions() throws InterruptedException
	 {
		 System.out.println("Trying to find the paymentOptions element...");
		 
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loaderMask")));
	        WebElement paymentInfoElement = wait.until(ExpectedConditions.elementToBeClickable(paymentOptions));
	        Thread.sleep(500);
	        paymentInfoElement.click();
	        System.out.println("Clicked on the paymentOptions element successfully!");
	    }


		
		@FindBy(xpath = "//input[@id='btnNonSouthAfricaVisaMaster']")
		private WebElement payWithCard;
		
		@FindBy(xpath = "//input[@id='cardNumber']")
		private WebElement cardNumber;
		
		@FindBy(id = "expiryMonth")
		private WebElement selectMonth;
		
		@FindBy(id = "expiryYear")
		private WebElement selectYear;
		
		@FindBy(id = "cardHolderName")
		private WebElement cardHolderName;
		
		@FindBy(id = "csc")
		private WebElement securityCode;
		
		@FindBy(xpath= "//*[@id='button-row1']/button[2]")
		private WebElement payNowButton;
		
		
		public void clickOnPayWithCard()
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			
			WebElement cardPayment = wait.until(ExpectedConditions.elementToBeClickable(payWithCard));

			cardPayment.click();
		}
			
		public void fillCardDetails(String cardnum,String expirationMonth,String expirationYear,String cardHoldername,String securitycode)
		{

             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
             WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@title='Hosted Checkout']")));
             driver.switchTo().frame(iframeElement);
             
             
			
			WebElement card = wait.until(ExpectedConditions.visibilityOf(cardNumber));
			//card.click();
		    card.sendKeys(cardnum);
		    
		    try 
		    {
		        Thread.sleep(500); // Adjust the sleep duration as needed
		    } 
		    catch (InterruptedException e)
		    {
		        e.printStackTrace();
		    }
		    
		    
		    Select selectMonths = new Select(selectMonth);
		    selectMonths.selectByValue(expirationMonth);
		    
		    try 
		    {
		    	
		        Thread.sleep(500); // Adjust the sleep duration as needed
		    } 
		    catch (InterruptedException e) 
		    {
		        e.printStackTrace();
		    }
		    
		    Select selectYears = new Select(selectYear);
		    selectYears.selectByVisibleText(expirationYear);
		    cardHolderName.sendKeys(cardHoldername);
		    securityCode.sendKeys(securitycode);
		    payNowButton.click();
		    driver.switchTo().defaultContent();
		}
		

}

