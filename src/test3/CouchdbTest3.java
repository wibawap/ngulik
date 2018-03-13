package test3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class CouchdbTest3 {

	private String filePath = "config/config.properties";
	private String host;
	private String hostUuids;
	private String hostMaster;
	private String hostTarget;
	
	private String browser;
	private int port = 5984;
	private int code;
	private String codeMess;
	
	private String uuids;
	
	
	
	@BeforeTest
	public void serverInit() throws Exception {
		
		//Read Config file ...!
		FileReader reader = new FileReader(filePath);
		Properties properties = new Properties();
		properties.load(reader);
		host = properties.getProperty("COUCHDB_HOST");
		hostUuids = properties.getProperty("COUCHDB_HOST_UUIDS");
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
		codeMess = connection.getResponseMessage();	
		
		// Read URL Contents ...
		InputStreamReader input =  new InputStreamReader(url.openStream());
		BufferedReader reader = new BufferedReader(input);
		
		String line;
		while((line = reader.readLine()) != null) {
			
			System.out.println(line);
			
		}
		reader.close();
				 		
	}
	
	/*
	@Test
	public void testServiceUpMaster() {
		
		try {
			serviceUp(hostMaster);
			System.out.println("Response Code Master : "+ code+" "+codeMess);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	*/
	
	
	/*
	@Test
	public void testServiceUpTarget() {
		
		try {
			serviceUp(hostTarget);
			System.out.println("Response Code Target : "+ code+" "+codeMess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	*/
	
	
	@Test
	public void createUuids() throws Exception {
		
		// Read URL Contents ...
		
		URL url = new URL(hostUuids);
		InputStreamReader input = new InputStreamReader(url.openStream());
		BufferedReader reader = new BufferedReader(input);

		String line;
		while ((line = reader.readLine()) != null) {
			
			uuids = line.substring(11,43);
			System.out.println(uuids);

		}
		reader.close();
		
		
	}
	
	@Test
	public void testInputNewDoc() throws IOException {
		
		//System.out.println(uuids);
		String inputNewDoc = hostMaster+"/"+uuids+"  -d '{\"title\":\"I Remember You\",\"artist\":\"Skid Row\"}'";
		//String inputNewDoc = hostMaster+"/"+uuids;
		System.out.println(inputNewDoc);
			
	}
	

}
