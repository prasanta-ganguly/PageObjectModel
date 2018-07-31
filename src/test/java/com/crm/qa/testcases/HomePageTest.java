package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.CustomListener;
import com.crm.qa.util.TestUtil;

@Listeners(CustomListener.class)
public class HomePageTest extends TestBase {

	HomePage homePage;
	LoginPage loginPage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	
	public HomePageTest() {
		super();
	}
	
	
	@BeforeMethod
	public void beforeTestTasks() throws InterruptedException {
	
		initialization();
		loginPage = new LoginPage();
		testUtil = new TestUtil();
		contactsPage = new ContactsPage();
		//homePage = new HomePage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	
	@Test(priority=1)
	public void homePageTitleTest() {
		
		String homePageTitle = homePage.verifyHomePagetitle();
		Assert.assertEquals(homePageTitle, "CRMPRO", "Home page titile not matched");
	}
	
	@Test(priority=2)
	public void userNameLabelTest() {
		
		testUtil.switchToFrame("mainpanel");
		//driver.switchTo().frame("mainpanel");
		String userNameLabel = homePage.verifyUserNameLabel();
		Assert.assertEquals(userNameLabel, "  User: Prasanta Ganguly");
	}
	
	@Test(priority=3)
	public void userNameLabelValidationTest() {
		
		//driver.switchTo().frame("mainpanel");
		testUtil.switchToFrame("mainpanel");
		boolean flag = homePage.validateUserNameLabel();
		Assert.assertTrue(flag);
			}
	
	@Test(priority=4)
	public void verifyContactsLinkTest() {
		
		testUtil.switchToFrame("mainpanel");
		contactsPage = homePage.clickOnContactsLink();
	}
	
	@AfterMethod
	public void tearDown() {
		extent.flush();
		driver.quit();
		
	}
}
