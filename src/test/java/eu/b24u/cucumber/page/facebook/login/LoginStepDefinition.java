package eu.b24u.cucumber.page.facebook.login;

import org.junit.Assert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.b24u.cucumber.page.facebook.login.FacebookPage;
import eu.b24u.cucumber.page.facebook.login.FacebookPageImpl;

public class LoginStepDefinition  {

	@When("^I write a username \"([^\"]*)\"$")
	public void i_write_a_username(String username) throws Throwable {
		facebookPage.setUsername(username);
	}

	FacebookPage facebookPage;

	public LoginStepDefinition() {
		this.facebookPage = new FacebookPageImpl();
	}

	@Given("^the login page$")
	public void the_login_page() throws Throwable {
		facebookPage.navigateTo();
	}

	@When("^I click a button login$")
	public void i_click_a_button_login() throws Throwable {
		facebookPage.clickLoginButton();
	}
	
	@And("^I write a password \"([^\"]*)\"$")
	public void i_write_a_password(String password) throws Throwable {
	    facebookPage.setPassword(password);
	}
	
	@Then("^I see title page which contain \"([^\"]*)\"$")
	public void i_see_notification_which_contain(String textInNotification) throws Throwable {
		Assert.assertEquals(facebookPage.getTitle(), textInNotification);
	}

}
