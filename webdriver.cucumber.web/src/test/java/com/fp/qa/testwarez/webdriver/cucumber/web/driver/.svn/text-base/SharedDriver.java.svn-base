package com.fp.qa.testwarez.webdriver.cucumber.web.driver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.fp.qa.testwarez.webdriver.cucumber.web.settings.EnvironmentReader;

import cucumber.annotation.After;
import cucumber.annotation.Before;
import cucumber.runtime.ScenarioResult;

/*
 * I'm a web driver injected into page objects
 */
public class SharedDriver extends EventFiringWebDriver {

	// Screenshots extension
	private static final String IMAGE_PNG = "image/png";

	// Web driver instance
	private static final WebDriver REAL_DRIVER = new DriverBuilder().build(
			new CapabilitiesBuilder().buildCapabilities(new EnvironmentReader()
					.readSettings()), new EnvironmentReader().readSettings()
					.getHub(), true);

	// Thread closing web driver
	private static final Thread CLOSE_THREAD = new Thread() {
		@Override
		public void run() {
			REAL_DRIVER.quit();
		}
	};

	// Taken screenshots
	private List<byte[]> takenScreenshots = new ArrayList<byte[]>();

	static {
		// Close web driver at testing shutdown
		Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
	}

	public SharedDriver() {
		super(REAL_DRIVER);
	}

	/*
	 * Takes screenshot
	 */
	public void captureScreenshot() {
		takenScreenshots.add(this.getScreenshotAs(OutputType.BYTES));
	}

	/*
	 * Closes web driver instance if possible
	 */
	@Override
	public void quit() {
		if (Thread.currentThread() != CLOSE_THREAD) {
			throw new UnsupportedOperationException(
					"You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
		}

		// Close only if end of testing
		super.quit();
	}

	@Before
	public void deleteAllCookies() {
		manage().deleteAllCookies();
	}

	@After
	public void closeDriver(ScenarioResult scenario) {
		if (scenario.isFailed()) {
			captureScreenshot();
		}

		if (!takenScreenshots.isEmpty()) {
			for (byte[] screenshot : takenScreenshots) {
				try {
					scenario.embed(screenshot, IMAGE_PNG);
				} catch (WebDriverException somePlatformsDontSupportScreenshots) {
					System.err.println(somePlatformsDontSupportScreenshots
							.getMessage());
				}
			}
		}
	}
}
