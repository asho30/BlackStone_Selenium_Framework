package com.blackstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.blackstone.base.TestBase;

public class HomePage extends TestBase {
	String welcomeMessage = "Welcome To Dashboard";
	String profileIconCss = "div.user";
	String logoutOptionXpath = "//li[text()='Logout']";
	
	public HomePage() {

		PageFactory.initElements(driver, this);

	}
	
	public void verifySuccessfulLogInAndHomePageRedirection() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement welcomeMessageText = driver.findElement(By.xpath("//p[text()='" + welcomeMessage + "']"));
		wait.until(ExpectedConditions.visibilityOf(welcomeMessageText));
		Assert.assertTrue(welcomeMessageText.isDisplayed());
		System.out.println("User has been logged in Successfully");
	}

	public void printHomePageTitle() {
		System.out.println("The Title Of The Home Page: " + driver.getTitle());
	}

	public void logout() {
		WebElement profileIcon = driver.findElement(By.cssSelector(profileIconCss));
		profileIcon.click();
		WebElement logoutOption = driver.findElement(By.xpath(logoutOptionXpath));
		logoutOption.click();
		System.out.println("User has been logged out");
	}
}
