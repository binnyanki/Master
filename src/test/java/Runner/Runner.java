package Runner;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class Runner extends FrameworkClass {

	@Test
	public void searchIpad() {

		try {
			BusinessFunctions.searchProduct(driver, datamap);

		} catch (Exception e) {
			failureCount = failureCount + 1;
			e.printStackTrace();
			logger.fail(e.toString());
		}
	}

	/*
	 * @Test public void searchLaptop() {
	 * 
	 * try { BusinessFunctions.searchLaptop(driver, datamap);
	 * 
	 * } catch (Exception e) { failureCount = failureCount + 1; e.printStackTrace();
	 * logger.fail(e.toString()); } }
	 */

}
