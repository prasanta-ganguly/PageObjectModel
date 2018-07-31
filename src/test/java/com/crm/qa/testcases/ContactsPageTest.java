package com.crm.qa.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.CustomListener;
import com.crm.qa.util.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

@Listeners(CustomListener.class)
public class ContactsPageTest extends TestBase{
	
	HomePage homePage;
	LoginPage loginPage;
	TestUtil testUtil;
	ContactsPage contactsPage;
	
	String sheetName = "contacts";
	
	
	public ContactsPageTest() {
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
		
		testUtil.switchToFrame("mainpanel");
		contactsPage = homePage.clickOnContactsLink();
	}
	
	@Test(priority=1)
	public void verifyContactsPageLabelTest() {
		
		Assert.assertTrue(contactsPage.verifyContactsLabel());
	}
	
	@Test(priority=2)
	public void selectContactTest() throws InterruptedException {
		
		contactsPage.selectContacts("Prasanta1 G1");
		Thread.sleep(2000);
		contactsPage.selectContacts("Prasanta G");
		Thread.sleep(4000);
	}
	
	@DataProvider
	public Object[][] getNewContactData() {
		Object[][] obj = TestUtil.getTestData(sheetName);
		return obj;
		
	}
	
	/*@DataProvider
	public Iterator<Object[]> getNewContactData(){
		ArrayList<Object[]>  obj = TestUtil.getTestData(sheetName);
		return obj.iterator();
	}*/
	
	
	@Test(priority=3, dataProvider="getNewContactData")
	public void creatingNewContact(String title, String firstName, String lastName, String CompanyName ) {
		
		homePage.clickOnNewContactLink();
		contactsPage.createNewContact(title, firstName, lastName, CompanyName);
		
	}
	
	
	@AfterMethod
	public void tearDown() {
		extent.flush();
		driver.quit();
		
	}

}
