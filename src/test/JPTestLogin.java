package test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class JPTestLogin {
	
	private String fileConfig = "config/configJPTest.properties";
	private String urlLogin;
	private String urlAccInfo;
	private String browser;
	private String user;
	private String passwd;
	private int expectedCode = 200;
	private int code;
	private WebDriver driver;
	private SoftAssert assertion;
	private FileReader read;
	
	@BeforeClass  // Read config file from 'configJPTest.properties'...
	public void readConfig() {
		
		try {
			read = new FileReader(fileConfig);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(read);
		} catch (IOException e) {
			e.printStackTrace();
		}
		urlLogin = prop.getProperty("URL_LOGIN");
		urlAccInfo = prop.getProperty("URL_ACCOUNT_INFO");
		browser = prop.getProperty("BROWSER");
		user = prop.getProperty("USER");
		passwd = prop.getProperty("PASSWD");
			
	}
	
	@BeforeClass  // Setup the Headless Browser...
	public void setupBrowser() {	
	
		// Use Chrome Headless mode
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
			        
		// Create Chrome driver to drive the browser
		driver = new ChromeDriver(options);
			     				
		// Use Soft Assert ...
		assertion = new SoftAssert();
	
	}

	@Test
	public void testLogin()  throws Exception {
		
		URL url = new URL(urlLogin);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestProperty("User-Agent",browser);
		connection.setRequestMethod("GET");
		connection.connect();
		code = connection.getResponseCode();
				
		// Open home page URL..
		driver.get(urlLogin);
			
		// Display URL and Page Title
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
		System.out.println("");
								
		// Start Test Login...
		assertion.assertEquals(code, expectedCode, driver.getCurrentUrl() +" is FAILED !!!");
		
		driver.findElement(By.name("email")).sendKeys(user);
		driver.findElement(By.name("password")).sendKeys(passwd);
	    driver.findElement(By.cssSelector("button.btn.btn-red")).click();
	    
	    driver.get(urlAccInfo);
		
	    System.out.println("Login ...TEST");
	    	assertion.assertNotNull(driver.findElement(By.id("hasilData")).getText(), "Login Failed !");
	    	assertion.assertAll();
	    
	    	System.out.println("");
	    	System.out.println("Testing Jakpost Login Completed...!");
	}

}
