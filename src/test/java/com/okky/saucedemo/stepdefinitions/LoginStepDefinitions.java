package com.okky.saucedemo.stepdefinitions;

import com.okky.saucedemo.pages.LoginPage;
import com.okky.saucedemo.pages.ProductsPage;
import com.okky.saucedemo.utilities.ConfigReader;
import com.okky.saucedemo.utilities.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Step Definitions - menghubungkan setiap baris Gherkin di login.feature
 * dengan perintah Java/Selenium yang sesungguhnya, dengan memanggil
 * fungsi-fungsi yang sudah disediakan oleh LoginPage (Page Object Model).
 */
public class LoginStepDefinitions {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    private WebDriver driver() {
        return DriverFactory.getDriver();
    }

    @Given("saya membuka halaman login SauceDemo")
    public void saya_membuka_halaman_login_saucedemo() {
        loginPage = new LoginPage(driver());
        loginPage.goTo(ConfigReader.getBaseUrl());
    }

    @When("saya memasukkan username {string} dan password {string}")
    public void saya_memasukkan_username_dan_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("saya menekan tombol login")
    public void saya_menekan_tombol_login() {
        loginPage.clickLoginButton();
    }

    @When("saya menekan tombol login tanpa mengisi username dan password")
    public void saya_menekan_tombol_login_tanpa_mengisi_username_dan_password() {
        loginPage.clickLoginButton();
    }

    @Then("saya berhasil masuk ke halaman Products")
    public void saya_berhasil_masuk_ke_halaman_products() {
        productsPage = new ProductsPage(driver());
        assertTrue("Halaman Products (inventory list) seharusnya tampil setelah login berhasil",
                productsPage.isDisplayedAfterLogin());
        assertEquals("Products", productsPage.getPageHeading());
    }

    @Then("saya melihat pesan error {string}")
    public void saya_melihat_pesan_error(String pesanErrorYangDiharapkan) {
        assertTrue("Pesan error seharusnya ditampilkan pada halaman login",
                loginPage.isErrorMessageDisplayed());
        assertEquals(pesanErrorYangDiharapkan, loginPage.getErrorMessageText());
    }
}
