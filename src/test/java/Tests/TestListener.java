package Tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Utilities.SeleniumUtility;


@Listeners(TestListener.class)
public class TestListener implements ITestListener
{
	
	private WebDriver driver;
    private SeleniumUtility S1;
	
	@Override
    public void onTestStart(ITestResult result)
    {
        // Do nothing on test start
    }
	
	
	 @Override
	    public void onTestSuccess(ITestResult result) 
	    {
		    String methodName = result.getMethod().getMethodName();
		

	    }

    @Override
    public void onTestFailure(ITestResult result) 
    {
    	String methodName = result.getMethod().getMethodName();
        captureScreenshot(methodName);

       
    }

   
    @Override
    public void onTestSkipped(ITestResult result) 
    {
    	String methodName = result.getMethod().getMethodName();
        
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
    {
        // Do nothing
    }

    @Override
    public void onStart(ITestContext context)
    {
//    	String extentReportPath = ".\\src\\test\\resources\\ExtentReports\\ExtentReport.html";
//    	extent = new ExtentReports();
//        ExtentSparkReporter spark = new ExtentSparkReporter(extentReportPath);
//        extent.attachReporter(spark);
    }

    @Override
    public void onFinish(ITestContext context)
    {
    //	extent.flush(); 
    }

    private void captureScreenshot(String methodName)
    {
       
        S1.takeScreenshot(methodName);
   }
}