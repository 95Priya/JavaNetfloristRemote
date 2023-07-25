package Tests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dataProvider.ConfigFileReader;

public class FlowerProduct
{
	public BaseSearchTest baseSearchTest;	
	public String productCode;
	public String address;
	
	 @BeforeMethod
	    public void setup() 
	 {
		 baseSearchTest = new BaseSearchTest();
	        baseSearchTest.setup();

	    }

	
    @Test
    public void FlowerAdd1() throws InterruptedException, TimeoutException 
    {
    	productCode = baseSearchTest.getProperty("productcode1");
    	address=baseSearchTest.getProperty("address1");
        baseSearchTest.SearchProduct(productCode, address, "Business");
    }
    
    @Test
    public void FlowerAdd2() throws InterruptedException, TimeoutException 
    {
    	address=baseSearchTest.getProperty("address2");
        baseSearchTest.SearchProduct(productCode, address, "Business");
    }
    
    @Test
    public void FlowerAdd3() throws InterruptedException, TimeoutException 
    {
    	productCode = baseSearchTest.getProperty("productcode1");

    	address=baseSearchTest.getProperty("address3");
        baseSearchTest.SearchProduct(productCode, address, "Business");
    }
    @Test
    public void FlowerAdd4() throws InterruptedException, TimeoutException 
    {
    	productCode = baseSearchTest.getProperty("productcode1");

    	address=baseSearchTest.getProperty("address4");
        baseSearchTest.SearchProduct(productCode, address, "Business");
    }
//    
//    @AfterMethod
//    public void teardown() 
//    {
//        baseSearchTest.teardown();
//    }

}
