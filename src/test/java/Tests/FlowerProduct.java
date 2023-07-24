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
	
	 @BeforeMethod
	    public void setup() 
	 {
		 baseSearchTest = new BaseSearchTest();
	        baseSearchTest.setup();

	    }

	
	
    List<String> addresses = new ArrayList<String>() {{
        add("Parade hotel, 191 O R Tambo Parade , marine parade ,Business");
        add("Hillcrest Primary School, 5 Bollihope Cres, Mowbray,School");
        add("34 Barbet ,Crescent, Midrand,Residence");
        add("Baragwanath Hospital , 26 Chris Hani Rd, Diepkloof ,Hospital");
    }};
    
	
    @Test
    public void FlowerAdd1() throws InterruptedException, TimeoutException 
    {
    	productCode = baseSearchTest.getProperty("productcode1");

        baseSearchTest.SearchProduct(productCode, addresses.get(0), "Business");
    }
    
    @Test
    public void FlowerAdd2() throws InterruptedException, TimeoutException 
    {
    	productCode = baseSearchTest.getProperty("productcode1");

        baseSearchTest.SearchProduct(productCode, addresses.get(1), "School");
    }
    
    @Test
    public void FlowerAdd3() throws InterruptedException, TimeoutException 
    {
    	productCode = baseSearchTest.getProperty("productcode1");

        baseSearchTest.SearchProduct(productCode, addresses.get(2), "Residence");
    }
    @Test
    public void FlowerAdd4() throws InterruptedException, TimeoutException 
    {
    	productCode = baseSearchTest.getProperty("productcode1");

        baseSearchTest.SearchProduct(productCode, addresses.get(3), "Hospital");
    }
    
    @AfterMethod
    public void teardown() 
    {
        baseSearchTest.teardown();
    }

}
