package pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductSearchHomePage {
	private WebDriver driver;

	public ProductSearchHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "divSignIn")
	private WebElement signIn;

	@FindBy(id = "tbUserName")
	private WebElement userName;

	@FindBy(id = "tbPassword")
	private WebElement userPass;

	@FindBy(id = "iLinkLogin1")
	private WebElement signInButton;

	public void SignIn(String email, String password) {
		signIn.click();
		userName.sendKeys(email);
		userPass.sendKeys(password);
		signInButton.click();
	}

	
	@FindBy(xpath = "//*[@id='inputSearch']")
	private WebElement searchField;

	public void clickOnSearch(String productCode) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Adjust the timeout value as needed
		wait.until(ExpectedConditions.visibilityOf(searchField));

		searchField.sendKeys(productCode);
		searchField.sendKeys(Keys.ENTER);
	}

	
	public List<WebElement> getProductsList()
	{
		return driver.findElements(By.xpath("//*[@id='SearchContainer']/div/div/div/div[4]/div[2]/div/a"));

	}

	
	public void selectProduct() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='SearchContainer']/div/div/div/div[4]/div[2]/div/a[1]")));
		System.out.println("Product Name is : "+element.getText());
		element.click();
	}

	
	@FindBy(id = "ButtonBottomArea")
	private WebElement addToBasket;

	public void addToBasket() 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(addToBasket));
		addToBasket.click();
	}

	
	@FindBy(xpath = "//div[@id='divAddressLogin']")
	private WebElement login;

	@FindBy(id = "loginId")
	private WebElement loginID;

	@FindBy(id = "loginPwd")
	private WebElement loginPwd;

	@FindBy(id = "btnLogin")
	private WebElement loginButton;

	public void performLogin(String username, String password) 
	{
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(login));
		login.click();
		loginID.sendKeys(username);
		loginPwd.sendKeys(password);
		loginButton.click();
	}

	@FindBy(id = "fName")
	private WebElement firstName;

	@FindBy(id = "lName")
	private WebElement lasttName;

	@FindBy(id = "telNo")
	private WebElement phoneNumber;

	public void fillRecipientDetails(String fName, String lName, String telNo) 
	{
		firstName.sendKeys(fName);
		lasttName.sendKeys(lName);
		phoneNumber.sendKeys(telNo);
	}

	@FindBy(id = "strtNameNo")
	private WebElement streetName;

	@FindBy(id = "ddlAddressType")
	private WebElement typeOfAdd;

	@FindBy(id = "aprtBldng")
	private WebElement apartName;

	@FindBy(id = "suburb")
	private WebElement suburbName;

	public void DeliveryDetails(String strtName, String apartment, String suburb, String addressType) 
	{
		streetName.sendKeys(strtName);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(typeOfAdd));
		Select addSelect = new Select(typeOfAdd);

		addSelect.selectByVisibleText(addressType);

		apartName.sendKeys(apartment);

		suburbName.sendKeys(suburb);

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html/body/ul/li")));

		List<WebElement> suburbsList = driver.findElements(By.xpath("/html/body/ul/li"));
		if (!suburbsList.isEmpty()) 
		{
			suburbsList.get(0).click();
		}

	}

	@FindBy(xpath = "//button[contains(text(), 'Next – Delivery Date')]")
	private WebElement nextDeliveryDate;

	public void selectDate(String dateToSelect) 
	{
		By dateLocator = By.xpath("//*[@id='pddDatePicker']/div/table/tbody/tr[4]/td[6]/a");

		// Retry mechanism to handle StaleElementReferenceException
		for (int attempt = 1; attempt <= 5; attempt++) 
		{
			try 
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(dateLocator));
				dateElement.click();
				break; // Successfully clicked, exit the loop
			}
			catch (org.openqa.selenium.StaleElementReferenceException e) 
			{
				System.out.println("StaleElementReferenceException occurred. Retrying attempt: " + attempt);
			}
		}
	}

	
	public void nextDate() 
	{
		nextDeliveryDate.click();
	}

	
	public void SelectDateFromCalendar(WebDriver driver, String desiredDate)
	{
		String dateXPath = String.format("//div[@id='pddDatePicker']//td/a[contains(text(), '%s')]", desiredDate);
		// Retry mechanism with a maximum number of retries
		int maxRetries = 3;
		int retries = 0;
		boolean clicked = false;

		while (!clicked && retries < maxRetries)
		{
			try
			{
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dateXPath)));
				dateElement.click();
				clicked = true;
			}
			catch (StaleElementReferenceException e) 
			{
				retries++;
				System.out.println("StaleElementReferenceException occurred. Retrying...");
			}
		}

		if (!clicked) 
		{
			System.out.println("Could not click on the date element after " + maxRetries + " retries.");
		}

	}

	@FindBy(xpath = "//button[contains(text(), 'Next – Delivery Type')]")
	private WebElement nextDeliveryType;

	public void nextDeliveryType()
	{
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.elementToBeClickable(nextDeliveryType));
	        nextDeliveryType.click();

	}

	
	@FindBy(id = "endPDD")
	private WebElement finalBasket;

	 public void AddToFinalBasket()
     {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement finalBasketElement = wait.until(ExpectedConditions.elementToBeClickable(finalBasket));
	        finalBasketElement.click();
	        System.out.println("Product added to basket");
	        
     }
	
	}

