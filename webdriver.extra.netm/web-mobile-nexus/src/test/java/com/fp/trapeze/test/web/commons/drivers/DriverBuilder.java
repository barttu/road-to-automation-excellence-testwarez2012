package com.fp.trapeze.test.web.commons.drivers;

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

public class DriverBuilder {
	private static final String OPERA_BROWSER_NAME = "opera";
	private static final String INTERNET_EXPLORER_BROWSER_NAME = "internet explorer";
	private static final String FIREFOX_BROWSER_NAME = "firefox";
	private static final String CHROME_BROWSER_NAME = "chrome";

	private class RemoteCapturingWebDriver extends RemoteWebDriver implements
			TakesScreenshot {

		public RemoteCapturingWebDriver(URL hub,
				DesiredCapabilities capabilities) {
			super(hub, capabilities);
		}

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

	private static abstract class LocalDriverBuilder {
		abstract WebDriver build();
	}

	private static class ChromeLocalDriverBuilder extends LocalDriverBuilder {

		@Override
		WebDriver build() {
			return new ChromeDriver();
		}

	}

	private static class FirefoxLocalDriverBuilder extends LocalDriverBuilder {

		@Override
		WebDriver build() {
			return new FirefoxDriver();
		}

	}

	private static class InternetExplorerLocalDriverBuilder extends
			LocalDriverBuilder {

		@Override
		WebDriver build() {
			return new InternetExplorerDriver();
		}

	}

	private static class OperaLocalDriverBuilder extends LocalDriverBuilder {

		@Override
		WebDriver build() {
			return new OperaDriver();
		}

	}

	private static HashMap<String, LocalDriverBuilder> locals = new HashMap<String, LocalDriverBuilder>();

	static {
		locals.put(CHROME_BROWSER_NAME, new ChromeLocalDriverBuilder());
		locals.put(FIREFOX_BROWSER_NAME, new FirefoxLocalDriverBuilder());
		locals.put(INTERNET_EXPLORER_BROWSER_NAME,
				new InternetExplorerLocalDriverBuilder());
		locals.put(OPERA_BROWSER_NAME, new OperaLocalDriverBuilder());
	}

	public WebDriver build(DesiredCapabilities capabilities, URL hub,
			boolean startFullscreen) {
		WebDriver driver = null;

		if (!isRemote(hub)) {
			driver = buildLocal(capabilities);
		} else {
			driver = buildRemote(capabilities, hub);
		}

		if (startFullscreen) {
			adjustFullscreen(driver);
		}

		return driver;
	}

	private void adjustFullscreen(WebDriver driver) {
		((JavascriptExecutor) driver)
				.executeScript("if (window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};");
	}

	private boolean isRemote(URL hub) {
		return hub != null;
	}

	private WebDriver buildRemote(DesiredCapabilities capabilities, URL hub) {
		WebDriver driver = new RemoteCapturingWebDriver(hub, capabilities);

		return driver;
	}

	private WebDriver buildLocal(DesiredCapabilities capabilities) {
		return locals.get(capabilities.getBrowserName()).build();
	}
}
