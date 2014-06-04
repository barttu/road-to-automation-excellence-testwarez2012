package com.fp.qa.testwarez.webdriver.cucumber.pageobjects.provider;

import com.fp.qa.testwarez.webdriver.cucumber.driver.SharedDriver;
import com.fp.qa.testwarez.webdriver.cucumber.pageobjects.HomePage;
import com.fp.qa.testwarez.webdriver.cucumber.pageobjects.ResultsPage;
import com.fp.qa.testwarez.webdriver.cucumber.settings.EnvironmentReader;

/*
 * I provide proper implementation of page objects
 */
public class PagesProvider {

	private static final String PACKAGE_NAME = "com.fp.qa.testwarez.webdriver.cucumber.pageobjects";
	private static final String RESULTS_PAGE = "ResultsPage";
	private static final String HOME_PAGE = "HomePage";
	private static final String DOT = ".";

	/*
	 * I represent model of service to be tested
	 */
	private enum Model {
		Mobile, Web
	}

	// Web driver instance
	private SharedDriver driver;
	// Model to be tested
	private Model model;

	public PagesProvider(SharedDriver driver, EnvironmentReader settingsReader) {
		this.model = Model.valueOf(settingsReader.readSettings().getHost());
		this.driver = driver;
	}

	/*
	 * Provides proper home page object
	 */
	public HomePage getHomePage() {
		HomePage homePage = null;
		try {
			String name = PACKAGE_NAME + DOT + model.toString().toLowerCase()
					+ DOT + model + HOME_PAGE;
			homePage = (HomePage) Class.forName(name).newInstance();
			homePage.setDriver(driver);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return homePage;
	}

	/*
	 * Provides proper results page object
	 */
	public ResultsPage getResultsPage() {
		ResultsPage resultsPage = null;
		try {
			String name = PACKAGE_NAME + DOT + model.toString().toLowerCase()
					+ DOT + model + RESULTS_PAGE;
			resultsPage = (ResultsPage) Class.forName(name).newInstance();
			resultsPage.setDriver(driver);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return resultsPage;
	}
}
