package com.okky.saucedemo.api.utilities;

public class ApiConfigReader {

    private static final String DEFAULT_BASE_URL = "https://api.rizqifauzan.com";

    private ApiConfigReader() {
    }

    public static String getBaseUrl() {
        return System.getProperty("api.base.url", DEFAULT_BASE_URL);
    }
}
