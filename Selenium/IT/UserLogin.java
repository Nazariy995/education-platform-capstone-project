package IT;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import junit.framework.TestCase;
import webpageInformation.Login;

public class UserLogin extends TestCase
{
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
		  driver.get((Login.Url()));
		  driver.findElement(Login.UsernameTextBox()).clear();
		  driver.findElement(Login.UsernameTextBox()).sendKeys("user1@umich.edu");
		  driver.findElement(Login.PasswordTextBox()).clear();
		  driver.findElement(Login.PasswordTextBox()).sendKeys("password");
		  driver.findElement(Login.LoginButton()).click();
	  }

	  @After
	  public void tearDown()
	  {
	    driver.quit();
	  }
}
