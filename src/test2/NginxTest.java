package test2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NginxTest {
	
	public static final String FILEPATH = "config/config.properties";
	private String host;
	private String hostHttp;
	private int port = 80;
	private String browser;
	private int code;
	
	@BeforeTest
	public void readConf() {
		
		//Read Config file ...!
		FileReader reader = null;
		try {
			
			reader = new FileReader(NginxTest.FILEPATH);
			
		} catch (FileNotFoundException e) {
			System.out.println("Config File Not Found ..! ");
		}
		
		try {
			
			Properties properties = new Properties();
			properties.load(reader);
			
			host = properties.getProperty("NGINX_HOST");
			hostHttp = properties.getProperty("NGINX_HOST_HTTP");
			browser = properties.getProperty("BROWSER");
			
		} catch (IOException e) {
			System.out.println("Couldn't Read Config File..!");
		}
		
	}
	
	@Test
	public void checkPort() {
		
		Socket sock = null;
		try {
			sock = new Socket(host,port);
			System.out.println("Port "+port+" is open...");
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		try {
			sock.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void checkPage() throws Exception {
		
		String hostHttpPort = hostHttp+":"+port;
		URL url = new URL(hostHttpPort);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestProperty("User-Agent",browser);
		connection.setRequestMethod("GET");
		connection.connect();
		code = connection.getResponseCode();
		System.out.println("Response Code is : "+code);
		
	}

}
