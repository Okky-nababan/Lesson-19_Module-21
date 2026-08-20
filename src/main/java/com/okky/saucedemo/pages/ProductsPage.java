package com.okky.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman Products (inventory.html) yang muncul
 * setelah login SauceDemo berhasil. Dipakai untuk memverifikasi
 * bahwa skenario login positif benar-benar mengarahkan user ke
 * halaman yang tepat.
 */
public class ProductsPage extends BasePage {

    private static final By PAGE_TITLE = By.className("title");
    private static final By INVENTORY_LIST = By.className("inventory_list");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayedAfterLogin() {
        return isDisplayed(INVENTORY_LIST);
    }

    public String getPageHeading() {
        return readText(PAGE_TITLE);
    }
}
