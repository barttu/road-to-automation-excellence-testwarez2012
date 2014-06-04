package com.fp.qa.testwarez.webdriver.cucumber.pageobjects.mobile;

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
public class MobileResultsPage extends LoadableComponent<MobileResultsPage>
		implements ResultsPage {

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
	private SharedDriver driver;

	public MobileResultsPage() {
	}
	
	public MobileResultsPage(SharedDriver driver) {
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

	public void selectPlace(Place tobeSelected) {
		new AjaxWait().waitDisplayed(driver, By.className(PLACE_CLASS));

		List<WebElement> places = driver
				.findElements(By.className(PLACE_CLASS));

		for (WebElement place : places) {
			Place currentPlace = new PlaceBuilder().buildPlace(place);
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
			nearbyStops.add(new StopBuilder().buildStop(stops.get(i)));
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
			matchedPlaces.add(new PlaceBuilder().buildPlace((places.get(i))));
		}

		return matchedPlaces;
	}

	/*
	 * I'm a search result - nearby stop
	 */
	public class StopBuilder {

		public StopBuilder() {
		}

		public Stop buildStop(WebElement mobileStop) {
			ResultsPageActions searchScenarios = new ResultsPageActions(null, null);
			Stop stop = searchScenarios.new Stop();
			stop.setStopname(retrieveStopname(mobileStop));
			stop.setStopcode(retrieveStopcode(mobileStop));
			stop.setTowards(retrieveStoptowards(mobileStop));
			stop.setRoutes(retrieveStoproutes(mobileStop, stop.getStopcode()));
			return stop;
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

		private String retrieveStoproutes(WebElement stop, String stopCode) {
			String stoproutes = "";
			try {
				stoproutes = stop.findElement(
						By.id(String.format(STOP_POINT_ROUTES_ID, stopCode)))
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
	}

	/*
	 * I'm a search result - place & location
	 */
	public class PlaceBuilder {

		public PlaceBuilder() {
		}

		public Place buildPlace(WebElement mobilePlace) {
			WebElement anchor = mobilePlace.findElement(By.tagName(ANCHOR_TAG));

			ResultsPageActions searchScenarios = new ResultsPageActions(null, null);
			Place place = searchScenarios.new Place();
			place.setAnchor(anchor);
			place.setName(retrieveName(anchor));
			place.setPostcode(retrievePostcode(anchor));
			return place;
		}

		/*
		 * Retrieves place name from raw place information
		 */
		private String retrieveName(WebElement mobilePlace) {
			String rawPlace = mobilePlace.getText();
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
		private String retrievePostcode(WebElement mobilePlace) {
			String rawPlace = mobilePlace.getText();
			int beginingOfPostcode = rawPlace.lastIndexOf("(");
			if (beginingOfPostcode == -1) {
				// No stop code
				return "";
			}

			int beginning = beginingOfPostcode + 1;
			int size = rawPlace.length() - beginning - 1;

			return rawPlace.substring(beginning, beginning + size);
		}
	}
}