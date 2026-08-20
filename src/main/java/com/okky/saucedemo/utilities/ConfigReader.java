package com.okky.saucedemo.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class untuk membaca konfigurasi environment testing
 * (base URL, implicit wait, mode headless) dari file config.properties
 * di classpath (src/main/resources/config.properties).
 *
 * Nilai default disediakan agar test tetap bisa jalan walau file config
 * tidak ditemukan (mis. saat dijalankan dari IDE tanpa resource folder ter-copy).
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Gagal membaca config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url", "https://www.saucedemo.com/");
    }

    public static int getImplicitWaitSeconds() {
        return Integer.parseInt(properties.getProperty("implicit.wait.seconds", "10"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "true"));
    }
}
