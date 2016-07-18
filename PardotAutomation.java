package automationStuff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Random;

//Converts integers in a ArrayList into a string
class ToString{

	public String convertToString(ArrayList<Integer> nums) {
		StringBuilder builder = new StringBuilder();
		for (int i : nums) {
			builder.append(i);

		}
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}
}

//Randomizes  10 numbers and adds them to an ArrayList
class Randomizer{

	public ArrayList<Integer> toArray(){

		ArrayList<Integer> nums = new ArrayList<Integer>();
		Random rand = new Random();

		for (int i = 0; i < 10; i++){
			int randInt = rand.nextInt(10);
			nums.add(randInt);
		}	
		return nums;
	}

}

class Sleep{

	public void sleep(int seconds){

		try{
			Thread.sleep(seconds);
		}catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}

class LogIn{
	
	public void logIn(WebDriver driver, String user, String pass){
		
		//Logs into https://pi.pardot.com
		driver.findElement(By.id("email_address")).sendKeys(user);
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.name("commit")).click();
		
	}
}


public class PardotAutomation {
	
	static String userName = "pardot.applicant@pardot.com";
	static String passWord = "Applicant2012";
	
	@Test ( description = "Tests List Creation, Duplication, and Createion of orginal list name cases")
	public void pardotListCases()
	{

		//Declarations and initialization of classes
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		ArrayList<Integer> inArray = new ArrayList<Integer>();
		Randomizer rand = new Randomizer();
		ToString convert = new ToString();
		Actions actions = new Actions(driver);
		ClickAndWait cAndW = new ClickAndWait();
		Sleep sleep = new Sleep();
		LogIn login = new LogIn();

		driver.get("https://pi.pardot.com");

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login-form")));
		
		login.logIn(driver, userName, passWord);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("navbar-inner")));

		WebElement element = driver.findElement(By.id("mark-tog"));  //Create new WebElement

		//Creates a new list in Segmentation
		actions.moveToElement(element).perform();  //Mouse over Marketing Menu
		element = driver.findElement(By.xpath("//ul[@id='dropmenu-marketing']/li[11]"));  //Sets new value to element
		actions.moveToElement(element).perform();	//Mouse over Segmentation drop down option
		element = driver.findElement(By.xpath("//ul[@id='dropmenu-marketing']/li[11]/ul/li"));	//Sets new value to element
		sleep.sleep(1000);	//Waits for sub menu of Segmentation to be displayed
		actions.moveToElement(element).click().perform();	//Clicks List option
		cAndW.byId(driver, "listxistx_link_create");	//Clicks '+ Add List' button
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("information_modal")));

		inArray = rand.toArray();  //Calls the Randomize class and sets the value in a ArrayList

		String converted = convert.convertToString(inArray);  //Calls ToString class to convert inArray to a string

		String randomName = "RandomName" + converted;	//Creates a random name string
		driver.findElement(By.id("name")).sendKeys(randomName);  //Enters a random name into name field
		driver.findElement(By.id("save_information")).click();	//Saves list

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("content")));

		//Create a duplicate list with the same name
		driver.findElement(By.linkText("Lists")).click();	//Clicks Lists link in bread crumbs menu
		cAndW.byId(driver, "listxistx_link_create");	//Clicks '+ Add List' button
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("information_modal")));

		driver.findElement(By.id("name")).sendKeys(randomName);	//Enters same random name
		driver.findElement(By.id("save_information")).click();

		sleep.sleep(2000);  //Waits for error message

		WebElement alertMsg = driver.findElement(By.xpath("//div[@class='alert alert-error']"));	//Sets WebElement into alertMsg

		Assert.assertEquals(true, alertMsg.isDisplayed());	//Tests if error message is displayed
		Assert.assertEquals(alertMsg.getText(), "Please correct the errors below and re-submit");	//Tests if correct error message is displaed

		driver.findElement(By.xpath("//div[@class='modal-footer']/a")).click();	//Closes form

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("content")));

		//Edits original list name
		driver.findElement(By.linkText(randomName)).click();	//Go back to list view
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("center-stage")));
		WebElement editLink = driver.findElement(By.xpath("//div[@id='center-stage']/ul/li"));	//Create WebElement for edit link
		actions.moveToElement(editLink).click().perform();	//Click edit link
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dynamicList-form")));
		driver.findElement(By.id("name")).sendKeys("-1");	//Adds '-1' to existing name
		driver.findElement(By.id("save_information")).click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-backdrop  in")));

		//Creates new list with original name
		driver.findElement(By.linkText("Lists")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("content")));
		driver.findElement(By.id("listxistx_link_create")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("information_modal")));
		driver.findElement(By.id("name")).sendKeys(randomName);
		driver.findElement(By.id("save_information")).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='content']/h1")));

		WebElement name = driver.findElement(By.xpath("//div[@id='content']/h1"));

		Assert.assertEquals(name.getText(), randomName);	//Tests if new list is created with original name
		
		//Create new Prospect List
		
		element = driver.findElement(By.id("pro-tog"));  //Create new WebElement

		//Creates a new Prospect List
		actions.moveToElement(element).perform();  //Mouse over Prospect Menu
		element = driver.findElement(By.xpath("//ul[@id='dropmenu-prospects']/li/a"));  //Sets new value to element
		actions.moveToElement(element).click().perform();	//Click Prospect List link
		
		cAndW.byId(driver, "pr_link_create");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pr_form_update")));
		
		driver.findElement(By.id("email")).sendKeys(randomName + "@name.com");
		
		Select campaignDropDown = new Select(driver.findElement(By.id("campaign_id")));	
		Select profileDropDown = new Select(driver.findElement(By.id("profile_id")));
		campaignDropDown.selectByIndex(1);	//Selects campaign
		profileDropDown.selectByIndex(1);	//Selects profile
		driver.findElement(By.xpath("//div[@class='form-actions']/input")).submit();	//Submits form
		sleep.sleep(2000);
		cAndW.byXpath(driver, "//ul[@class='nav']/li[2]/a");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("center-stage")));
	
		WebElement listDropDown = driver.findElement(By.xpath("//div[@class='controls']/div[1]/a/span"));
		
		actions.moveToElement(listDropDown).click().perform();	
		driver.findElement(By.xpath("//ul[@class='chzn-results']/li[contains(text(),'" + randomName + "')]")).click();
		driver.findElement(By.id("added_lists")).submit();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("module-wrapper")));
		
		//Verify prospect is saved in list
		element = driver.findElement(By.id("mark-tog"));
		actions.moveToElement(element).perform();  //Mouse over Marketing Menu
		element = driver.findElement(By.xpath("//ul[@id='dropmenu-marketing']/li[11]"));  //Sets new value to element
		actions.moveToElement(element).perform();	//Mouse over Segmentation drop down option
		element = driver.findElement(By.xpath("//ul[@id='dropmenu-marketing']/li[11]/ul/li"));	//Sets new value to element
		sleep.sleep(1000);	//Waits for sub menu of Segmentation to be displayed
		actions.moveToElement(element).click().perform();	//Clicks List option
		
		driver.findElement(By.linkText(randomName)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("listxProspect_table")));
		WebElement prospectName = driver.findElement(By.xpath("//table[@id='listxProspect_table']/tbody/tr/td[3]/a"));
		Assert.assertEquals(prospectName.getText(), randomName + "@name.com");	//Tests if prospect was added to list after save
		
		//Send mail to list
		element = driver.findElement(By.id("mark-tog"));
		actions.moveToElement(element).perform();  //Mouse over Marketing Menu
		element = driver.findElement(By.xpath("//ul[@id='dropmenu-marketing']/li[7]"));  //Sets new value to element
		actions.moveToElement(element).click().perform();	//Mouse over Segmentation drop down option
		cAndW.byXpath(driver, "//span[@class='link_to_create pull-right']/a");
		driver.findElement(By.id("name")).sendKeys("Test Email");	//Sets name for new e-mail list
		sleep.sleep(1000);
		WebElement chooseCampign = driver.findElement(By.xpath("//form[@id='information_form']/div[4]/div/div"));
		actions.moveToElement(chooseCampign).click().perform();	//Opens campaign selection pop up
		sleep.sleep(1000);
		chooseCampign = driver.findElement(By.xpath("//div[@id='folder-contents']/div[1]/div[1]/div[1]"));	
		actions.moveToElement(chooseCampign).click().perform();	//Chooses campaign
		chooseCampign = driver.findElement(By.id("select-asset"));	
		actions.moveToElement(chooseCampign).click().perform();	//Sets campaign selected
		driver.findElement(By.id("email_type_text_only")).click();	//Selects text only option
		driver.findElement(By.id("from_template")).click();	//Unchecks template option
		driver.findElement(By.id("save_information")).click();	//Saves new e-mail list
		sleep.sleep(2000);
		driver.findElement(By.xpath("//ul[@id='flow_navigation']/li[3]/a")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("send_form")));
		
		listDropDown = driver.findElement(By.xpath("//div[@id='email-wizard-list-select']/div[1]/div/a/span"));	//Clicks drop down menu
		actions.moveToElement(listDropDown).click().perform();	
		sleep.sleep(1000);
		driver.findElement(By.xpath("//div[@class='chzn-search']/input")).sendKeys(randomName);	//Searches for list
		sleep.sleep(1000);
		driver.findElement(By.xpath("//div[@id='email-wizard-list-select']/div[1]/div/div/ul[@class='chzn-results']/li[contains(text(),'" + randomName + "')]")).click();	//Selects list
		
		
		//Log out of pardot
		element = driver.findElement(By.id("menu-account"));	
		actions.moveToElement(element).perform();	//Mouse over account name
		element = driver.findElement(By.xpath("//ul[@id='dropmenu-account']/li[8]"));	
		actions.moveToElement(element).click().perform();	//Click log out option
		
		driver.close();

	}
	
}

