package com.fp.qa.testwarez.webdriver.cucumber.web.pageobjects;

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

import com.fp.qa.testwarez.webdriver.cucumber.web.common.AjaxWait;
import com.fp.qa.testwarez.webdriver.cucumber.web.driver.SharedDriver;

import cucumber.annotation.en.Then;

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
	private final SharedDriver driver;

	public ResultsPage(SharedDriver driver) {
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
	 * Compare matched places
	 */
	@Then("^the following list of matched places is returned$")
	public void comparePlaces(List<Place> expectedPlaces) {
		List<Place> matchedPlaces = getPlaces();
		driver.captureScreenshot();
		assertEquals(matchedPlaces, expectedPlaces);
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

	/*
	 * I'm a search result
	 */
	public class Place {

		// Name of place
		public String name;
		// Post code of place
		public String postcode;

		public Place() {
		}

		public Place(String rawPlace) {
			this.name = retrieveName(rawPlace);
			this.postcode = retrievePostcode(rawPlace);
		}

		/*
		 * Retrieves place name from raw place information
		 */
		private String retrieveName(String rawPlace) {
			int beginingOfPostcode = rawPlace.lastIndexOf("(");

			if (beginingOfPostcode == -1) {
				// No stop code
				return rawPlace;
			}

			int beginning = 0;
			int size = beginingOfPostcode - 1;

			return rawPlace.substring(beginning, beginning + size);
		}

		/*
		 * Retrieves place stop code from raw place information
		 */
		private String retrievePostcode(String rawPlace) {
			int beginingOfPostcode = rawPlace.lastIndexOf("(");

			if (beginingOfPostcode == -1) {
				// No stop code
				return "";
			}

			int beginning = beginingOfPostcode + 1;
			int size = rawPlace.length() - beginning - 1;

			return rawPlace.substring(beginning, beginning + size);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result
					+ ((postcode == null) ? 0 : postcode.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Place other = (Place) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (postcode == null) {
				if (other.postcode != null)
					return false;
			} else if (!postcode.equals(other.postcode))
				return false;
			return true;
		}
	}
}
