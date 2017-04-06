package webpageInformation;

import org.openqa.selenium.By;

public class CourseNavigation 
{
	static public By AssignmentsButton()
	{
		return By.cssSelector("div.childNavWrapper > ul > li.ng-scope > a > div.navLiWrapper > div.navText.ng-binding");
	}
	
	static public By GradesButton()
	{
		return By.cssSelector("div.childNavWrapper > ul > li.ng-scope > a > div.navLiWrapper > div.navIcon > span.glyphicon.glyphicon-book");
	}
}
