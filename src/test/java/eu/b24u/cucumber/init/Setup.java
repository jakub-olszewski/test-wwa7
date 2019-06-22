package eu.b24u.cucumber.init;

import java.io.IOException;

import cucumber.api.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import eu.b24u.cucumber.utils.OSCheck;
import eu.b24u.cucumber.utils.SeleniumUtil;


public class Setup {

	public Setup() {
	}

	public static SeleniumUtil selenium;
	
	public static WebDriver driver;

	protected void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	boolean silentMode;

	@cucumber.api.java.Before
	public void prepare()  {
		silentMode = false;
		// setup chromedriver
		String osname = OSCheck.getName();
		String pathDriver = "src/test/resources/" + osname + "/";
		if (silentMode) {
			pathDriver += "phantomjs";
		} else {
			pathDriver += "chromedriver";
		}
		if (osname.equals("windows")) {
			pathDriver += ".exe";
		}

		if (silentMode) {
			System.setProperty("phantomjs.binary.path", pathDriver);
			setDriver(new PhantomJSDriver());
		} else {
			System.setProperty("webdriver.chrome.driver", pathDriver);
			setDriver(new ChromeDriver());
		}

		// Create a new instance of the Chrome driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.

		// maximize window
		driver.manage().window().maximize();

		// And now use this to visit myBlog
		// Alternatively the same thing can be done like this
		// driver.navigate().to(testUrl);
		selenium = new SeleniumUtil(driver);
	}

	public void openURL(String url) {
		driver.get(url);
	}

	@cucumber.api.java.After
	public void quitDriver(Scenario scenario){
		if(scenario.isFailed()){
			saveScreenshotsForScenario(scenario);
		}
		driver.quit();
	}

	private void saveScreenshotsForScenario(final Scenario scenario) {

		final byte[] screenshot = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.BYTES);
		scenario.embed(screenshot, "image/png");
	}

	protected void sendTextToElementById(String name, String text) {
		driver.findElement(By.id(name)).sendKeys(text);
	}

	protected void sendTextToElementByName(String name, String text) {
		driver.findElement(By.name(name)).sendKeys(text);
	}

	protected WebElement getElementByName(String name) {
		return driver.findElement(By.name(name));
	}

	protected WebElement getElementById(String name) {
		return driver.findElement(By.id(name));
	}

	protected void clickCheckBoxById(String name) {
		getElementById(name).click();
	}
}
