package Runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class Utils {
	static Properties prop = null;
	public static void setText(WebDriver driver, String object, String value)
	{
		getElement(driver, object).sendKeys(value);
	}
	public static WebElement getElement(WebDriver driver, String object)
	{ 
		WebElement e=null;;
		String []objects = object.split("~");
		String elementType = objects[0];
		String element =objects[1];
		
		if(elementType.equals("id"))
		{
			e = driver.findElement(By.id(element));
		}
		if(elementType.equals("name"))
		{
			e = driver.findElement(By.name(element));
		}
		if(elementType.equals("classname"))
		{
			e = driver.findElement(By.className(element));
		}
		if(elementType.equals("tagname"))
		{
			e = driver.findElement(By.tagName(element));
		}
		if(elementType.equals("linktext"))
		{
			e = driver.findElement(By.linkText(element));
		}
		if(elementType.equals("partiallinktext"))
		{
			e = driver.findElement(By.partialLinkText(element));
		}
		if(elementType.equals("css"))
		{
			e = driver.findElement(By.cssSelector(element));
		}
		if(elementType.equals("xpath"))
		{
			e = driver.findElement(By.xpath(element));
		}		
		return e;
		
	}
	public static void Click(WebDriver driver, String object)
	{
		getElement(driver, object).click();
	}
	public static List<WebElement> getElements(WebDriver driver, String object)
	{ 
		List<WebElement> e=null;;
		String []objects = object.split("~");
		String elementType = objects[0];
		String element =objects[1];
		
		if(elementType.equals("id"))
		{
			e = driver.findElements(By.id(element));
		}
		if(elementType.equals("name"))
		{
			e = driver.findElements(By.name(element));
		}
		if(elementType.equals("classname"))
		{
			e = driver.findElements(By.className(element));
		}
		if(elementType.equals("tagname"))
		{
			e = driver.findElements(By.tagName(element));
		}
		if(elementType.equals("linktext"))
		{
			e = driver.findElements(By.linkText(element));
		}
		if(elementType.equals("partiallinktext"))
		{
			e = driver.findElements(By.partialLinkText(element));
		}
		if(elementType.equals("css"))
		{
			e = driver.findElements(By.cssSelector(element));
		}
		if(elementType.equals("xpath"))
		{
			e = driver.findElements(By.xpath(element));
		}		
		return e;
		
	}
	public static Properties loadProperty()
	{
		try {
			File file = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Property.properties");
			FileInputStream fis = new FileInputStream(file);
			prop = new Properties();
			prop.load(fis);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	public static void scrollToElement(WebDriver driver, String object)
	{ 
		WebElement element = getElement(driver, object);
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	public static boolean isDisplayed(WebDriver driver, String object)
	{ 
		boolean status = false;
		if(getElement(driver, object).isDisplayed())
		{
			status = true;
		}
		
		return status;	
	}
	public static boolean isEnabled(WebDriver driver, String object)
	{ 
		boolean status = false;
		if(getElement(driver, object).isEnabled())
		{
			status = true;
		}
		
		return status;	
	}
	public static String takeScreenshot(WebDriver driver)
	{ 
		String desPath = System.getProperty("user.dir")+"\\Screenshot\\image.png";
		try {
			TakesScreenshot scrshot = (TakesScreenshot) driver;
			File srcfile=scrshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcfile, new File(desPath));
		} catch (WebDriverException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return desPath;
	}
	
	

}
