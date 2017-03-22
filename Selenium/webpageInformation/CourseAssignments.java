package webpageInformation;

import org.openqa.selenium.By;

public class CourseAssignments extends MainNavigation
{
	static public By SelectModule(String item)
	{
		return By.linkText(item);
	}
}
