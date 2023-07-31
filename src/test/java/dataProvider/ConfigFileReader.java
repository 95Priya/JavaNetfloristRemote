package dataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.DataProvider;

public class ConfigFileReader {

	private Properties properties;
	private final String propertyFilePath= ".\\src\\test\\resources\\AppData\\LoginInfo.properties";

	
	public ConfigFileReader(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	public String getApplicationUrl()
	{
		String url = properties.getProperty("baseurl");
		if(url != null) return url;
		else throw new RuntimeException("url not specified in the Configuration.properties file.");
	}

	public String getProperty(String string) 
	{
		String value = properties.getProperty(string);
        if (value != null) 
        {
            return value.trim();
        } else 
        {
            throw new RuntimeException("Property '" + string + "' not found in the Configuration.properties file.");
        }
	}
	
	public List<String> getAllAddressesForProductCode(String productCode) {
	    List<String> addresses = new ArrayList<>();
	    int addressNumber = 1;

	    // Loop through the properties and look for addresses with the specified product code
	    String addressKey = "address." + productCode + "." + addressNumber;
	    String addressValue = properties.getProperty(addressKey);
	    while (addressValue != null) {
	        addresses.add(addressValue);
	        addressNumber++;
	        addressKey = "address." + productCode + "." + addressNumber;
	        addressValue = properties.getProperty(addressKey);
	    }

	    System.out.println("Addresses for product code " + productCode + ": " + addresses);
	    return addresses;
	}

	
	 public List<String> getAllProductCodes() 
	 {
	        List<String> productCodes = new ArrayList<>();

	        for (String key : properties.stringPropertyNames())
	        {
	            if (key.startsWith("productcode.")) {
	                productCodes.add(properties.getProperty(key));
	            }
	        }

	        return productCodes;
	    }
	
	 
	 @DataProvider(name = "productCodesAndAddresses")
	 public Object[][] getProductCodesAndAddresses() {
	     List<String> productCodes = getAllProductCodes();
	     Object[][] data = new Object[productCodes.size()][];

	     for (int i = 0; i < productCodes.size(); i++) {
	         String productCode = productCodes.get(i);
	         List<String> addresses = getAllAddressesForProductCode(productCode);
	         data[i] = new Object[] { productCode, addresses };
	     }

	     return data;
	 }

	 
	 
	 
	 public Properties getProperties() 
	 {
	        return properties;
	    }
}
