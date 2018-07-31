package com.crm.qa.util;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.crm.qa.base.TestBase;


public class CustomListener extends TestBase implements ITestListener{

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {

		test.pass(MarkupHelper.createLabel(result.getName()+" Test case passed", ExtentColor.GREEN));
		test.log(Status.PASS, result.getName()+"Test case passed");
	}

	public void onTestFailure(ITestResult result) {

		test.fail(MarkupHelper.createLabel(result.getName()+" Test case failed", ExtentColor.RED));
		test.log(Status.FAIL, result.getName()+"Test case failed");
		
		try {
			takingScreenshots(result.getMethod().getMethodName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test.fail(result.getThrowable());
	}

	public void onTestSkipped(ITestResult result) {

		test.skip(MarkupHelper.createLabel(result.getName()+"Test case skipped", ExtentColor.YELLOW));
		test.log(Status.SKIP, result.getName()+"Test case skipped");
		test.skip(result.getThrowable());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
