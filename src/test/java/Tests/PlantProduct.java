package Tests;

import java.util.concurrent.TimeoutException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class PlantProduct {

	public SearchForProductTest baseSearchTest;
	public String productCode;
	public String address;
	public String addressType;

	@BeforeMethod
	public void setup() {

		baseSearchTest = new SearchForProductTest();
		baseSearchTest.setup();

	}

	@Test
	public void PlantProduct1() throws InterruptedException, TimeoutException {
		productCode = baseSearchTest.getProperty("productcode2");
		address = baseSearchTest.getProperty("address4");
		addressType = baseSearchTest.getProperty("addressType4");
		baseSearchTest.SearchProduct(productCode, address, addressType);
	}

	@Test
	public void PlantProduct2() throws InterruptedException, TimeoutException {
		productCode = baseSearchTest.getProperty("productcode2");
		address = baseSearchTest.getProperty("address2");
		addressType = baseSearchTest.getProperty("addressType2");
		baseSearchTest.SearchProduct(productCode, address, addressType);
	}

	@Test
	public void PlantProduct3() throws InterruptedException, TimeoutException {
		productCode = baseSearchTest.getProperty("productcode2");
		address = baseSearchTest.getProperty("address3");
		addressType = baseSearchTest.getProperty("addressType3");
		baseSearchTest.SearchProduct(productCode, address, addressType);
	}

	@Test
	public void PlantProduct4() throws InterruptedException, TimeoutException {
		productCode = baseSearchTest.getProperty("productcode2");
		address = baseSearchTest.getProperty("address4");
		addressType = baseSearchTest.getProperty("addressType4");
		baseSearchTest.SearchProduct(productCode, address, addressType);
	}

	@AfterMethod
	public void close(ITestResult result) {
		baseSearchTest.teardown(result);

	}

}
