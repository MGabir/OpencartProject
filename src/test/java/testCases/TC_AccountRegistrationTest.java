package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.accountRegistrationPage;
import testBase.BaseClass;

public class TC_AccountRegistrationTest extends BaseClass {
	
	
	@Test(groups={"Regression", "Master"})
	public void verify_account_registration() throws InterruptedException
	{
		
		logger.info("*******Starting TC_AccountRegistrationTest********");
		
		try
		{
		
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("clicked on my account link........");
		hp.clickRegister();
		logger.info("clicked on the regester from the drop down......");
		
		accountRegistrationPage regpage = new accountRegistrationPage(driver);
		logger.info("Providing customer details........");
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com");
		regpage.setTelephone(randomeNumber());
		String passowrd = randomeAlphaNumber();
		regpage.sedPassword(passowrd);
		regpage.sedConfirmPassword(passowrd);
		regpage.setPrivacyPolicy();
		Thread.sleep(2000);
		regpage.clickContinue();
		
		logger.info("validating expected message...");
		String confmsg = regpage.getConfirmationMsg();
		
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed....");
			logger.debug("Debug logs....");
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		catch(Exception e)
		{
			
			Assert.fail();
		}
		
		logger.info("*******Finishing the TC_AccountRegistrationTest********");
		
	}
	

	
	

}
