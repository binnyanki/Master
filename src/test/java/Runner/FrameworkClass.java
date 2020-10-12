package Runner;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class FrameworkClass {
 
	WebDriver driver;
	ExtentReports report;
	ExtentHtmlReporter extent;
	ExtentTest logger;
	ChromeOptions options = new ChromeOptions();
	DesiredCapabilities capabilities = new DesiredCapabilities();
	List<String> failurelist = new LinkedList<String>();
	int failureCount=0;
	int retryCount=2;
	static int retry =0;
	@BeforeSuite
	public void beforeSuite()
	{
		Utils.loadProperty();
		extent = new ExtentHtmlReporter(new File("./Reports/Report.html"));
		extent.config().setDocumentTitle("Automation Report");
		extent.config().setReportName("Functional Report");
		report=new ExtentReports();
		report.attachReporter(extent);
		
	}
	@BeforeTest
	public void beforeTest()
	{
		options.addArguments("test-type");
		options.addArguments("start-maximized");
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-notifications");
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	}
	@BeforeMethod
	public void beforeMethod(Method m)
	{
		logger=report.createTest(m.getName());
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");	
		driver = new ChromeDriver(capabilities);
	}
	@AfterSuite
	public void afterSuite()
	{
		if(!failurelist.isEmpty())
		{ 
			;
			while(retry<retryCount)
			{
				retry++;
				TestNG runner = new TestNG();
				List<String> list = new ArrayList<String>();
				list.add(System.getProperty("user.dir")+"\\test-output\\testng-failed.xml");
				runner.setTestSuites(list);
				runner.run();
				
			}
			failurelist.clear();
		}
	}
	@AfterMethod
	public void afterMethod(ITestResult result)
	{ 
		if((result.getStatus()==ITestResult.SUCCESS)&&(failureCount==0))
		{
			logger.pass("Test Case " +result.getName()+" is Passed");
		}
		if((result.getStatus()==ITestResult.FAILURE)||(failureCount!=0))
		{
			logger.fail("Test Case " +result.getName()+" is Failed");
			failureCount=0;
			failurelist.add(result.getName());
		}
		
		report.flush();
		driver.quit();
	}
}
