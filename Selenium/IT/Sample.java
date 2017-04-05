package IT;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Sample {
  private WebDriver driver;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception
  {
	System.setProperty("webdriver.chrome.driver", "SeleniumDrivers/chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void testSample() throws Exception 
  {
  }

  @After
  public void tearDown() throws Exception 
  {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) 
      fail(verificationErrorString);
  }

  @SuppressWarnings("unused")
private boolean isElementPresent(By by) 
  {
    try 
    {
      driver.findElement(by);
      return true;
    } 
    catch (NoSuchElementException e) 
      {		return false;	}
  }

  @SuppressWarnings("unused")
private boolean isAlertPresent() 
  {
    try 
    {
      driver.switchTo().alert();
      return true;
    } 
    catch (NoAlertPresentException e) 
    {	return false;	}
  }

  @SuppressWarnings("unused")
private String closeAlertAndGetItsText() 
  {
    try 
    {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) 
        alert.accept(); 
      else 
        alert.dismiss();
      return alertText;
    } 
    finally 
    {	acceptNextAlert = true;	   }
  }
}
