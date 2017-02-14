package com.goutham.selenium.javaseleniummaven;

import com.goutham.selenium.pageobjects.DemoRequestFormObject;
import com.goutham.selenium.pageobjects.HomePage;

public class MainTest extends SeleniumBase{
	
	HomePage hPage;
	DemoRequestFormObject demoRequestFormObject;

	public MainTest() {
		super();
		this.hPage = (HomePage) createHomePage();
	}
	
	
	private void goToDemoPage(){
		this.demoRequestFormObject = hPage.clickDemoButton();

		
	} 
	 
	
	
	private void fillFormAndSubmit(){
		demoRequestFormObject.fillForm();
		demoRequestFormObject.clickSubmitButton();
		if (demoRequestFormObject.hasInvalidEmail()) {
			demoRequestFormObject.setEmail("prepfor@qwest.com");
			demoRequestFormObject.clickSubmitButton();
		}	
	}


	public void startTest(){
		goToDemoPage();
		fillFormAndSubmit();
		
	}
	

	
}
