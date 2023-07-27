package Tests;

import java.util.concurrent.TimeoutException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class BakeryProduct 
{


	public SearchForProductTest baseSearchTest;	
	public String productCode;
	public String address;
    public String addressType;
    
	 @BeforeMethod
	    public void setup() 
	 {
		 baseSearchTest = new SearchForProductTest();
	        baseSearchTest.setup();

	    }

	 @Test
	    public void BakeryAdd1() throws InterruptedException, TimeoutException 
	    {
	    	productCode = baseSearchTest.getProperty("productcode4");
	    	address=baseSearchTest.getProperty("address1");
	    	addressType = baseSearchTest.getProperty("addressType1");
	        baseSearchTest.SearchProduct(productCode, address, addressType);
	    }
	    
	    @Test
	    public void BakeryAdd2() throws InterruptedException, TimeoutException 
	    {
	    	productCode = baseSearchTest.getProperty("productcode4");
	    	address=baseSearchTest.getProperty("address2");
	    	addressType = baseSearchTest.getProperty("addressType2");
	        baseSearchTest.SearchProduct(productCode, address, addressType);
	    }
	    
	    @Test
	    public void BakeryAdd3() throws InterruptedException, TimeoutException 
	    {
	    	productCode = baseSearchTest.getProperty("productcode4");
	    	address=baseSearchTest.getProperty("address3");
	    	addressType = baseSearchTest.getProperty("addressType3");
	        baseSearchTest.SearchProduct(productCode, address, addressType);
	    }
	    @Test
	    public void BakeryAdd4() throws InterruptedException, TimeoutException 
	    {
	    	productCode = baseSearchTest.getProperty("productcode4");
	    	address=baseSearchTest.getProperty("address4");
	    	addressType = baseSearchTest.getProperty("addressType4");
	        baseSearchTest.SearchProduct(productCode, address, addressType);
	    }
	//    
//	    @AfterMethod
//	    public void teardown() 
//	    {
//	        baseSearchTest.teardown();
//	    }
	
	
}
