package com.goutham.selenium.pageobjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class BasePageObject {
	/**
	 * This method will wait for the correct screen to be displayed.  If it isn't it will timeout after 
	 * 60 seconds with a meaningful error message.
	 * 
	 * @param driver
	 * @param expectedPageTitle
	 * @return 
	 */
	protected Boolean verifyPage (WebDriver driver, final String expectedPageTitle) {
	    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			.withTimeout(40, TimeUnit.SECONDS)
			.pollingEvery(100, TimeUnit.MILLISECONDS)
		    .ignoring(org.openqa.selenium.NoSuchElementException.class, org.openqa.selenium.UnhandledAlertException.class)
		    .withMessage("Unexpected Page -- Selenium was expecting '" + expectedPageTitle + "', but got '" + driver.getTitle().trim() + "'.");
	    return wait.until(ExpectedConditions.titleIs(expectedPageTitle.trim()));
	}
	
    public void click(WebDriver driver, By b) {
        
        waitForElementVisible(driver, b);
        
        if (driver.findElement(b).isDisplayed()) {
            driver.findElement(b).click();
        }
        
        sleepFor(225);
    }
  
    public void click(WebDriver driver, WebElement webElement) {
        
        
        if (webElement.isDisplayed()) {
        	webElement.click();
        }
        
        sleepFor(225);
    }
    
    public void clickBy(WebDriver driver, By b) {
       
        waitForElementVisible(driver, b);
        
        if (driver.findElement(b).isDisplayed()) {
            driver.findElement(b).click();
        }
        
        sleepFor(225);
    }
    
	protected WebElement waitForElementVisible(WebDriver driver, By b) {	
		// TODO: This could be cleaned up.
		// Wait for element to be visible before waiting for it to be clickable
		int retryCount = 0;
		while (!(driver.findElements(b).size() > 0 && driver.findElement(b).isDisplayed()) && 
				retryCount < 5) {
			sleepFor(1000);
			retryCount++;
		}
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(40, TimeUnit.SECONDS)
                .pollingEvery(100, TimeUnit.MILLISECONDS)
                .ignoring(org.openqa.selenium.NoSuchElementException.class, org.openqa.selenium.UnhandledAlertException.class)
                .withMessage("Could not find element with By " + b);
        return wait.until(ExpectedConditions.elementToBeClickable(b));
	}
	
	public static void sleepFor(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
	
	/**
	 * A wrapped version of Selenium's sendKeys which waits for the element to load.
	 * USE THIS INSTEAD OF SELENIUM'S SENDKEYS
	 * 
	 */
    public void sendKeys(WebDriver driver, By b, String keys) {
                
        // If textbox is disabled, exit:
        if (driver.findElement(b).getAttribute("disabled") != null) {
        	return;
        }
        
        waitForElementVisible(driver, b);
        
        if (driver.findElement(b).isDisplayed()) {
            WebElement e = driver.findElement(b);
            e.clear();
            e.sendKeys(keys);
        }
        sleepFor(175);
    }
    
    /** Select Dropdown **/
    protected void select(WebDriver driver, String id, String text) {
        select(driver, By.id(id), text, false);
    }
    
    protected void select(WebDriver driver, String id, String text, boolean exactMatch) {
        select(driver, By.id(id), text, exactMatch);
    }
    
    public void select(WebDriver driver, By b, String text) {
        select(driver, b, text, false);
    }
    
    /**
     * A wrapped version of Selenium's select which waits for the element to load.
     *
     * @param exactMatch if true must match character for character to be selected, else if false can be a partial match
     */
    protected void select(WebDriver driver, By b, String text, boolean exactMatch) {
        
        // If select is disabled, exit:
        if (driver.findElement(b).getAttribute("disabled") != null) {
        	return;
        }
        
        waitForElementVisible(driver, b);
        
        if (driver.findElement(b).isDisplayed()) {
            WebElement e = driver.findElement(b);
            if (e.isEnabled()) {
                waitForSelectToContainElements(e);
                Select select = new Select(e);
            
                if (exactMatch) {
                    select.selectByVisibleText(text);
                } else {
                	// Partial Match logic
                    List<WebElement> list = select.getOptions();
                    for (WebElement option : list) {
                        String fullText = option.getText();
                        if (fullText.toUpperCase().contains(text.toUpperCase())) {
                            select.selectByVisibleText(fullText);
                            break;  // accept the first match
                        }
                    }
                }
            }
        }
        sleepFor(200);
    }
    
    protected void selectByValue(WebDriver driver, By b, String text, boolean exactMatch) {
     
        
        // If select is disabled, exit:
        if (driver.findElement(b).getAttribute("disabled") != null) {
            return;
        }
        
        waitForElementVisible(driver, b);
        
        if (driver.findElement(b).isDisplayed()) {
            WebElement e = driver.findElement(b);
            if (e.isEnabled()) {
                waitForSelectToContainElements(e);
                Select select = new Select(e);
            
                if (exactMatch) {
                    select.selectByValue(text);
                } else {
                    // Partial Match logic
                    List<WebElement> list = select.getOptions();
                    for (WebElement option : list) {
                        String fullText = option.getText();
                        if (fullText.toUpperCase().contains(text.toUpperCase())) {
                            select.selectByValue(fullText);
                            break;  // accept the first match
                        }
                    }
                }
            }
        }
        sleepFor(200);
    }
    
	/**
	 * This method will wait for 5 seconds for the Select list to have more than 1 element.
	 * 
	 * @param webElement
	 */
	
	public static void waitForSelectToContainElements(WebElement webElement) {
		Select select = new Select(webElement);
		// This for loop will wait up to 5 seconds, then proceed either way
		for (int i=1; i <= 10; i++) {
			if (select.getOptions().size() <= 1) {
				// There is only no meaningful elements in the list.  The first option is ("---")
				sleepFor(500);
			} else {
				return;
			}
		} 
	}

}
