package Tests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Utilities.SeleniumUtility;
import dataProvider.ConfigFileReader;

@Listeners(TestListener.class)
public class FlowerProduct
{
	public WebDriver driver;
	public SearchForProductTest baseSearchTest;	
	public String productCode;
	public String address;
    public String addressType;
    private SeleniumUtility S1;

    
    
    @BeforeMethod
	    public void setup() 
	 {
    	    
		    baseSearchTest = new SearchForProductTest();
	        baseSearchTest.setup();
	        //S1 = new SeleniumUtility(baseSearchTest.driver); 
	    }

    @Test
    public void FlowerAdd1() throws InterruptedException
    {
    	
    	productCode = baseSearchTest.getProperty("productcode1");
    	address=baseSearchTest.getProperty("address1");
    	addressType = baseSearchTest.getProperty("addressType1");
        baseSearchTest.SearchProduct(productCode, address, addressType);
    }
    
    @Test
    public void FlowerAdd2() throws InterruptedException
    {
    	
    	productCode = baseSearchTest.getProperty("productcode1");
    	address=baseSearchTest.getProperty("address2");
    	addressType = baseSearchTest.getProperty("addressType2");
        baseSearchTest.SearchProduct(productCode, address, addressType);
    }
    
    @Test
    public void FlowerAdd3() throws InterruptedException
    {
    	
    	productCode = baseSearchTest.getProperty("productcode1");
    	address=baseSearchTest.getProperty("address3");
    	addressType = baseSearchTest.getProperty("addressType3");
        baseSearchTest.SearchProduct(productCode, address, addressType);
    }
    @Test
    public void FlowerAdd4() throws InterruptedException
    {
    	
    	productCode = baseSearchTest.getProperty("productcode1");
    	address=baseSearchTest.getProperty("address4");
    	addressType = baseSearchTest.getProperty("addressType4");
        baseSearchTest.SearchProduct(productCode, address, addressType);
    }
    
    @AfterMethod
 public void close(ITestResult result)
 {
	 baseSearchTest.teardown(result);
	 
 }
}
