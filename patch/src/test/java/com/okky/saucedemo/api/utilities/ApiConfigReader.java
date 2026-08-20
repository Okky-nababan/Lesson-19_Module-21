package com.okky.saucedemo.api.utilities;

/**
 * Konfigurasi environment testing untuk API automation test.
 * Base URL bisa dioverride lewat system property -Dapi.base.url=... saat
 * dijalankan dari CI (GitHub Actions) atau lokal.
 */
public class ApiConfigReader {

    private static final String DEFAULT_BASE_URL = "https://api.rizqifauzan.com";

    private ApiConfigReader() {
    }

    public static String getBaseUrl() {
        return System.getProperty("api.base.url", DEFAULT_BASE_URL);
    }
}
