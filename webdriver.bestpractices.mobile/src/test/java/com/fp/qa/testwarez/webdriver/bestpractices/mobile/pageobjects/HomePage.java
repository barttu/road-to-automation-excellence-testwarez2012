package com.fp.qa.testwarez.webdriver.bestpractices.mobile.pageobjects;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.fp.qa.testwarez.webdriver.bestpractices.mobile.common.AjaxWait;

/*
 * I'm home page, you can search for the points of interest from here
 */
public class HomePage extends LoadableComponent<HomePage> {

	// Waiting for web elements timeout
	private static final int TIMEOUT = 90;

	// Home page properties
	private static final String HOMEPAGE_URL = "http://m.countdown.tfl.gov.uk/";
	private static final String HOMEPAGE_TITLE = "Live Bus Departures | Transport for London";

	// Web elements identifiers
	private static final String SEARCH_BOX_NAME = "searchTerm";

	// Web driver instance
	private final WebDriver driver;

	public HomePage(WebDriver driver) {
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
	protected void load() {
		driver.get(HOMEPAGE_URL);
	}

	private void invokeSearch(String term) {
		AjaxWait aw = new AjaxWait();
		WebElement searchBox = aw.wait(driver, By.name(SEARCH_BOX_NAME),
				aw.new WaitCondition() {
					public boolean occurs(WebElement element) {
						return element.isDisplayed() && element.isEnabled();
					}
				});

		searchBox.clear();
		searchBox.sendKeys(term);
		searchBox.submit();
	}

	/*
	 * Find locations matching your search term
	 */
	public ResultsPage search(String term) {
		invokeSearch(term);

		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.get();

		return resultsPage;
	}
}