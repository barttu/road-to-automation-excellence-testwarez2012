package com.fp.qa.testwarez.webdriver.cucumber.web.pageobjects;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.fp.qa.testwarez.webdriver.cucumber.web.driver.SharedDriver;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.When;

/*
 * I'm home page, you can search for the points of interest from here
 */
public class HomePage extends LoadableComponent<HomePage> {

	// Waiting for web elements timeout
	private static final int TIMEOUT = 90;

	// Home page properties
	private static final String HOMEPAGE_URL = "http://countdown.tfl.gov.uk/#/";
	private static final String HOMEPAGE_TITLE = "Live Bus Departures | Transport for London";

	// Web elements identifiers
	private static final String SEARCH_BOX_ID = "initialSearchField";
	private static final String SEARCH_BUTTON_ID = "ext-gen35";

	// Web driver instance
	private final SharedDriver driver;

	// Web elements
	@FindBy(how = How.ID, using = SEARCH_BOX_ID)
	@CacheLookup
	private WebElement searchBox;
	@FindBy(how = How.ID, using = SEARCH_BUTTON_ID)
	@CacheLookup
	private WebElement searchButton;

	public HomePage(SharedDriver driver) {
		this.driver = driver;

		ElementLocatorFactory finder = new AjaxElementLocatorFactory(
				this.driver, TIMEOUT);
		PageFactory.initElements(finder, this);
	}

	@Override
	protected void isLoaded() throws Error {
		String title = driver.getTitle();
		assertEquals(
				"Improper page title, you're probably not on the page you want to test",
				title, HOMEPAGE_TITLE);

		String url = driver.getCurrentUrl();
		assertEquals(
				"Improper page url, you're probably not on the page you want to test",
				url, HOMEPAGE_URL);
	}

	@Override
	@Given("^I navigated to the home page$")
	public void load() {
		driver.get(HOMEPAGE_URL);
		driver.captureScreenshot();
	}

	private void setSearchTerm(String term) {
		searchBox.clear();
		searchBox.sendKeys(term);
	}

	private void invokeSearch() {
		searchButton.click();
	}

	/*
	 * Find locations matching your search term
	 */
	@When("^I search for (.*) from the home page$")
	public ResultsPage search(String term) {
		setSearchTerm(term);
		invokeSearch();

		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.get();

		return resultsPage;
	}
}
