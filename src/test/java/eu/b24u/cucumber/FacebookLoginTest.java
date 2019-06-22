package eu.b24u.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/features/login.feature"},
		strict = false, plugin = {"pretty",
		"json:target/cucumber_json_reports/home-page.json",
		"html:target/home-page-html"},
		glue = {"eu.b24u.cucumber.page.facebook.login",
				"eu.b24u.cucumber.init"})
public class FacebookLoginTest {

}
