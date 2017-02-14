package com.goutham.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DemoRequestFormObject extends BasePageObject {

	final private WebDriver driver;	
	public static final String FIRST_NAME = "firstname";
	public static final String LAST_NAME = "lastname";
	public static final String COMPANY = "company";
	public static final String EMAIL = "email";
	public static final String PHONE = "phone";
	public static final String ZIP = "zip";
	public static final String STATE = "state";
	public static final String HOW_MANY_LOCATIONS = "how_many_locations_do_you_have_";
	public static final String INDUSTRY_SEGMENT = "industry_segment";
	public static final String WHAT_POS_DO_YOU_USE = "what_pos_do_you_currently_use_if_any_";
	public static final String COMMENTS = "comments";
	
	public DemoRequestFormObject(WebDriver driver){
		this.driver = driver;
	}
	
	
	public void fillForm(){
		  sendKeys(driver, By.name(FIRST_NAME), "Test");
		  sendKeys(driver, By.name(LAST_NAME), "Selenium");
		  sendKeys(driver, By.name(COMPANY), "Interview");
		  setEmail("call@gmail.com");
		  sendKeys(driver, By.name(PHONE), "3165894455");
		  sendKeys(driver, By.name(ZIP), "76032");
		  sendKeys(driver, By.name(STATE), "TX");
		  sendKeys(driver, By.name(HOW_MANY_LOCATIONS), "5");
		  sendKeys(driver, By.name(WHAT_POS_DO_YOU_USE), "NONE");
		  select(driver, By.name(INDUSTRY_SEGMENT),"Fast Casual");
		  sendKeys(driver, By.name(COMMENTS), "This is a selenium test");
		  
		  
	}

	public void setEmail(String email) {
		sendKeys(driver, By.name(EMAIL), email);
	}
	
	public void clickSubmitButton(){
		
		By b = By.xpath("//input[@type='submit']");
		driver.findElement(b).click();
	}
	
	public boolean hasInvalidEmail(){
		By b = By.xpath("//*[contains(text(), 'Please enter a valid email address.')]");
		return driver.findElement(b).isDisplayed();
	}
}
