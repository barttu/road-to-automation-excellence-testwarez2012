package com.fp.qa.testwarez.webdriver.environment.web.pageobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.fp.qa.testwarez.webdriver.environment.web.common.AjaxWait;
import com.fp.qa.testwarez.webdriver.environment.web.pageobjects.common.Place;

/*
 * I'm a results page, you can find results matching your search term here
 */
public class ResultsPage extends LoadableComponent<ResultsPage> {

	// Waiting for web elements timeout
	private static final int TIMEOUT = 90;

	// Results page properties
	private static final String RESULTPAGE_PARTIAL_URL = "http://countdown.tfl.gov.uk/#|searchTerm=";
	private static final String RESULTPAGE_TITLE = "Live Bus Departures | Transport for London";

	// Web elements identifiers
	private static final String PLACE_NAME_CLASS = "place-name";

	// Web driver instance
	private final WebDriver driver;

	public ResultsPage(WebDriver driver) {
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
				title, RESULTPAGE_TITLE);

		String url = driver.getCurrentUrl();
		assertTrue(
				"Improper page url, you're probably not on the page you want to test",
				url.contains(RESULTPAGE_PARTIAL_URL));
	}

	@Override
	protected void load() {
		driver.get(RESULTPAGE_PARTIAL_URL);
	}

	/*
	 * Get matched places
	 */
	public List<Place> getPlaces() {
		List<Place> matchedPlaces = new ArrayList<Place>();

		new AjaxWait().waitDisplayed(driver, By.className(PLACE_NAME_CLASS));

		List<WebElement> places = driver.findElements(By
				.className(PLACE_NAME_CLASS));
		for (WebElement place : places) {
			matchedPlaces.add(new Place(place.getText()));
		}

		return matchedPlaces;
	}
}
