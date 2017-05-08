package IT;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import webpageInformation.Account;
import webpageInformation.Courses;
import webpageInformation.Login;

public class ChangeUserPassword {

	 private WebDriver driver;

	 @Before
	 public void setUp()
	 {
		System.setProperty("webdriver.chrome.driver", "SeleniumDrivers/chromedriver.exe");
	    driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  }

	  @Test
	  public void test()
	  {
		  Login.LoginUser(driver, "user1@umich.edu", "password");
		  driver.findElement(Courses.AccountButton()).click();
		  driver.findElement(Account.NewPasswordField()).clear();
		  driver.findElement(Account.NewPasswordField()).clear();
		  driver.findElement(Account.NewPasswordField()).sendKeys("password1");
		  driver.findElement(Account.RetypePasswordField()).clear();
		  driver.findElement(Account.RetypePasswordField()).sendKeys("password1");
		  
		  driver.findElement(Account.LogoutButton()).click();
		  Login.LoginUser(driver, "user1@umich.edu", "password");
	  }

	  @After
	  public void tearDown()
	  {
		  driver.quit();
	  }

}
