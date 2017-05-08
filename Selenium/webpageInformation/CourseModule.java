package webpageInformation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CourseModule extends MainNavigation
{
	static public void SelectUserFromDropDown(WebDriver driver, String item)
	{
	    new Select(driver.findElement(By.xpath("//select"))).selectByVisibleText(item);
	}
        
	static public By AddGroupMemberButton()
	{
		return By.cssSelector("button.btn.btn-primary");
	}
    
	static public By FinalizeButton()
	{
		return By.xpath("//div/button");
	}
	
	static public By CreateEditGroupButton()
	{
		return By.xpath("(//button[@type='button'])[2]");
	}
	
	static public By RemoveGroupMember(int memberIndex)
	{
		String removeMemberAtIndex = String.format("//li[%1$d]/button", memberIndex);
		return By.xpath(removeMemberAtIndex);
	}
}
