package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
public static WebDriver driver;
public Logger logger; // for log4j
public Properties p;  // for getting the information from the properties class
	
	@BeforeClass(groups= {"Sanity", "Regression","Master"} )
	@Parameters({"os","browser"})
	public void setup(String os , String browser) throws IOException
	{
		
		// loading confing.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		logger = LogManager.getLogger(this.getClass());
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			//operating system os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if (os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if (os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			//browser
			switch(browser.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			
			default: System.out.println("No matching browser"); return;
			}
			driver = new RemoteWebDriver(new URL("http://192.168.1.53:4444/wd/hub"),capabilities);
		} 
		
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(browser.toLowerCase())
			{
				case"chrome":driver = new ChromeDriver(); break;
				case"edge":driver = new EdgeDriver(); break;
				default : System.out.println("Invalid browser name..."); return;
			}
		}
		
		
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL")); // reading url from properties file
		driver.manage().window().maximize();
		
	}
	
	
	
	public String randomeString()
	{
		String generatedstring =RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}
	
	public String randomeNumber()
	{
		String generatednumber =RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}
	public String randomeAlphaNumber()
	{
		String generatedstring =RandomStringUtils.randomAlphabetic(5);
		String generatednumber =RandomStringUtils.randomNumeric(10);
		return (generatedstring+generatednumber+"@");
	}
	public String captureScreen(String tname) throws IOException
	{
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(0));
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+ tname +"_"+timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
	@AfterClass
	public void tearDown()
	{
		driver.close();
	}
	

}
