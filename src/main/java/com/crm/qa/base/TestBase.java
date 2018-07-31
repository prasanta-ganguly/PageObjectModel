package com.crm.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.crm.qa.util.TestUtil;
import com.crm.qa.util.WebEventListener;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	
	//reference variable declaration for extent reports
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	//public static Logger log;
	
	//This is for implement WebDriverEventListener
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	
	public TestBase() {
		
		
		try {
		prop = new Properties();	
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\crm\\qa\\config\\config.properties");
				prop.load(ip);
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	public static void initialization() {
		
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\eclipse_workspace\\Common Browser Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			
		} else if(browserName.equals("Mozilla")){
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");// not to get any unwanted log for geco driver
			FirefoxOptions options = new FirefoxOptions();
			options.setBinary("C:\\Program Files\\Mozilla Firefox Latest\\firefox.exe");
			//System.setProperty("webdriver.gecko.driver", "C:\\eclipse_workspace\\Common Browser Drivers\\geckodriver.exe");
			driver = new FirefoxDriver(options);
			
		}else if(browserName.equals("Edge")){
			System.setProperty(EdgeDriverService.EDGE_DRIVER_LOG_PROPERTY, "null");// not to get unwanted logs in console
			//System.setProperty("webdriver.edge.driver", "C:\\eclipse_workspace\\Common Browser Drivers\\MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			
			}else if(browserName.equals("IE")){
				System.setProperty(InternetExplorerDriverService.IE_DRIVER_SILENT_PROPERTY, "true");//not to get unwanted logs in console
				//System.setProperty("webdriver.ie.driver", "C:\\eclipse_workspace\\Common Browser Drivers\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				}
		//this will initialize WebEventListener
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
	}
	
	public void takeScreenshots(String methodName){
		
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+methodName+"_"+".png"));
			} catch (IOException e) {
			e.printStackTrace();
			}
			//test.log(LogStatus.INFO, "Screenshot->"+test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+methodName+"_"+".png"));

		}
	
	public void takingScreenshots(String methodName) throws IOException{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+methodName+"_"+".png"));
		} catch (IOException e) {
		e.printStackTrace();
		}
		test.log(Status.FAIL, "Screenshot->"+test.addScreenCaptureFromPath(System.getProperty("user.dir")+"//screenshots//"+methodName+"_"+".png"));
	}
		
		/*public void startextentReport() {
			extent = new ExtentReports(System.getProperty("user.dir")+"//extent-reports//extent_report.html",true);
			extent.addSystemInfo("Host Name", "LocalHost");
			extent.addSystemInfo("Environment", "QA");
			extent.addSystemInfo("User Name", "Prasanta");
			extent.loadConfig(new File(System.getProperty("user.dir")+"//ReportsConfig.xml"));
		}*/
	
	@BeforeSuite
	public void setup() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\extent-reports\\extent.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"\\config.xml");
		}
	
	@BeforeMethod
	public void getMethodNameInReport(Method methodName) {
		test = extent.createTest(methodName.getName());
		test.log(Status.INFO, methodName.getName()+" test started");
	}
	
	/*@AfterMethod
	public void getResult(ITestResult result) throws IOException {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			test.fail(MarkupHelper.createLabel(result.getName()+"Test case failed,", ExtentColor.RED));
			takingScreenshots(result.getMethod().getMethodName());//TestUtil.takeScreenshotForWebEvenListener();
			test.fail(result.getThrowable());
		}
			else if(result.getStatus()==ITestResult.SUCCESS) {
				test.pass(MarkupHelper.createLabel(result.getName()+"Test case passed", ExtentColor.GREEN));
			}
		
			else {
				test.skip(MarkupHelper.createLabel(result.getName()+"Test case skipped", ExtentColor.YELLOW));
				test.skip(result.getThrowable());
			}
	}*/
	
	/*@AfterSuite
	public void endReport() {
		extent.flush();
	}*/
}
