package com.fp.qa.testwarez.webdriver.cucumber.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.opera.core.systems.OperaDriver;

/*
 * I build desired web driver based on environment settings
 */
public class DriverBuilder {

	// Recognized web browser names
	private static final String OPERA_BROWSER_NAME = "opera";
	private static final String INTERNET_EXPLORER_BROWSER_NAME = "internet explorer";
	private static final String FIREFOX_BROWSER_NAME = "firefox";
	private static final String CHROME_BROWSER_NAME = "chrome";

	/*
	 * I'm a remote web driver who can take screenshots
	 */
	private class RemoteCapturingWebDriver extends RemoteWebDriver implements
			TakesScreenshot {

		public RemoteCapturingWebDriver(URL hub,
				DesiredCapabilities capabilities) {
			super(hub, capabilities);
		}

		/*
		 * Captures screenshot
		 */
		public <X> X getScreenshotAs(OutputType<X> target)
				throws WebDriverException {
			if ((Boolean) getCapabilities().getCapability(
					CapabilityType.TAKES_SCREENSHOT)) {
				String base64Str = execute(DriverCommand.SCREENSHOT).getValue()
						.toString();

				return target.convertFromBase64Png(base64Str);
			}

			return null;
		}
	}

	/*
	 * I build local web driver instances
	 */
	private static abstract class LocalDriverBuilder {
		abstract WebDriver build();
	}

	/*
	 * I build local chrome web driver instance
	 */
	private static class ChromeLocalDriverBuilder extends LocalDriverBuilder {

		@Override
		WebDriver build() {
			return new ChromeDriver();
		}

	}

	/*
	 * I build local firefox web driver instance
	 */
	private static class FirefoxLocalDriverBuilder extends LocalDriverBuilder {

		@Override
		WebDriver build() {
			return new FirefoxDriver();
		}

	}

	/*
	 * I build local internet explorer web driver instance
	 */
	private static class InternetExplorerLocalDriverBuilder extends
			LocalDriverBuilder {

		@Override
		WebDriver build() {
			return new InternetExplorerDriver();
		}

	}

	/*
	 * I build local opera web driver instance
	 */
	private static class OperaLocalDriverBuilder extends LocalDriverBuilder {

		@Override
		WebDriver build() {
			return new OperaDriver();
		}

	}

	// Available local instances of web driver
	private static HashMap<String, LocalDriverBuilder> locals = new HashMap<String, LocalDriverBuilder>();

	static {
		// Initialize local instances of web driver
		locals.put(CHROME_BROWSER_NAME, new ChromeLocalDriverBuilder());
		locals.put(OPERA_BROWSER_NAME, new OperaLocalDriverBuilder());
		locals.put(FIREFOX_BROWSER_NAME, new FirefoxLocalDriverBuilder());
		locals.put(INTERNET_EXPLORER_BROWSER_NAME,
				new InternetExplorerLocalDriverBuilder());
	}

	/*
	 * Starts browser full screen
	 */
	private void showFullscreen(WebDriver driver) {
		((JavascriptExecutor) driver)
				.executeScript("if (window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};");
	}

	/*
	 * Should I create remote or local web driver?
	 */
	private boolean isRemote(String hub) {
		return !hub.equals("");
	}

	/*
	 * Builds remote instance of web driver
	 */
	private WebDriver buildRemote(DesiredCapabilities capabilities, String hub) {
		try {
			return new RemoteCapturingWebDriver(new URL(hub), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return buildLocal(capabilities);
		}
	}

	/*
	 * Builds local instance of web driver
	 */
	private WebDriver buildLocal(DesiredCapabilities capabilities) {
		return locals.get(capabilities.getBrowserName()).build();
	}

	/*
	 * Builds instance of web driver based on environment settings
	 */
	public WebDriver build(DesiredCapabilities capabilities, String hub,
			boolean startFullscreen) {
		WebDriver driver = null;

		if (!isRemote(hub)) {
			// Build local instance of web driver
			driver = buildLocal(capabilities);
		} else {
			// Build remote instance of web driver
			driver = buildRemote(capabilities, hub);
		}

		if (startFullscreen) {
			// Show full screen
			showFullscreen(driver);
		}

		return driver;
	}
}
