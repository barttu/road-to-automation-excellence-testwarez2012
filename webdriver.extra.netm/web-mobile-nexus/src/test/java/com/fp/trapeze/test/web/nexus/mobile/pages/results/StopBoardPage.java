package com.fp.trapeze.test.web.nexus.mobile.pages.results;

import static org.fest.assertions.Assertions.assertThat;

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

public class StopBoardPage extends ResultsPage {

	private static final String STOP_CODE_LABEL_ID = "stopCodeAndTimeHeader";
	private static final String STOP_NAME_LABEL_CLASS = "stopInfo";
	private static final String SEARCH_BUTTON_ID = "searchButton";
	private static final String SEARCH_BOX_ID = "searchTerm";

	private static final String HTML_LI_TAG = "li";

	private static final int FAVOURITES_BUTTON_POSITION = 2;
	private static final int FIRST_STOP = 0;

	public class Stop {
		public String name;
		public String id;
		public String code;

		public Stop() {

		}

		public Stop(String name, String id, String code) {
			this.name = name;
			this.id = id;
			this.code = code;
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

			if (this.name.equals(other.name) && this.id.equals(other.id)
					&& this.code.equals(other.code)) {
				return true;
			}

			return false;
		}

		@Override
		public String toString() {
			return String.format("%s (%s) - %s", this.name, this.id, this.code);
		}
	}

	@FindBy(className = STOP_NAME_LABEL_CLASS)
	private WebElement stopNameLabel;

	@FindBy(id = STOP_CODE_LABEL_ID)
	private WebElement stopCodeLabel;

	@FindBy(id = SEARCH_BOX_ID)
	private WebElement searchBox;

	@FindBy(id = SEARCH_BUTTON_ID)
	private WebElement searchButton;

	@FindBy(className = "buttonList")
	private WebElement buttonList;

	public StopBoardPage(SharedDriver driver) {
		super(driver);

		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 120);
		PageFactory.initElements(finder, this);
	}

	@Then("^the stop board label informs the service matched stop$")
	public void checkStopDetails(List<Stop> stop) {
		Stop first = stop.get(FIRST_STOP);

		assertThat(first.name).isEqualTo(stopNameLabel.getText());
		assertThat(stopCodeLabel.getText().endsWith(first.code));
		assertThat(url().endsWith(first.id));
	}

	@Given("^I searched for (.*) from the stop board page$")
	@When("^I search for (.*) from the stop board page$")
	public void search(String term) {
		setTerm(term);
		search();
	}

	@Given("^I added the stop to favourites$")
	@When("^I add the stop to favourites$")
	public void addFavourites() {
		buttonList.findElements(By.tagName(HTML_LI_TAG))
				.get(FAVOURITES_BUTTON_POSITION).click();
	}

	public void search() {
		searchButton.click();
	}

	public void setTerm(String term) {
		searchBox.sendKeys(term);
	}
}
