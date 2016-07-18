package automationStuff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;

public class ClickAndWait {

	public void byXpath(WebDriver driver, String element){

		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
		driver.findElement(By.xpath(element)).click();

	}

	public void byId(WebDriver driver, String element){

		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(element)));
		driver.findElement(By.id(element)).click();

	}

	public void byName(WebDriver driver, String element){

		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element)));
		driver.findElement(By.name(element)).click();

	}

	public void byClassName(WebDriver driver, String element){

		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element)));
		driver.findElement(By.className(element)).click();

	}
	
	public void byLinkText(WebDriver driver, String element){

		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.name(element)));
		driver.findElement(By.linkText(element)).click();

	}


}
