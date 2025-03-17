package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="Datadriven") //getting data provider from different class
	public void verify_loginDDT (String email , String pwd, String exp) throws InterruptedException
	{
		logger.info("*******Starting TC003_LoginDDT********");
		
		try
		{
			//home page 
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//login page
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
			
			//my account page exist or not
			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPageExists();
			
			
			if(exp.equalsIgnoreCase("Valid"))
			{
				if(targetPage == true)// target page means login successful
				{
					macc.clickLogout();
					Assert.assertTrue(true);				
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetPage == true)
				{
					macc.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
			
		
		}catch(Exception e)
		{
			Assert.fail();
		}
		Thread.sleep(1000);
		
		logger.info("*******Finished TC003_LoginDDT********");
	}

}
