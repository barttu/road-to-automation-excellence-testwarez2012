package com.fp.qa.testwarez.webdriver.cucumber.mobile.pageobjects;

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

import com.fp.qa.testwarez.webdriver.cucumber.mobile.common.AjaxWait;
import com.fp.qa.testwarez.webdriver.cucumber.mobile.driver.SharedDriver;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

/*
 * I'm a results page, you can find results matching your search term here
 */
public class ResultsPage extends LoadableComponent<ResultsPage> {

	// Waiting for web elements timeout
	private static final int TIMEOUT = 90;

	// Results page properties
	private static final String RESULTPAGE_PARTIAL_URL = "http://m.countdown.tfl.gov.uk/";
	private static final String RESULTPAGE_TITLE = "Live Bus Departures | Transport for London";

	// Web elements identifiers
	private static final String PLACE_CLASS = "place";
	private static final String STOP_CLASS = "information";
	private static final String STOP_POINT_ROUTES_ID = "stopPoint-%s-routes";

	// Tags
	private static final String ANCHOR_TAG = "a";
	private static final String SPAN_TAG = "span";
	private static final String HREF_ATTR = "href";

	// Counters
	private static final int FIRST_ELEMENT = 0;
	private static final int STOP_CODE_LENGTH = 5;

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
	@Given("^the following list of matched places has been returned$")
	public void comparePlaces(List<Place> expectedPlaces) {
		List<Place> matchedPlaces = getPlaces(expectedPlaces.size());
		driver.captureScreenshot();
		assertEquals(matchedPlaces, expectedPlaces);
	}

	/*
	 * Compare nearby stops
	 */
	@Then("^the following list of nearby stops is returned$")
	public void compareStops(List<Stop> expectedStops) {
		List<Stop> matchedStops = getStops(expectedStops.size());
		driver.captureScreenshot();
		assertEquals(matchedStops, expectedStops);
	}

	/*
	 * Selects place of your choice
	 */
	@When("^I select the following place from the list$")
	public void selectPlace(List<Place> tobeSelected) {
		selectPlace(tobeSelected.get(FIRST_ELEMENT));
		driver.captureScreenshot();
	}

	public void selectPlace(Place tobeSelected) {
		new AjaxWait().waitDisplayed(driver, By.className(PLACE_CLASS));

		List<WebElement> places = driver
				.findElements(By.className(PLACE_CLASS));

		for (WebElement place : places) {
			Place currentPlace = new Place(place);
			if (currentPlace.equals(tobeSelected)) {
				currentPlace.select();
				break;
			}
		}
	}

	/*
	 * Get nearby stops
	 */
	public List<Stop> getStops(int limit) {
		List<Stop> nearbyStops = new ArrayList<Stop>();

		new AjaxWait().waitDisplayed(driver, By.className(STOP_CLASS));

		List<WebElement> stops = driver.findElements(By.className(STOP_CLASS));
		for (int i = 0; i < limit; i++) {
			nearbyStops.add(new Stop(stops.get(i)));
		}

		return nearbyStops;
	}

	/*
	 * Get matched places
	 */
	public List<Place> getPlaces(int limit) {
		List<Place> matchedPlaces = new ArrayList<Place>();

		new AjaxWait().waitDisplayed(driver, By.className(PLACE_CLASS));

		List<WebElement> places = driver
				.findElements(By.className(PLACE_CLASS));
		for (int i = 0; i < limit; i++) {
			matchedPlaces.add(new Place(places.get(i)));
		}

		return matchedPlaces;
	}

	/*
	 * I'm a search result - nearby stop
	 */
	public class Stop {

		// Name
		public String stopname;
		// Code
		public String stopcode;
		// Direction
		public String towards;
		// Route numbers, comma separated
		public String routes;

		public Stop() {
		}

		public Stop(WebElement stop) {
			stopname = retrieveStopname(stop);
			stopcode = retrieveStopcode(stop);
			towards = retrieveStoptowards(stop);
			routes = retrieveStoproutes(stop);
		}

		private String retrieveStopcode(WebElement stop) {
			String stopcode = "";
			try {
				String href = stop.findElement(By.tagName(ANCHOR_TAG))
						.getAttribute(HREF_ATTR);
				stopcode = href.substring(href.length() - STOP_CODE_LENGTH,
						href.length());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return stopcode;
		}

		private String retrieveStoptowards(WebElement stop) {
			String towards = "";
			try {
				towards = stop.findElements(By.tagName(SPAN_TAG))
						.get(FIRST_ELEMENT).getText();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return towards.trim();
		}

		private String retrieveStoproutes(WebElement stop) {
			String stoproutes = "";
			try {
				stoproutes = stop.findElement(
						By.id(String.format(STOP_POINT_ROUTES_ID, stopcode)))
						.getText();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return stoproutes;
		}

		private String retrieveStopname(WebElement stop) {
			String stopname = "";
			try {
				stopname = stop.findElement(By.tagName(ANCHOR_TAG)).getText();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return stopname;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((routes == null) ? 0 : routes.hashCode());
			result = prime * result
					+ ((stopcode == null) ? 0 : stopcode.hashCode());
			result = prime * result
					+ ((stopname == null) ? 0 : stopname.hashCode());
			result = prime * result
					+ ((towards == null) ? 0 : towards.hashCode());
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
			Stop other = (Stop) obj;
			if (routes == null) {
				if (other.routes != null)
					return false;
			} else if (!routes.equals(other.routes))
				return false;
			if (stopcode == null) {
				if (other.stopcode != null)
					return false;
			} else if (!stopcode.equals(other.stopcode))
				return false;
			if (stopname == null) {
				if (other.stopname != null)
					return false;
			} else if (!stopname.equals(other.stopname))
				return false;
			if (towards == null) {
				if (other.towards != null)
					return false;
			} else if (!towards.equals(other.towards))
				return false;
			return true;
		}
	}

	/*
	 * I'm a search result - place & location
	 */
	public class Place {

		// Name of place
		public String name;
		// Post code of place
		public String postcode;

		// Click the stop
		private WebElement anchor;

		public Place() {
		}

		public Place(WebElement place) {
			anchor = place.findElement(By.tagName(ANCHOR_TAG));
			String rawPlace = anchor.getText();
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

		/*
		 * Selects the stop
		 */
		public void select() {
			anchor.click();
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