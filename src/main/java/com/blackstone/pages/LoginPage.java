package com.blackstone.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.blackstone.base.TestBase;

public class LoginPage extends TestBase {
	String emailTextBoxCss = "input[placeholder='Enter Email']";
	String passwordTextBoxCss = "input[placeholder='Enter Password']";
	String sgininButtonCss = "button.block";
	
	public LoginPage() {

		PageFactory.initElements(driver, this);

	}

	public void login(String username, String password) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement emailTextBox = driver.findElement(By.cssSelector(emailTextBoxCss));
		WebElement passwordTextBox = driver.findElement(By.cssSelector(passwordTextBoxCss));
		WebElement sgininButton = driver.findElement(By.cssSelector(sgininButtonCss));
		wait.until(ExpectedConditions.visibilityOf(emailTextBox));
		System.out.println("Page has been loaded");
		emailTextBox.sendKeys(username);
		passwordTextBox.sendKeys(password);
		sgininButton.click();
		System.out.println("User has been logged in");
		return;
	}
	
	public void verifySuccessfulLogOutAndLoginPageRedirection() {
		WebElement sgininButton = driver.findElement(By.cssSelector(sgininButtonCss));
		Assert.assertTrue(sgininButton.isDisplayed());
		System.out.println("User has been logged out Successfully");
	}
}
