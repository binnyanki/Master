package Runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.common.base.Function;

public class Library {
	static Properties prop = null;
	static HashMap<String, String> testDatamap = new LinkedHashMap<String, String>();
	static LinkedList<String> columnNames = new LinkedList<String>();
	
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
	public static boolean isDisplayed(WebDriver driver, String object) throws Exception
	{ 
		boolean status = false;
		//WebElement element = waitForElement(driver,  object);
		if(getElement(driver, object).isDisplayed())
		{
			status = true;
		}
		
		return status;	
	}
	public static boolean isEnabled(WebDriver driver, String object) throws Exception
	{ 
		boolean status = false;
		//WebElement element = waitForElement(driver,  object);
		if(getElement(driver, object).isEnabled())
		{
			status = true;
		}
		
		return status;	
	}
	public static String takeScreenshot(WebDriver driver)
	{ 
		String desPath = System.getProperty("user.dir")+"\\Screenshot\\"+System.currentTimeMillis()+"image.png";
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
	public static void launchApplication(WebDriver driver) 
	{
		driver.get(loadProperty().getProperty("URL"));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	public static WebElement waitForElement(WebDriver driver,  final String object) throws Exception
	{
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);

			   WebElement element = wait.until(new Function<WebDriver, WebElement>() {
			     public WebElement apply(WebDriver driver) {
			       return getElement(driver, object);
			     }
			   });
			return element;
	}
	public static void loadTestData()
	{
		try {
			
			
			String query ="Select * from Sheet1";
			Fillo fillo = new Fillo();
			Connection con = fillo.getConnection("./src/test/resources/TestData.xlsx");
			Recordset rs = con.executeQuery(query);
			columnNames.addAll(rs.getFieldNames());
			int count =1;
			while(rs.next())
			{ 
				
				for(String s:columnNames)
				{
					testDatamap.put(s+String.valueOf(count), rs.getField(s));
					
				}
				count++;
			}
		} catch (FilloException e) {
			e.printStackTrace();
		}
	}
	public static HashMap<String,String> loadTestCaseData(String testCaseName)
	{
		HashMap<String, String> childmap = new LinkedHashMap<String, String>();
		for(Map.Entry m:testDatamap.entrySet())
		{
			String key = m.getValue().toString();
			
			if(key.equals(testCaseName))
			{
				String key2 =m.getKey().toString();
				String index = key2.replaceAll("[^0-9]", "");
				for(String s: columnNames)
				{
					childmap.put(s, testDatamap.get(s+index));
				}
			}
		}
		return childmap;
		
	}
	
	
	

}
