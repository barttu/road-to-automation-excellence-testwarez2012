package com.fp.qa.testwarez.webdriver.cucumber.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/*
 * I'm reading environment settings details from configuration file
 */
public class EnvironmentReader {

	// Location of properties file
	private static final String PROPERTIES_FILE_PATH = "target/classes/env.properties";

	// Recognized properties
	private static final String BROWSER = "env.test.browser";
	private static final String VERSION = "env.test.version";
	private static final String PLATFORM = "env.test.platform";
	private static final String HUB = "env.test.hub";
	private static final String HOST = "env.test.host";

	/*
	 * Loads properties from file
	 */
	private Properties loadProperties() throws FileNotFoundException,
			IOException {
		Properties env = new Properties();
		env.load(new FileInputStream(PROPERTIES_FILE_PATH));
		return env;
	}

	/*
	 * Reads environment settings
	 */
	public EnvironmentSettings readSettings() {
		// Read and set up settings
		EnvironmentSettings settings = new EnvironmentSettings();

		try {
			// Load properties from file
			Properties env = loadProperties();
			settings.setBrowser(env.getProperty(BROWSER));
			settings.setVersion(env.getProperty(VERSION));
			settings.setPlatform(env.getProperty(PLATFORM));
			settings.setHub(env.getProperty(HUB));
			settings.setHost(env.getProperty(HOST));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return settings;
	}
}
