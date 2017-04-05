package webpageInformation;

import org.openqa.selenium.By;

public class Account extends MainNavigation
{
	static public By NewPasswordField ()
	{
		return By.name("newPassword");
	}
	
	static public By RetypePasswordField()
	{
		return By.name("retypePassword");
	}
}
