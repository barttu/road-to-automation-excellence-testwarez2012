package com.fp.trapeze.test.web.nexus.mobile.pages.results;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.fp.trapeze.test.web.commons.drivers.SharedDriver;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class LocationResultsPage extends ResultsPage {

	private static final String PLACE_LIST_ID = "placeList";

	private static final String HTML_H3_TAG = "h3";
	private static final String HTML_TD_TAG = "td";

	private static final String LOCATION_RESULTS_LABEL_PATTERN = "Results for '%s'";

	private static final int RESULT_TITLE = 1;
	private static final int FIRST_LOCATION = 0;

	public class LocationResult {
		public String name;

		public LocationResult() {

		}

		public LocationResult(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}

			final LocationResult other = (LocationResult) obj;

			if (this.name.equals(other.name)) {
				return true;
			}

			return false;
		}

		@Override
		public String toString() {
			return String.format("%s", this.name);
		}
	}

	@FindBy(tagName = HTML_H3_TAG)
	private WebElement resultsLabel;

	@FindBy(id = PLACE_LIST_ID)
	private WebElement placeList;

	public LocationResultsPage(SharedDriver driver) {
		super(driver);

		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 120);
		PageFactory.initElements(finder, this);
	}

	@Given("^the results label informed the service matched locations for (.*)$")
	@Then("^the results label informs the service matched locations for (.*)$")
	public void checkResultsLabel(String label) {
		assertThat(
				String.format(LOCATION_RESULTS_LABEL_PATTERN,
						label.toLowerCase())).isEqualTo(resultsLabel.getText());
	}

	@Given("^the following list of matched locations appeared$")
	@Then("^the following list of matched locations appears$")
	public void checkLocations(List<LocationResult> expected) {
		List<LocationResult> current = getLocationResults(expected.size());

		assertThat(expected).isEqualTo(current);
	}

	@Given("^I selected the following location from results$")
	@When("^I select the following location from results$")
	public void selectLocation(List<LocationResult> location) {
		List<LocationResult> locations = getLocationResults();
		selectResult(locations.indexOf(location.get(FIRST_LOCATION)));
	}

	private List<LocationResult> getLocationResults(int size) {
		List<WebElement> elements = retrieveResultsElements(true, placeList);
		return getLocationResults(elements, size);
	}

	private List<LocationResult> getLocationResults() {
		List<WebElement> elements = retrieveResultsElements(true, placeList);
		return getLocationResults(elements, elements.size());
	}

	private List<LocationResult> getLocationResults(List<WebElement> elements,
			int size) {
		List<LocationResult> current = new ArrayList<LocationResult>();

		for (int i = 0; i < size; i++) {
			current.add(new LocationResult((elements.get(i)
					.findElements(By.tagName(HTML_TD_TAG)).get(RESULT_TITLE)
					.getText())));
		}

		return current;
	}
}
