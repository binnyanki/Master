package Runner;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class Runner extends FrameworkClass {
	
  @Test
  public void Search() {
	  
	  try {
		  driver.get(Utils.loadProperty().getProperty("URL"));
		  logger.addScreenCaptureFromPath(Utils.takeScreenshot(driver));
		  
	} catch (Exception e) {
		failureCount=failureCount+1;
		e.printStackTrace();
		logger.fail(e.toString());
	}
  }
  
}
