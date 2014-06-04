package com.fp.qa.testwarez.webdriver.environment.mobile.driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.fp.qa.testwarez.webdriver.environment.mobile.settings.EnvironmentSettings;

/*
 * I build desired browser capabilities based on environment settings
 */
public class CapabilitiesBuilder {
	
	/*
	 * Builds desired browser capabilities based on environment settings
	 */
	public DesiredCapabilities buildCapabilities(EnvironmentSettings settings) {
		// Create and set desired capabilities
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setJavascriptEnabled(true);
		capabilities.setBrowserName(settings.getBrowser());
		capabilities.setVersion(settings.getVersion());

		try {
			capabilities.setPlatform(Platform.valueOf(settings.getPlatform()));
		} catch (IllegalArgumentException ex) {
			capabilities.setPlatform(Platform.ANY);
		}

		return capabilities;
	}
}
