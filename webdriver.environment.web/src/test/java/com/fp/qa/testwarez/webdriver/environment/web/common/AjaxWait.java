package com.fp.qa.testwarez.webdriver.environment.web.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * I'm waiting for a proper condition of an ajax web element
 */
public class AjaxWait {

	// Timeout
	private static final int WAIT_TIMEOUT = 30;

	/*
	 * I'm the condition
	 */
	public abstract class WaitCondition {
		public abstract boolean occurs(WebElement element);
	}

	/*
	 * Waits for specified condition of an ajax element
	 */
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

	/*
	 * Waits for specified condition of a parent's ajax element
	 */
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

	/*
	 * Waits for an ajax element to be displayed
	 */
	public WebElement waitDisplayed(WebDriver driver, final By by) {
		return wait(driver, by, new WaitCondition() {
			public boolean occurs(WebElement element) {
				return element.isDisplayed();
			}
		});
	}

	/*
	 * Waits for a parent's ajax element to be displayed
	 */
	public WebElement waitDisplayed(WebDriver driver, final By by,
			WebElement parent) {
		return wait(driver, by, parent, new WaitCondition() {
			public boolean occurs(WebElement element) {
				return element.isDisplayed();
			}
		});
	}
}
