package webpageInformation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login
{
	static String baseUrl = "http://localhost:8080/#/login";
	
	static public String Url()
	{
		return baseUrl;
	}
	
	static public By UsernameTextBox()
	{
		return By.id("username");
	}

	static public By PasswordTextBox()
	{
		return By.id("password");
	}
	
	static public By LoginButton()
	{
		return By.cssSelector("button.btn.btn-primary");
	}
	
	static public void LoginUser(WebDriver driver, String user, String password)
	{
		driver.get((Login.Url()));
		driver.findElement(Login.UsernameTextBox()).clear();
		driver.findElement(Login.UsernameTextBox()).sendKeys(user);
		driver.findElement(Login.PasswordTextBox()).clear();
	    driver.findElement(Login.PasswordTextBox()).sendKeys(password);
	    driver.findElement(Login.LoginButton()).click();
	}
}
