package IT;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import webpageInformation.CourseAssignments;
import webpageInformation.CourseModule;
import webpageInformation.Courses;
import webpageInformation.Login;

public class RemoveGroupMember 
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
		  Login.LoginUser(driver, "user1@umich.edu", "password");
		  driver.findElement(Courses.SelectCourseLink("course1Atitle")).click();
		  driver.findElement(CourseAssignments.SelectModule("mod1-course1Atitle")).click();
		  driver.findElement(CourseModule.CreateEditGroupButton()).click();
		  driver.findElement(CourseModule.RemoveGroupMember(5)).click();
		  driver.findElement(CourseModule.RemoveGroupMember(4)).click();
	  }

	  @After
	  public void tearDown()
	  {
		  driver.quit();
	  }

}
