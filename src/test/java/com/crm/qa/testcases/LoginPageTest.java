package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.CustomListener;

@Listeners(CustomListener.class)
public class LoginPageTest extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;
	
	public LoginPageTest() {
		super();
	}
	
	@BeforeMethod
	public void beforeTestTasks() {
		
		initialization();
		loginPage = new LoginPage();
	}
		
	
	@Test(priority=1)
	public void loginPageTitleTest() {
		
		String title = loginPage.validateLoginPageTitle();
		Assert.assertEquals("#1 Free CRM software in the cloud for sales and service", title);
		
	}
	
	@Test(priority=2)
	public void crmLogoTest() {
		
		boolean flag = loginPage.validateCrmImage();
		Assert.assertTrue(flag);
		
	}
	
	@Test(priority=3)
	public void loginTest() throws InterruptedException {
		
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	
	@AfterMethod
	public void tearDown() {
		extent.flush();
		driver.quit();
		
	}

}
