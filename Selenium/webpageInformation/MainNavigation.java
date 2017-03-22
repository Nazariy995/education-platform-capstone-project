package webpageInformation;

import org.openqa.selenium.By;

public class MainNavigation
{
	static public By CoursesButton()
	{
		return By.cssSelector("div.navText.ng-binding");
	}
	
	static public By AccountButton()
	{
		return By.cssSelector("span.glyphicon.glyphicon-user");
	}
	
	static public By LogoutButton()
	{
		return By.cssSelector("span.glyphicon.glyphicon-off");
	}
}
