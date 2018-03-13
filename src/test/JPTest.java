package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class JPTest {

	private String fileConfig 	= "config/configJPTest.properties";
	private String fileDataNews 	= "config/dataNewsTest.properties";
	private String fileDataJobs 	= "config/dataJobTest.properties";
	private String urlNews;
	private String urlJobs;
	private String browser;
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
		urlNews = prop.getProperty("URL_NEWS");
		urlJobs = prop.getProperty("URL_JOBS");
		browser = prop.getProperty("BROWSER");
			
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
	
	// Setup Test Page ...
	public void testPage(String page, String data) throws Exception {
		
		URL url = new URL(page);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestProperty("User-Agent",browser);
		connection.setRequestMethod("GET");
		connection.connect();
		code = connection.getResponseCode();
			
		// Open home page URL..
		driver.get(page);

		// Display URL and Page Title
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
		System.out.println("");
					
		// Start Test Page...	
		FileReader read = new FileReader(data);
		Properties prop = new Properties();
		prop.load(read);
	
		Enumeration<?> e = prop.propertyNames();
		while(e.hasMoreElements()) {
		
		Object key =  e.nextElement();
		String value = prop.getProperty((String) key);
		
		driver.get(page + key);
		
		//Print the response code ...
		StringBuffer sb = new StringBuffer(value);
		sb.append(" ");
		sb.append(code);
		sb.append(" ...TEST");
		System.out.println(sb);
		
		assertion.assertEquals(code,expectedCode,"...Response Code is not 200 !");
		assertion.assertEquals(sb,value,key+" is FAILED !!");
		System.out.println(driver.getTitle());
		assertion.assertAll();
		
		
	}
	
	System.out.println("");	
		
	}
	
	@Test	// Run Test Jakpost News ...
	public void newsTest() {
		
		try {
			
			testPage(urlNews,fileDataNews);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	@Test  // Run Test Jakpost Jobs ...
	public void jobsTest() {
		
		try {
			
			testPage(urlJobs,fileDataJobs);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	
}
