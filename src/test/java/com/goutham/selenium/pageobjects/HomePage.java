package com.goutham.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePageObject{
	public static final String PAGE_TITLE = "Home";
	final private WebDriver driver;
	// Common Buttons
	//@FindBy(linkText="Request a Demo") 
	//protected WebElement demoButton; 
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		verifyPage(driver, PAGE_TITLE);
	}
	
	
	
	public DemoRequestFormObject clickDemoButton(){
		By demoButton = By.linkText("Request a Demo");
		clickBy(driver, demoButton);
		return PageFactory.initElements(driver, DemoRequestFormObject.class);
	}
}
