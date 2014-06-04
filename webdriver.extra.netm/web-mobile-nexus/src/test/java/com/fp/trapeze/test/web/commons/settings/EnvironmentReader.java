package com.fp.trapeze.test.web.commons.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class EnvironmentReader {
	private static final String PROPERTIES_FILE_PATH = "target/classes/env.properties";

	private static final String BROWSER = "env.browser.name";
	private static final String VERSION = "env.browser.version";
	private static final String PLATFORM = "env.browser.platform";
	private static final String HUB = "env.selenium.hub";
	private static final String HOST = "env.test.host";

	public EnvironmentSettings read() throws FileNotFoundException, IOException {
		Properties env = create();

		EnvironmentSettings settings = new EnvironmentSettings();
		settings.setBrowser(env.getProperty(BROWSER));
		settings.setVersion(env.getProperty(VERSION));
		settings.setPlatform(env.getProperty(PLATFORM));
		settings.setHost(env.getProperty(HOST));

		try {
			settings.setHub(new URL(env.getProperty(HUB)));
		} catch (MalformedURLException ex) {
			settings.setHub(null);
		}

		System.out.println("---------------------------------------");
		System.out.println("settings");
		System.out.println("---------------------------------------");
		System.out.println("browser name: " + settings.getBrowser());
		System.out.println("browser version: " + settings.getVersion());
		System.out.println("browser platform: " + settings.getPlatform());
		System.out.println("selenium hub: " + settings.getHub());
		System.out.println("test host: " + settings.getHost());
		System.out.println("---------------------------------------");

		return settings;
	}

	private Properties create() throws FileNotFoundException, IOException {
		Properties env = new Properties();
		env.load(new FileInputStream(PROPERTIES_FILE_PATH));

		return env;
	}
}
