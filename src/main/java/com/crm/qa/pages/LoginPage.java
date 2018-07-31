package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class LoginPage extends TestBase {
	
	//Page Factory - OR:
	@FindBy(name="username")
	WebElement username;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(xpath="//input[@type='submit']")
	WebElement loginBtn;
	
	@FindBy(xpath = "//button[contains(text(), 'Sign Up')]")
	WebElement signUpBtn;
	
	@FindBy(xpath = "//img[@alt='free crm logo1']")
	WebElement crmLogo;
	
	//initializing the page objects
	public LoginPage() {
		//PageFactory.initElements(driver, LoginPage.class);
		PageFactory.initElements(driver, this);
	}
	
	public String validateLoginPageTitle() {
		return driver.getTitle();
	}
	
	public boolean validateCrmImage() {
		return crmLogo.isDisplayed();
	}
	
	public HomePage login(String un, String pw) throws InterruptedException {
		
		username.sendKeys(un);
		Thread.sleep(1000);
		password.sendKeys(pw);
		Thread.sleep(1000);
		loginBtn.click();
		
		return new HomePage();
		
	}

}
