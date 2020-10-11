package Runner;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class Runner extends FrameworkClass {
	
  @Test
  public void Search(Method m) {
	  
	  logger=report.createTest(m.getName());
	  driver.get(Utils.loadProperty().getProperty("URL"));
  }
  
}
