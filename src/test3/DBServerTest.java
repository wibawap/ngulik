package test3;

import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbInfo;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DBServerTest {
	
	
	private CouchDbClient dbClient;

	@BeforeClass
	public void setUpClass() {
		dbClient = new CouchDbClient("coba", true, "http", "127.0.0.1", 5984, "admin", "admin123");
	}

	@AfterClass
	public void tearDownClass() {
		dbClient.shutdown();
	}

	@Test
	public void dbInfo() {
		CouchDbInfo dbInfo = dbClient.context().info();
		assertNotNull(dbInfo);
	}
	
	@Test
	public void serverVersion() {
		String version = dbClient.context().serverVersion();
		assertNotNull(version);
	}

	@Test
	public void compactDb() {
		dbClient.context().compact();
	}

	@Test
	public void allDBs() {
		List<String> allDbs = dbClient.context().getAllDbs();
		assertNotNull(allDbs.size());
	}

	

	

}
