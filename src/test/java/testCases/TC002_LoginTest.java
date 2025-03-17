package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	@Test(groups={"Sanity", "Master"})
	public void verify_login()
	{
		logger.info("*******Starting TC002_LoginTest********");
		try
		{
		//home page 
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();//action from the homepage 
		hp.clickLogin();//action from the homepage 
		
		//login page
		LoginPage lp=new LoginPage(driver); // pass the driver as param
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//my account page exist or not
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExists();
		
		Assert.assertEquals(targetPage, true,"Login Failed");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("*******Finished TC002_LoginTest********");
	}
	

}
