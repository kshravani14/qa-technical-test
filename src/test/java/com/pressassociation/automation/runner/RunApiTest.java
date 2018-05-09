package com.pressassociation.automation.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue="com/pressassociation/automation/stepDefinitions",
        format = {"pretty", "html:test-results/hotelapp", "json:test-results/hotelapp.json"},
        monochrome = true,
        tags = {"~@failing"}
)

public class RunApiTest {
}
