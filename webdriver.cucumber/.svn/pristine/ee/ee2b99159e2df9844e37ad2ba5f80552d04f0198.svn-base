package com.fp.qa.testwarez.webdriver.cucumber.scenarios;

import com.fp.qa.testwarez.webdriver.cucumber.driver.SharedDriver;
import com.fp.qa.testwarez.webdriver.cucumber.pageobjects.provider.PagesProvider;

/*
 * I'm abstract page action
 */
public abstract class PageActions {

	// Web driver instance
	private SharedDriver driver;

	// Page objects provider
	private PagesProvider provider;

	public PageActions(SharedDriver driver, PagesProvider provider) {
		this.driver = driver;
		this.provider = provider;
	}
	
	public SharedDriver getDriver() {
		return driver;
	}

	public void setDriver(SharedDriver driver) {
		this.driver = driver;
	}
	
	public PagesProvider getProvider() {
		return provider;
	}

	public void setProvider(PagesProvider provider) {
		this.provider = provider;
	}
}
