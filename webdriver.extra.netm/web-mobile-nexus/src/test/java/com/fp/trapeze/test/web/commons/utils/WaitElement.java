package com.fp.trapeze.test.web.commons.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitElement {
	private static final int WAIT_TIMEOUT = 30;

	public abstract class WaitCondition {
		public abstract boolean occurs(WebElement element);
	}

	public WebElement wait(WebDriver driver, final By by,
			final WaitCondition condition) {
		WebElement element = (new WebDriverWait(driver, WAIT_TIMEOUT))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						WebElement e = d.findElement(by);
						if (condition.occurs(e)) {
							return e;
						} else {
							return null;
						}
					}
				});

		return element;
	}

	public WebElement wait(WebDriver driver, final By by,
			final WebElement parent, final WaitCondition condition) {
		WebElement element = (new WebDriverWait(driver, WAIT_TIMEOUT))
				.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						WebElement e = parent.findElement(by);
						if (condition.occurs(e)) {
							return e;
						} else {
							return null;
						}
					}
				});

		return element;
	}

	public WebElement waitDisplayed(WebDriver driver, final By by) {
		return wait(driver, by, new WaitCondition() {
			public boolean occurs(WebElement element) {
				return element.isDisplayed();
			}
		});
	}

	public WebElement waitDisplayed(WebDriver driver, final By by,
			WebElement parent) {
		return wait(driver, by, parent, new WaitCondition() {
			public boolean occurs(WebElement element) {
				return element.isDisplayed();
			}
		});
	}
}
