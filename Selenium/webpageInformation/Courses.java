package webpageInformation;

import org.openqa.selenium.By;

public class Courses extends MainNavigation
{	
	static public By SelectCourseLink(String item)
	{
		return By.linkText(item);
	}
}
