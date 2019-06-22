package eu.b24u.cucumber.page;

import eu.b24u.cucumber.init.Setup;
import eu.b24u.cucumber.utils.SeleniumUtil;
import org.openqa.selenium.WebDriver;

public class BasePage {

	protected SeleniumUtil selenium;
	protected WebDriver driver;

	protected WebDriver getDriver() {
		return driver;
	}

	public BasePage() {
		this.driver = Setup.driver;
		this.selenium = Setup.selenium;
	}

}
