package Runner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	static ExtentTest logger;
	ChromeOptions options = new ChromeOptions();
	DesiredCapabilities capabilities = new DesiredCapabilities();
	List<String> failurelist = new LinkedList<String>();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	 Map<String,String> datamap = new LinkedHashMap<String,String>();
	int failureCount=0;
	int retryCount=2;
	static int retry =0;
	static boolean retryflag = false;
	@BeforeSuite(alwaysRun = true)
	public  void beforeSuite()
	{
		Library.loadTestData();
		Library.loadProperty();
		if(retryflag==false)
		{
			extent = new ExtentHtmlReporter(new File("./Reports/Report.html"));
			extent.config().setDocumentTitle("Automation Report");
			extent.config().setReportName("Functional Report");
			report=new ExtentReports();
			report.attachReporter(extent);
		}
		else
		{
			extent = new ExtentHtmlReporter(new File("./Reports/ReportRetryTestCases.html"));
			extent.config().setDocumentTitle("Automation Report");
			extent.config().setReportName("Functional Report");
			report=new ExtentReports();
			report.attachReporter(extent);
		}
		
		
	}
	@BeforeTest(alwaysRun = true)
	public  void beforeTest()
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
	@BeforeMethod(alwaysRun = true)
	public  void beforeMethod(Method m)
	{
		logger=report.createTest(m.getName());
		datamap = Library.loadTestCaseData(m.getName());
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");	
		driver = new ChromeDriver(capabilities);
	}
	@AfterSuite(alwaysRun = true)
	public  void afterSuite()
	{
		if(!failurelist.isEmpty())
		{ 
			retryflag=true;
			System.out.println("--------This is Retry Mechanism----------");
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
			retryflag=false;
		}
	}
	@AfterMethod(alwaysRun = true)
	public  void afterMethod(ITestResult result) throws Exception
	{ 
		if((result.getStatus()==ITestResult.SUCCESS)&&(failureCount==0))
		{
			logger.pass("Test Case " +result.getName()+" is Passed");
		}
		if((result.getStatus()==ITestResult.FAILURE)||(failureCount!=0))
		{ 
			logger.addScreenCaptureFromPath(Library.takeScreenshot(driver));
			logger.fail("Test Case " +result.getName()+" is Failed");
			//logger.fail(result.getThrowable());
			failureCount=0;
			failurelist.add(result.getName());
		}
		
		report.flush();
		driver.quit();
	}
}
