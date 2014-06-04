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

public class HomePage extends ResultsPage {
	private static final String FAVOURITE_STOPS_LIST_ID = "myStopsList";
	private static final String RECENT_STOPS_LIST_ID = "recentStopsList";
	private static final String RECENT_NEWS_FORM_ID = "recentNewsForm";
	private static final String SEARCH_BUTTON_ID = "searchButton";
	private static final String SEARCH_BOX_ID = "searchTerm";

	private static final char BACKSLASH = '/';

	private static final String HTML_TD_TAG = "td";
	private static final String HTML_A_TAG = "a";

	private static final int OFFSET = 1;

	private static final String HTML_HREF_PARAMETER = "href";

	public class Stop {
		public String name;
		public String id;

		public Stop() {

		}

		public Stop(String name, String id) {
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

			final Stop other = (Stop) obj;

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

	@FindBy(id = SEARCH_BOX_ID)
	private WebElement searchBox;

	@FindBy(id = SEARCH_BUTTON_ID)
	private WebElement searchButton;

	@FindBy(id = RECENT_NEWS_FORM_ID)
	private WebElement recentNewsForm;

	@FindBy(id = RECENT_STOPS_LIST_ID)
	private WebElement recentStopsList;

	@FindBy(id = FAVOURITE_STOPS_LIST_ID)
	private WebElement favouriteStopsList;

	public HomePage(SharedDriver driver) {
		super(driver);

		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 120);
		PageFactory.initElements(finder, this);
	}

	@Given("^I searched for (.*) from the home page$")
	@When("^I search for (.*) from the home page$")
	public void search(String term) {
		setTerm(term);
		search();
	}

	@Given("^I opened recent news$")
	@When("^I open recent news$")
	public void viewRecentNews() {
		recentNewsForm.click();
	}

	@Given("^I opened home page$")
	@When("^I open home page$")
	public void openHome() {
		goHome();
	}

	@Given("^the following list of recent stops appeared on the home page$")
	@Then("^the following list of recent stops appears on the home page$")
	public void checkRecentStops(List<Stop> expected) {
		List<Stop> current = getStops(expected.size(), recentStopsList);

		assertThat(expected).isEqualTo(current);
	}

	@Given("^the following list of favourite stops appeared on the home page$")
	@Then("^the following list of favourite stops appears on the home page$")
	public void checkFavouriteStops(List<Stop> expected) {
		List<Stop> current = getStops(expected.size(), favouriteStopsList);

		assertThat(expected).isEqualTo(current);
	}

	private List<Stop> getStops(int size, WebElement table) {
		List<WebElement> elements = retrieveResultsElements(false, table);

		List<Stop> current = new ArrayList<Stop>();

		for (int i = 0; i < size; i++) {
			WebElement record = elements.get(i);

			String id = record.findElement(By.tagName(HTML_A_TAG))
					.getAttribute(HTML_HREF_PARAMETER);
			id = id.substring(id.lastIndexOf(BACKSLASH) + OFFSET, id.length());

			String name = record.findElement(By.tagName(HTML_TD_TAG)).getText();

			current.add(new Stop(name, id));
		}

		return current;
	}

	private void search() {
		searchButton.click();
	}

	private void setTerm(String term) {
		searchBox.sendKeys(term);
	}
}
