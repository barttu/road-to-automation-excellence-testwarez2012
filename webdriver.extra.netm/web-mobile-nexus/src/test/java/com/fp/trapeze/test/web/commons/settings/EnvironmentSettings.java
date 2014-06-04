package com.fp.trapeze.test.web.commons.settings;

import java.net.URL;

public class EnvironmentSettings {
	private String browser;
	private String platform;
	private String version;
	private String host;
	private URL hub;

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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public URL getHub() {
		return hub;
	}

	public void setHub(URL hub) {
		this.hub = hub;
	}
}
