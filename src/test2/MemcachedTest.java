package test2;

import java.io.FileReader;
import java.net.InetSocketAddress;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
//import org.testng.asserts.SoftAssert;

import net.spy.memcached.MemcachedClient;

public class MemcachedTest {
	
	private String filePath = "config/config.properties";
	private String host;
	private String host2;
	private int port = 11211;
	private String key;
	private String value;
	//private String value_config;
	//private SoftAssert assertion;

	
	@BeforeTest
	public void readConf() throws Exception {
		
		//Read Config file ...!
		FileReader reader = new FileReader(filePath);
		Properties properties = new Properties();
		properties.load(reader);
		host = properties.getProperty("MEMCACHED_HOST");
		host2 = properties.getProperty("MEMCACHED_HOST_2");
		key = properties.getProperty("KEY");
		value = properties.getProperty("VALUE");
		//value_config = properties.getProperty("VALUE_CONFIG");
		
	}
	
	
	@Test
	public void testMemcached() {
		
		try {
			MemcachedClient mc = new MemcachedClient(new InetSocketAddress(host, port));
			mc.set(key, 3600, value);
			System.out.println("Object 1 : "+ mc.get(key));
			//assertion.assertEquals(mc.get(key),value_config,"FAILED !!");
			//assertion.assertAll();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}
	
	
	public void testMemcachedReplication() {
		
		try {
			MemcachedClient mc2 = new MemcachedClient(new InetSocketAddress(host2, port));
			
			System.out.println("Object 2 : "+ mc2.get(key));
				
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

}
