package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.crm.qa.base.TestBase;

public class ContactsPage extends TestBase {
	
	
	@FindBy(xpath="//td[contains(text(), 'Contacts')]")
	WebElement contactsLabel;
	
	@FindBy(name="title")
	WebElement titles;
	
	@FindBy(id="first_name")
	WebElement firstName;
	
	@FindBy(name="surname")
	WebElement surname;
	
	@FindBy(name="client_lookup")
	WebElement company;
	
	@FindBy(xpath="//form[@id=\"contactForm\"]//td/input/following-sibling::input[@value='Save']")
	WebElement saveBtn;
	
	/*a[contains(text(), 'Prasanta1 G1')]/parent::td/preceding-sibling::td/input[@name='contact_id']
	@FindBy(xpath="//a[contains(text(), 'Prasanta1 G1')]/parent::td[@class='datalistrow']/preceding-sibling::td/input[@name='contact_id']")
	WebElement selectContactsByChkbox;*/
	
	public ContactsPage() {
		PageFactory.initElements(driver, this);
	}
	
	public boolean verifyContactsLabel() {
		return contactsLabel.isDisplayed();
	}
	
	public void selectContacts(String name) {
		driver.findElement(By.xpath("//a[contains(text(), '"+name+"')]/parent::td[@class='datalistrow']/preceding-sibling::td/input[@name='contact_id']")).click();
		
	}
	
public void createNewContact(String title, String fName, String lName, String compName) {
		
		Select select = new Select(titles);
		select.selectByVisibleText(title);
		
		firstName.sendKeys(fName);
		surname.sendKeys(lName);
		company.sendKeys(compName);
		
		saveBtn.click();
	}
	
	

}
