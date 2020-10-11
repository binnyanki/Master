package Runner;

import java.io.File;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class FrameworkClass {
 
	WebDriver driver;
	ExtentReports report;
	ExtentHtmlReporter extent;
	ExtentTest logger;
	int failureCount=0;
	@BeforeSuite
	public void beforeSuite()
	{
		Utils.loadProperty();
		extent = new ExtentHtmlReporter(new File("./Reports/Report.html"));
		report=new ExtentReports();
		report.attachReporter(extent);
		
	}
	@BeforeMethod
	public void beforeMethod(Method m)
	{
		logger=report.createTest(m.getName());
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	@AfterSuite
	public void afterSuite()
	{
		
	}
	@AfterMethod
	public void afterMethod(ITestResult result)
	{
		if((result.getStatus()==ITestResult.SUCCESS)&&(failureCount==0))
		{
			logger.pass("Testcase passed");
		}
		if((result.getStatus()==ITestResult.FAILURE)||(failureCount!=0))
		{
			logger.fail("Testcase failed");
			failureCount=0;
		}
		
		report.flush();
		driver.quit();
	}
}
