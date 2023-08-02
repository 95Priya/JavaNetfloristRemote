package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;
//import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumUtility {

	private WebDriver driver;
	private TakesScreenshot ts;
	public static Actions action = null;
	public WebDriverWait wait;

	public SeleniumUtility(WebDriver driver) 
	{
		
		this.ts = (TakesScreenshot) driver;
	}

	public WebDriver setUp(String browserName, String appUrl) {

		if (browserName.equalsIgnoreCase("Chrome")) {
			// use setup method of WebDriverManager
			WebDriverManager.chromedriver().setup();
			// step2: create an instance of Chrome Browser
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			// use setup method of WebDriverManager
			WebDriverManager.iedriver().setup();
			// step2: create an instance of Chrome Browser
			driver = new InternetExplorerDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			// use setup method of WebDriverManager
			WebDriverManager.firefoxdriver().setup();
			// step2: create an instance of Chrome Browser
			driver = new FirefoxDriver();
		}
		// maximize browser
		driver.manage().window().maximize();
		// implicit wait
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get(appUrl);
		action = new Actions(driver);
		return driver;
	}

	public void performClick(WebElement element) {
		element.click();
	}

	public void typeInput(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	public String getCurrentWorkingDir() {
		String currentDir = System.getProperty("user.dir");
		return currentDir;
	}

	public void copyTextFromField(WebElement element) {
		action.doubleClick(element).perform();
		element.sendKeys(Keys.chord(Keys.CONTROL, "c"));
	}

	public void pasteTextInToField(WebElement element) {
		element.sendKeys(Keys.chord(Keys.CONTROL, "v"));
	}

	public void waitTillElementIsClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public String getValueFromPropertyFile(String fileName, String key) {
		Properties prop = getPropertyFile(fileName);
		return prop.getProperty(key);
	}

	public Properties getPropertyFile(String fileName) {
		String filePath = getCurrentWorkingDir() + fileName;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public void takeScreenshot(String methodName) {
		try {
			
			File srcFile = ts.getScreenshotAs(OutputType.FILE);

			// Get the current date and time
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			Date currentDate = new Date();
			String timestamp = dateFormat.format(currentDate);

			// Generate the screenshot name with the method name and timestamp
			 String screenshotName = methodName + "_" + timestamp + ".png";

			// Define the destination file path and name
			String destinationFilePath = ".\\src\\test\\resources\\Screenshots\\" + screenshotName;
		

			// Copy the screenshot file to the destination
			FileUtils.copyFile(srcFile, new File(destinationFilePath));

			System.out.println("Screenshot taken: " + destinationFilePath);
		} catch (IOException e) {
			System.out.println("Failed to capture screenshot: " + e.getMessage());
		}
	}

	public void performDranAndDrop(WebElement src, WebElement target) {
		action.moveToElement(src).dragAndDrop(src, target).build().perform();
	}

	public void tearDown() {
		driver.close();
	}


	
	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getPageUrl() {
		return driver.getCurrentUrl();
	}

	public void SelectDate(WebDriver driver, LocalDate date) {
		WebElement dateText = driver.findElement(By.id("txtSelectDate"));
		dateText.click();

		// Calculate the row and column values based on the provided date
		int row = (date.getDayOfMonth() + 6) / 7;
		int col = ((date.getDayOfMonth() - 1) % 7) + 1;

		By dateLocator = By.xpath("//div[@id='ui-datepicker-div']/table/tbody/tr[" + row + "]/td[" + col + "]");

		WebElement selectedDate = driver.findElement(dateLocator);
		selectedDate.click();
	}

	public void SelectDateFromCalendar(WebDriver driver, String desiredDate) {
		String dateXPath = String.format("//div[@id='pddDatePicker']//td/a[contains(text(), '%s')]", desiredDate);

		// Retry mechanism with a maximum number of retries
		int maxRetries = 3;
		int retries = 0;
		boolean clicked = false;

		while (!clicked && retries < maxRetries) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dateXPath)));
				dateElement.click();
				clicked = true;
			} catch (StaleElementReferenceException e) {
				retries++;
				System.out.println("StaleElementReferenceException occurred. Retrying...");
			}
		}

		if (!clicked) {
			System.out.println("Could not click on the date element after " + maxRetries + " retries.");
		}

	}

}
