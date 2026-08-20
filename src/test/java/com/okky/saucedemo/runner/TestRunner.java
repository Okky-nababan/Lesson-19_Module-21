package com.okky.saucedemo.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Test Runner - mengeksekusi seluruh skenario Cucumber yang ada di
 * src/test/resources/features dan menghasilkan laporan (pretty console,
 * HTML report, JSON report) melalui plugin yang dikonfigurasi di bawah.
 *
 * Jalankan dengan: ./gradlew test
 * Report HTML akan tersedia di: build/reports/cucumber/cucumber-report.html
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.okky.saucedemo.stepdefinitions", "com.okky.saucedemo.hooks"},
        plugin = {
                "pretty",
                "html:build/reports/cucumber/cucumber-report.html",
                "json:build/reports/cucumber/cucumber-report.json",
                "summary"
        },
        monochrome = true
)
public class TestRunner {
}
