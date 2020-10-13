package Runner;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;


public class BusinessFunctions extends FrameworkClass{
	static SoftAssert softAssert = new SoftAssert();

	public static void searchProduct(WebDriver driver, Map<String,String> data) throws Exception
	{
		Library.launchApplication(driver);
		logger.addScreenCaptureFromPath(Library.takeScreenshot(driver));
		Library.setText(driver, ObjectRepository.searchTextbox, data.get("ProductValue"));
		Library.Click(driver, ObjectRepository.searchBtn);
		//Library.isDisplayed(driver, ObjectRepository.shopByDisplaySizeText);
		logger.addScreenCaptureFromPath(Library.takeScreenshot(driver));
		//softAssert.assertAll();
	}
	public static void searchLaptop(WebDriver driver, Map<String,String> data) throws Exception 
	{
		Library.launchApplication(driver);
		logger.addScreenCaptureFromPath(Library.takeScreenshot(driver));
		Library.setText(driver, ObjectRepository.searchTextbox, data.get("ProductValue"));
		Library.Click(driver, ObjectRepository.searchBtn);
		//Library.isDisplayed(driver, ObjectRepository.shopByDisplaySizeText);
		logger.addScreenCaptureFromPath(Library.takeScreenshot(driver));
		//softAssert.assertAll();
	}
}
