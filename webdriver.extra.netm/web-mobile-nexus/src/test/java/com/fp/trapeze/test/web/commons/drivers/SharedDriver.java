package com.fp.trapeze.test.web.commons.drivers;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.fp.trapeze.test.web.commons.settings.EnvironmentReader;
import com.fp.trapeze.test.web.commons.settings.EnvironmentSettings;

import cucumber.annotation.After;
import cucumber.runtime.ScenarioResult;

public class SharedDriver extends EventFiringWebDriver {
	private static final String IMAGE_PNG = "image/png";

	private EnvironmentSettings settings;
	private List<byte[]> screenshots = new ArrayList<byte[]>();

	public SharedDriver(DriverBuilder builder,
			CapabilitiesBuilder capabilities, EnvironmentReader reader)
			throws FileNotFoundException, IOException {
		super(builder.build(capabilities.build(reader.read()), reader.read()
				.getHub(), true));

		settings = reader.read();
	}

	public void captureScreenshot() {
		byte[] screenshot = this.getScreenshotAs(OutputType.BYTES);
		screenshots.add(screenshot);
	}

	public void load() {
		this.navigate().to(settings.getHost());
	}

	@After
	public void closeDriver(ScenarioResult result) {
		if (result.isFailed()) {
			captureScreenshot();
		}

		if (!screenshots.isEmpty()) {
			for (byte[] screenshot : screenshots) {
				result.embed(new ByteArrayInputStream(screenshot), IMAGE_PNG);
			}
		}

		super.manage().deleteAllCookies();
		super.quit();
	}
}
