package Listeners;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import Utilities.SeleniumUtility;

public class TestListener implements ITestListener 
{
	SeleniumUtility S1 = new SeleniumUtility();
	
	
	 @Override
	    public void onTestFailure(ITestResult result) {
	        
	        System.out.println("Test Failed: " + result.getName());
	        S1.takeScreenshot(result.getName());
	        
	    }
	
	
	    
}
