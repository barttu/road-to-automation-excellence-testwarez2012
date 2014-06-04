package com.fp.trapeze.test.web;

import org.junit.runner.RunWith;

import cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format = { "pretty", "html:reports/cucumber-html-report" }, tags = { "@nexus-mobile" })
public class FeaturesRunner {
}
