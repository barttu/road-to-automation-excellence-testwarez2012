package com.fp.qa.testwarez.webdriver.cucumber.pageobjects.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.fp.qa.testwarez.webdriver.cucumber.common.AjaxWait;
import com.fp.qa.testwarez.webdriver.cucumber.driver.SharedDriver;
import com.fp.qa.testwarez.webdriver.cucumber.pageobjects.ResultsPage;
import com.fp.qa.testwarez.webdriver.cucumber.scenarios.ResultsPageActions;
import com.fp.qa.testwarez.webdriver.cucumber.scenarios.ResultsPageActions.Place;
import com.fp.qa.testwarez.webdriver.cucumber.scenarios.ResultsPageActions.Stop;

/*
 * I'm a results page, you can find results matching your search term here
 */
public class WebResultsPage extends LoadableComponent<WebResultsPage> implements
		ResultsPage {

	// Waiting for web elements timeout
	private static final int TIMEOUT = 90;

	// Results page properties
	private static final String RESULTPAGE_PARTIAL_URL = "http://countdown.tfl.gov.uk/#|searchTerm=";
	private static final String RESULTPAGE_TITLE = "Live Bus Departures | Transport for London";

	// Web elements identifiers
	private static final String PLACE_NAME_CLASS = "place-name";

	// Web driver instance
	private SharedDriver driver;

	public WebResultsPage() {
	}

	public WebResultsPage(SharedDriver driver) {
		setDriver(driver);
	}

	public void setDriver(SharedDriver driver) {
		this.driver = driver;

		ElementLocatorFactory finder = new AjaxElementLocatorFactory(
				this.driver, TIMEOUT);
		PageFactory.initElements(finder, this);
	}

	@Override
	public void isLoaded() throws Error {
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
	public void load() {
		driver.get(RESULTPAGE_PARTIAL_URL);
	}

	/*
	 * Get matched places
	 */
	public List<Place> getPlaces(int limit) {
		List<Place> matchedPlaces = new ArrayList<Place>();

		new AjaxWait().waitDisplayed(driver, By.className(PLACE_NAME_CLASS));

		List<WebElement> places = driver.findElements(By
				.className(PLACE_NAME_CLASS));
		for (int i = 0; i < limit; i++) {
			matchedPlaces.add(new PlaceBuilder().buildPlace(places.get(i)));
		}

		return matchedPlaces;
	}

	/*
	 * Get nearby stops
	 */
	public List<Stop> getStops(int limit) {
		return null; // not implemented for web
	}

	/*
	 * Selects the place from results list
	 */
	public void selectPlace(Place tobeSelected) {
		// not implemented for web
	}

	/*
	 * I'm a search result
	 */
	public class PlaceBuilder {

		public PlaceBuilder() {
		}

		/*
		 * Builds place
		 */
		public Place buildPlace(WebElement webPlace) {
			ResultsPageActions searchScenarios = new ResultsPageActions(null, null);
			Place place = searchScenarios.new Place();
			place.setName(retrieveName(webPlace));
			place.setPostcode(retrievePostcode(webPlace));
			return place;
		}

		/*
		 * Retrieves place name from raw place information
		 */
		private String retrieveName(WebElement webPlace) {
			String rawWeb = webPlace.getText();
			int beginingOfPostcode = rawWeb.lastIndexOf("(");
			if (beginingOfPostcode == -1) {
				// No stop code
				return rawWeb;
			}

			int beginning = 0;
			int size = beginingOfPostcode - 1;

			return rawWeb.substring(beginning, beginning + size);
		}

		/*
		 * Retrieves place stop code from raw place information
		 */
		private String retrievePostcode(WebElement webPlace) {
			String rawWeb = webPlace.getText();
			int beginingOfPostcode = rawWeb.lastIndexOf("(");
			if (beginingOfPostcode == -1) {
				// No stop code
				return "";
			}

			int beginning = beginingOfPostcode + 1;
			int size = rawWeb.length() - beginning - 1;

			return rawWeb.substring(beginning, beginning + size);
		}
	}
}
