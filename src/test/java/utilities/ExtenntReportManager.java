package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtenntReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter; // UI of the report 
	public ExtentReports extent; // populate common information on the report
	public ExtentTest test; // creating test case entries in the report and update status of the test methods
	String repName;
	
	public void onStart(ITestContext context) {
		
		/*SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp = df.format(dt);*/
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
		// to avoid variables simple date format directly get the object by new simpledate format(yyyy,mm,dd,hh,mm,ss.format (new date())
		repName = "Test-Report-"+timeStamp+".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+ repName);
		sparkReporter.config().setDocumentTitle(" operncart Automation Report"); // title of the report
		sparkReporter.config().setReportName("opencart Functional Testing");//name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		// capture the operating system values form the master.xml to set the system information and os in the report
		String os = context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("browser", browser);
		
		//capture the group name form the xml file and display them in the report.
		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();// capture all the group names and store them in a list of collection.
		if(!includedGroups.isEmpty()) { // if the list is not empty capture the value 
			extent.setSystemInfo("Groups", includedGroups.toString());// and print the value in the report
		}
	  }
	
	/*public void onTestStart(ITestResult result) {
		System.out.println("Test started........");
	  }*/
	
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); //from the rest what class executed get the result and  get the test class name
		test.assignCategory(result.getMethod().getGroups());// to display groups in report
		test.log(Status.PASS, result.getName()+"got successfully executed"); // update status p/f/s
	  }
	
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); // create a new entry in the report
		test.assignCategory(result.getMethod().getGroups());// to display groups in report
		test.log(Status.FAIL, result.getName()+"got FAILED"); // update status p/f/s
		test.log(Status.INFO,result.getThrowable().getMessage());
		try
		{
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromBase64String(imgPath);
		}catch(IOException e1)
		{
			e1.printStackTrace(); // it will print the error message in the console
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); // create a new entry in the report
		test.assignCategory(result.getMethod().getGroups());// to display groups in report
		test.log(Status.SKIP, result.getName()+"got SKIPED"); // update status p/f/s
		test.log(Status.INFO,result.getThrowable().getMessage());
		
	  }
	
	public void onFinish(ITestContext context) {
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+ repName;
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI()); // will auto open the browser 
		}
		catch(IOException e)
		{
			e.printStackTrace(); 
		}
	  }
	

}
