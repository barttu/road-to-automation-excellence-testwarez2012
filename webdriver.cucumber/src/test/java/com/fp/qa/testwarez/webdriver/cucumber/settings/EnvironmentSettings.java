package com.fp.qa.testwarez.webdriver.cucumber.settings;

/*
 * I store test environment settings
 */
public class EnvironmentSettings {

	// Browser name
	private String browser;
	// Platform name
	private String platform;
	// Version of the browser
	private String version;
	// Grid url
	private String hub;
	// Service url
	private String host;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}
}
