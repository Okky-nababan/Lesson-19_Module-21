package com.okky.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Login SauceDemo (https://www.saucedemo.com/).
 * Semua locator dan fungsi klik tombol / isi formulir / baca teks khusus
 * halaman login dikumpulkan di sini agar test case (step definitions)
 * tetap bersih dan hanya bicara dalam bahasa bisnis (login, verifikasi, dsb).
 */
public class LoginPage extends BasePage {

    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage goTo(String baseUrl) {
        open(baseUrl);
        return this;
    }

    public void enterUsername(String username) {
        type(USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        type(PASSWORD_INPUT, password);
    }

    public void clickLoginButton() {
        click(LOGIN_BUTTON);
    }

    /**
     * Melakukan alur login lengkap: isi username, isi password, klik tombol login.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(ERROR_MESSAGE);
    }

    public String getErrorMessageText() {
        return readText(ERROR_MESSAGE);
    }
}
