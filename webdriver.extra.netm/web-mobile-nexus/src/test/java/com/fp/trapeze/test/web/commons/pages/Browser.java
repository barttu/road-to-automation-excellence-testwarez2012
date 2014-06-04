package com.fp.trapeze.test.web.commons.pages;

import org.openqa.selenium.WebDriver;

import com.fp.trapeze.test.web.commons.drivers.SharedDriver;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class Browser {
	private WebDriver driver;

	public Browser(SharedDriver driver) {
		this.driver = driver;
	}

	@Given("^I typed (.*) as url in a browser window$")
	@When("^I type (.*) as url in a browser window$")
	public void typeUrl(String url) {
		driver.navigate().to(url);
	}

	@Then("^the current view adjusted to the screen$")
	public void captureScreen() {
		((SharedDriver) driver).captureScreenshot();
	}
}
