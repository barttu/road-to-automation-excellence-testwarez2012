package com.fp.trapeze.test.web.nexus.mobile.pages.results;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.fp.trapeze.test.web.commons.drivers.SharedDriver;
import com.fp.trapeze.test.web.commons.pages.Page;

public class ResultsPage extends Page {

	private static final String HTML_A_TAG = "a";
	private static final String HTML_TR_TAG = "tr";

	private static final int HEADER_POSITION = 1;

	protected List<WebElement> results;

	public ResultsPage(SharedDriver driver) {
		super(driver);
	}

	protected void selectResult(int index) {
		results.get(index).findElement(By.tagName(HTML_A_TAG)).click();
	}

	protected List<WebElement> retrieveResultsElements(boolean hasHeader,
			WebElement table) {
		results = table.findElements(By.tagName(HTML_TR_TAG));
		if (hasHeader) {
			results = results.subList(HEADER_POSITION, results.size());
		}
		return results;
	}

}
