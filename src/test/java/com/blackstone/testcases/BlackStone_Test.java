package com.blackstone.testcases;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.blackstone.base.TestBase;
import com.blackstone.pages.LoginPage;
import com.blackstone.pages.HomePage;
import com.blackstone.utils.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class BlackStone_Test extends TestBase {

	String role, username, password;
	int startRowNumber, endRowNumber;
	LoginPage loginPage;
	HomePage homePage;

	public BlackStone_Test() {
		super();
	}

	@BeforeSuite()
	public void setExtent() {

		extent = new ExtentReports("AutoReport/index.html", true);
		extent.addSystemInfo("Framework", "Page Object");
		extent.addSystemInfo("Author", "Ahmed Ashour");
		extent.addSystemInfo("Enviroment", "Win 11");
		extent.addSystemInfo("Test", "BlackStone Test");

	}

	@BeforeMethod
	public void start(Method method) throws InterruptedException, Throwable {
		logger = extent.startTest(method.getName());
		initialization(prop.getProperty("url")); // From config.properties
		// The following for multiple data lines, but for a single data line row number = end row number = 1.
		startRowNumber = Integer.parseInt(prop.getProperty("startRowDataNumber"));
		endRowNumber = Integer.parseInt(prop.getProperty("endRowDataNumber"));
		pagesInitialization();
	}

	@Test(priority = 1)
	public void TC_PrintTheTitleOfTheHomePage() throws Exception {
		for (int rowNumber = startRowNumber; rowNumber <= endRowNumber; rowNumber++) {
			// read test data
			readTestData(rowNumber);
			loginPage.login(username,password);
			homePage.verifySuccessfulLogInAndHomePageRedirection();
			homePage.printHomePageTitle();
			homePage.logout();
			loginPage.verifySuccessfulLogOutAndLoginPageRedirection();
		}
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws Throwable {

		if (result.getStatus() == ITestResult.FAILURE) {

			logger.log(LogStatus.FAIL, "Test Failed " + result.getThrowable());
			String picturePath = TestUtil.TakeSnapshot(driver, result.getName());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(picturePath));

		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test case Skipped is " + result.getName());

		} else {
			logger.log(LogStatus.PASS, "Test passed");
			String picturePath = TestUtil.TakeSnapshot(driver, result.getName());
			logger.log(LogStatus.PASS, logger.addScreenCapture(picturePath));

		}

		extent.endTest(logger);
		driver.close();
	}

	@AfterSuite()
	public void endReport() {

		extent.flush();
		extent.close();
	}

	private void pagesInitialization() {
		loginPage = new LoginPage();
		homePage = new HomePage();
	}

	private void readTestData(int rowNumber) throws IOException {
		String inputFileName = prop.getProperty("testDataFolderPath");
		String sheetName = prop.getProperty("sheetName");
		role = TestUtil.readFromExcelFile(inputFileName, sheetName, "Role", rowNumber);
		username = TestUtil.readFromExcelFile(inputFileName, sheetName, "Username", rowNumber);
		password = TestUtil.readFromExcelFile(inputFileName, sheetName, "Password", rowNumber);
		System.out.println("Data has been loaded");
		return;
	}
}
