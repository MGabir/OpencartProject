package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
	WebDriver driver;
	public BasePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}

}
// this is the parent of all the object class need to be called in them.