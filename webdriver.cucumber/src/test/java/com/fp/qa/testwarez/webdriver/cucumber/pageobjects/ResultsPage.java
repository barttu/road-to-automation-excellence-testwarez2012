package com.fp.qa.testwarez.webdriver.cucumber.pageobjects;

import java.util.List;

import com.fp.qa.testwarez.webdriver.cucumber.scenarios.ResultsPageActions.Place;
import com.fp.qa.testwarez.webdriver.cucumber.scenarios.ResultsPageActions.Stop;

/*
 * I'm an interface of results page
 */
public interface ResultsPage extends Page {

	/*
	 * Get matched places
	 */
	public List<Place> getPlaces(int limit);

	/*
	 * Get nearby stops
	 */
	public List<Stop> getStops(int limit);

	/*
	 * Selects the place from results list
	 */
	public void selectPlace(Place tobeSelected);
}
