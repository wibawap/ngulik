package test3;

import java.io.InputStream;
import java.util.Properties;

import org.testng.annotations.Test;

public class TestProperties {
	
	private static final String filePath = "config/config.properties";
	private static Properties myProperties;
	 
    //static initializer
    static
    {
        try
        {
            InputStream propertiesInputStream = ClassLoader.getSystemResourceAsStream(filePath);
            myProperties.load(propertiesInputStream);
        }
        catch (Exception e)
        {
            throw new ExceptionInInitializerError("There was a problem initializing the properties file: " + e.toString());
        }
    }
     
    @Test 
    public void someMethod()
    {
        String foo = myProperties.getProperty("NGINX_HOST");
        System.out.println(foo);
    }

}
