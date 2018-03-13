package test2;

import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class CouchdbTest {

	private String filePath = "config/config.properties";
	private String host;
	private String hostMaster;
	private String hostTarget;
	
	private String browser;
	private int port = 5984;
	private int code;
	
	
	@BeforeTest
	public void serverInit() throws Exception {
		
		//Read Config file ...!
		FileReader reader = new FileReader(filePath);
		Properties properties = new Properties();
		properties.load(reader);
		host = properties.getProperty("COUCHDB_HOST");
		hostMaster 	= properties.getProperty("COUCHDB_HOST_MASTER");
		hostTarget 	= properties.getProperty("COUCHDB_HOST_TARGET");
		browser = properties.getProperty("BROWSER");
		
	}
	
	
	@Test
	public void checkPort() throws Exception {
		
		Socket sock = new Socket(host,port);
		System.out.println("Port "+port+" is open...");
		sock.close();
		
	}
	
	
	public void serviceUp(String couchdbHost) throws Exception {
		
		URL url = new URL(couchdbHost);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestProperty("User-Agent",browser);
		connection.setRequestMethod("GET");
		connection.connect();
		code = connection.getResponseCode();	
				 		
	}
	
	@Test
	public void testServiceUpMaster() {
		
		try {
			serviceUp(hostMaster);
			System.out.println("Response Code Master : "+ code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testServiceUpTarget() {
		
		try {
			serviceUp(hostTarget);
			System.out.println("Response Code Target : "+ code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
