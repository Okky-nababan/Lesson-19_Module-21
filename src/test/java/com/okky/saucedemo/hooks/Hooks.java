package com.okky.saucedemo.hooks;

import com.okky.saucedemo.utilities.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber Hooks - menyiapkan environment testing sebelum tiap skenario
 * (buka browser baru) dan membersihkannya sesudahnya (tutup browser).
 * Juga melampirkan screenshot ke laporan Cucumber saat skenario gagal,
 * agar laporan hasil eksekusi memuat bukti visual kegagalan.
 */
public class Hooks {

    @Before
    public void setUp() {
        // Memanggil getDriver() otomatis menginisialisasi browser baru (lihat DriverFactory)
        DriverFactory.getDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver driver = DriverFactory.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
