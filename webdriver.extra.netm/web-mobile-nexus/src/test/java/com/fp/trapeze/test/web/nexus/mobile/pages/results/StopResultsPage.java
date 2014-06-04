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

public class StopResultsPage extends ResultsPage {

	private static final String STOP_LIST_ID = "stopList";
	private static final String STOP_RESULTS_LABEL_ID = "title";

	private static final String HTML_TD_TAG = "td";
	private static final String HTML_A_TAG = "a";

	private static final String HTML_ID_PARAMETER = "id";

	private static final String STOP_RESULTS_LABEL_PATTERN = "Stops near '%s'";
	private static final String STOP_POINT_ID_PATTERN = "stopPoint-";

	private static final int RESULT_TITLE = 1;
	private static final int FIRST_STOP = 0;

	public class StopResult {
		public String name;
		public String id;

		public StopResult() {

		}

		public StopResult(String name, String id) {
			this.name = name;
			this.id = id;
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

			final StopResult other = (StopResult) obj;

			if (this.name.equals(other.name) && this.id.equals(other.id)) {
				return true;
			}

			return false;
		}

		@Override
		public String toString() {
			return String.format("%s (%s)", this.name, this.id);
		}
	}

	@FindBy(id = STOP_RESULTS_LABEL_ID)
	private WebElement resultsLabel;

	@FindBy(id = STOP_LIST_ID)
	private WebElement stopList;

	public StopResultsPage(SharedDriver driver) {
		super(driver);

		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 120);
		PageFactory.initElements(finder, this);
	}

	@Given("^the results label informed the service matched stops for (.*)$")
	@Then("^the results label informs the service matched stops for (.*)$")
	public void checkResultsLabel(String label) {
		assertThat(String.format(STOP_RESULTS_LABEL_PATTERN, label)).isEqualTo(
				resultsLabel.getText());
	}

	@Given("^the following list of matched stops appeared$")
	@Then("^the following list of matched stops appears$")
	public void checkStops(List<StopResult> expected) {
		List<StopResult> current = getStopResults(expected.size());

		assertThat(expected).isEqualTo(current);
	}

	@Given("^I selected the following stop from results$")
	@When("^I select the following stop from results$")
	public void selectStop(List<StopResult> stop) {
		List<StopResult> stops = getStopResults();
		selectResult(stops.indexOf(stop.get(FIRST_STOP)));
	}

	private List<StopResult> getStopResults(int size) {
		List<WebElement> elements = retrieveResultsElements(true, stopList);
		return getStopResults(elements, size);
	}

	private List<StopResult> getStopResults() {
		List<WebElement> elements = retrieveResultsElements(true, stopList);
		return getStopResults(elements, elements.size());
	}

	private List<StopResult> getStopResults(List<WebElement> elements, int size) {
		List<StopResult> current = new ArrayList<StopResult>();

		for (int i = 0; i < size; i++) {
			WebElement record = elements.get(i);
			String id = record.getAttribute(HTML_ID_PARAMETER);
			id = id.substring(STOP_POINT_ID_PATTERN.length(), id.length());

			current.add(new StopResult((record
					.findElements(By.tagName(HTML_TD_TAG)).get(RESULT_TITLE)
					.findElement(By.tagName(HTML_A_TAG)).getText()), id));
		}

		return current;
	}
}
