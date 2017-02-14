package com.goutham.selenium.javaseleniummaven;

import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This factory creates instance of WebDrivers to be used on your local machine.
 * For WebDrivers that connect to Sauce Labs, see RemoteWebDriver factory.
 *
 */
public class WebDriverFactory {
    Properties configProperties;
    
    public WebDriverFactory(Properties configProperties) {
        this.configProperties = configProperties;
    }
    
    public WebDriver getDriver(String browser) {
        WebDriver driver;
        DesiredCapabilities caps;
        
        if (browser.equalsIgnoreCase("InternetExplorer")) 
        {
            caps = DesiredCapabilities.internetExplorer();          
            driver = new InternetExplorerDriver(caps);
        } 
        else if (browser.equalsIgnoreCase("Firefox")) 
        {
            String downloadDir = configProperties.getProperty("selenium.download.directory");
            
            FirefoxProfile profile = new FirefoxProfile();

            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.dir", downloadDir);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/rtf");
            
            driver = new FirefoxDriver(profile);
        } 
        else if (browser.equalsIgnoreCase("Chrome")) 
        {
            String downloadDir = configProperties.getProperty("selenium.download.directory");
            
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadDir);
            
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("--test-type");
            
            caps = DesiredCapabilities.chrome();
            caps.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver(caps);
        } 
        else if (browser.equalsIgnoreCase("PhantomJS"))
        {
        	caps = DesiredCapabilities.phantomjs();
        	caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "src/test/resources/drivers/phantomjs.exe");
        	
            driver = new PhantomJSDriver(caps);
        }
        else
        {
        	driver = new ChromeDriver();
        }
        
        return driver;
    }
}
