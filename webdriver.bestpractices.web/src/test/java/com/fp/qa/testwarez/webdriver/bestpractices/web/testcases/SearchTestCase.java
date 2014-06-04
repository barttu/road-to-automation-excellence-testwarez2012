package com.fp.qa.testwarez.webdriver.bestpractices.web.testcases;

import static org.junit.Assert.assertEquals;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.fp.qa.testwarez.webdriver.bestpractices.web.pageobjects.HomePage;
import com.fp.qa.testwarez.webdriver.bestpractices.web.pageobjects.common.Place;

/*
 *	I'm testing search feature
 */
@RunWith(value = Parameterized.class)
public class SearchTestCase {

	// Web driver instance
	private WebDriver driver;

	// Test data
	private AbstractMap.SimpleEntry<String, List<Place>> testData;

	public SearchTestCase(AbstractMap.SimpleEntry<String, List<Place>> testData) {
		this.testData = testData;
	}

	/*
	 * Produces test data
	 */
	@Parameters
	public static Collection<Object[]> data() {
		List<Place> aldwychPlaces = new ArrayList<Place>();
		aldwychPlaces.add(new Place("Aldwych (closed) (WC2R 1)"));
		aldwychPlaces.add(new Place("Aldwych (WC2A 2)"));
		aldwychPlaces.add(new Place("Aldwych Avenue (IG6 1)"));
		aldwychPlaces.add(new Place("Aldwych Close (RM12 4)"));

		AbstractMap.SimpleEntry<String, List<Place>> aldwychData = new AbstractMap.SimpleEntry<String, List<Place>>(
				"Aldwych", aldwychPlaces);

		List<Place> westHamPlaces = new ArrayList<Place>();
		westHamPlaces.add(new Place("West Ham"));
		westHamPlaces.add(new Place("West Ham Substation (WHAM) (E16 4)"));
		westHamPlaces.add(new Place("West Ham (E15 3)"));
		westHamPlaces.add(new Place("West Ham Lane (E15 4)"));

		AbstractMap.SimpleEntry<String, List<Place>> westHamData = new AbstractMap.SimpleEntry<String, List<Place>>(
				"West Ham", westHamPlaces);

		Object[][] data = new Object[][] { { aldwychData }, { westHamData } };
		return Arrays.asList(data);
	}

	/*
	 * I'm invoked before a test run
	 */
	@Before
	public void setUp() throws Exception {
		// Start web driver instance
		driver = new FirefoxDriver();
	}

	/*
	 * Searching by ambiguous locality should return possible hits (matched
	 * locations)
	 */
	@Test
	public void searchLocalityTest() {
		List<Place> matchedPlaces = new HomePage(driver).get()
				.search(testData.getKey()).getPlaces();

		assertEquals(matchedPlaces, testData.getValue());
	}

	/*
	 * I'm invoked after a test run
	 */
	@After
	public void tearDown() throws Exception {
		// Close web driver instance
		driver.quit();
	}
}
