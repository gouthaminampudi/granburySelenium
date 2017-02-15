package com.goutham.selenium.javaseleniummaven;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Properties;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.goutham.selenium.pageobjects.BasePageObject;
import com.goutham.selenium.pageobjects.HomePage;


/**
 * This is the base class for all Selenium implementations.
 * 
 *
 */
public class SeleniumBase {
	public static final String SELENIUM_CSV_DATA_DIRECTORY   = "/data/csvtestdata/";

	public DesiredCapabilities caps = new DesiredCapabilities();
	public static Properties configProperties = new Properties();

	protected String startURL;
	protected String testName = "Unnamed Test";
	

	public boolean useSauce = false;

	
	public WebDriver driver;

	protected String browser;
	
	

	
	// Loads in all properties files at the very beginning. You can "chain" properties files. Simply add a new properties file to the resources folder and load it here.
	static {
	    InputStreamReader in;
        try {
	        in = new InputStreamReader(SeleniumBase.class.getResourceAsStream("/config/SeleniumConfiguration.properties"));
	        configProperties.load(in);
			in.close();
        } catch (FileNotFoundException e) {
	        System.err.println(e);
        } catch (IOException e) {
	        System.err.println(e);
        } 
	}  // end static initializer
	
	
	/**
	 * Prefer this constructor. It will load user and state information for login.
	 */
	public SeleniumBase() {
		init();
			}
			
	/* This happens first */
	private void init() {
	    // Identify driver executables:
        System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");  
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe"); 
	    
        try {
            setupBrowser();
            setupSelenium();
        } catch (Exception e) {
            System.err.println(e);
        }
	}
	
	

	

	protected void setupBrowser() {

	        browser = configProperties.getProperty("test.defaultBrowser");
	}
	
	
	public void setupSelenium() throws IOException {
		startURL = "http://www.granburyrs.com";
	    setupSelenium(testName);
	}
	
	public void setupSelenium(String testName) throws IOException  {
		startURL = "http://www.granburyrs.com";
		openBrowser(startURL, testName);
	}

	protected WebDriver openBrowser(String url) throws MalformedURLException {
	    return openBrowser(url, "Unnamed Test");
	}
	
	/**
	 * This method open the appropriate browser (with the correct capabilities).
	 * 
	 */
	protected WebDriver openBrowser(String url, String testName) throws MalformedURLException {

		WebDriverFactory factory = new WebDriverFactory(configProperties);
		driver = factory.getDriver(browser);

		// Set Resolution:
		driver.manage().window().setSize(new Dimension(1280, 1024));

		// Update the rule with the newly created WebDriver
		driver.get(url);

		return driver;
    }
	
	
	
	/**
	 * This method closes the browser
	 * 
	 * 
	 */
	public void closeBrowser() {
	    if (driver != null) {
	        driver.close();
	        driver = null;
	    }
	}
	
	public BasePageObject createHomePage(){
		HomePage hPage = new HomePage(driver);
		return hPage;
	}
	

	
}
