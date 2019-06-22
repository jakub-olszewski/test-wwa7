package eu.b24u.cucumber.page.facebook.login;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import eu.b24u.cucumber.page.BasePage;

/**
 * Strona logowania 
 * 
 * @author github/jakub-olszewski
 *
 */
public class FacebookPageImpl extends BasePage implements FacebookPage{
	
	String BASE_URL = "https://pl-pl.facebook.com/";

	@FindBy(id = "email")
	private WebElement usernameField;
	
	@FindBy(id = "pass")
	private WebElement passwordField;
	
	@FindBy(id = "loginbutton")
	private WebElement loginButton;

	public FacebookPageImpl() {
		PageFactory.initElements(driver, this);
	}
	
	public void setUsername(String username){
		usernameField.clear();
		usernameField.sendKeys(username);
	}

	@Override
	public void setPassword(String password) {
		passwordField.clear();
		passwordField.sendKeys(password);
	}

	@Override
	public void clickLoginButton() {
		loginButton.click();
		selenium.wait(1);
	}

	@Override
	public void navigateTo() {
		getDriver().navigate().to(BASE_URL);
		selenium.wait(1);
		Assert.assertEquals("Facebook – zaloguj się lub zarejestruj", driver.getTitle());
	}

	@Override
	public String getTitle() {
		return driver.getTitle();
	}

}
