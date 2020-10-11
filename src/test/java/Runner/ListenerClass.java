package Runner;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass implements ITestListener {

	public void onTestStart(ITestResult result) {

		System.out.println("Test case "+result.getName()+" STARTED");
		
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("Test case "+result.getName()+" is PASS");
		
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("Test case "+result.getName()+" is FAILED");
		
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("Test case "+result.getName()+" is SKIPPED");
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
