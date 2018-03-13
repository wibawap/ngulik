package test2;

import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class PgsqlTest {
	
	private String filePath = "config/config.properties";
	private String host;
	private String dbName;
	private String user;
	private String passwd;
	private String randomQuery;
	private int port = 5432;
	
	
	@BeforeTest
	public void serverInit() throws Exception {
		
		//Read Config file ...!
		FileReader reader = new FileReader(filePath);
		Properties properties = new Properties();
		properties.load(reader);
		host 	= properties.getProperty("PGSQL_HOST");
		dbName	= properties.getProperty("PGSQL_DB");
		user		= properties.getProperty("PGSQL_USER");
		passwd	= properties.getProperty("PGSQL_PASSWD");
		randomQuery = properties.getProperty("RANDOM_QUERY");
		
	}
	
	
	@Test
	public void checkPort() throws Exception {
		
		Socket sock = new Socket(host,port);
		System.out.println("Port "+port+" is open...");
		sock.close();
		
	}
	
	
	@Test
	public void isServiceUp() throws SQLException, IOException {
		
			String url = "jdbc:postgresql://"+host+"/"+dbName+"?user="+user+"&password="+passwd;
			Connection conn = DriverManager.getConnection(url);
			System.out.println("Connected to PGQSL DB");
		
			Statement smt = conn.createStatement();
			ResultSet rs = smt.executeQuery(randomQuery);
		
			while(rs.next()) {
				String name = rs.getString("name");
				System.out.println("Package Name :" +name);
			}
			 		
	}
	
}
