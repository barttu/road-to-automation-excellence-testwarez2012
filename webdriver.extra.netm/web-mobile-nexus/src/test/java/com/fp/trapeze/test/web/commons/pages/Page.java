package com.fp.trapeze.test.web.commons.pages;

import org.openqa.selenium.WebDriver;

import com.fp.trapeze.test.web.commons.drivers.SharedDriver;

public class Page {

	protected WebDriver driver;

	public Page(SharedDriver driver) {
		this.driver = driver;
	}

	protected void goHome() {
		((SharedDriver) driver).load();
	}
	
	protected String url() {
		return driver.getCurrentUrl();
	}
}
