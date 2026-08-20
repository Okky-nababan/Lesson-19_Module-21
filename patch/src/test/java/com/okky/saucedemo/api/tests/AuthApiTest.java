package com.okky.saucedemo.api.tests;

import com.okky.saucedemo.api.requests.AuthApiClient;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * API Automation Test - Auth (Siswa Management API - api.rizqifauzan.com)
 * TC01 Register (positive), TC02 Login (positive).
 * Data dibuat unik per-run (timestamp) agar test bisa dijalankan berulang
 * kali di CI (setiap commit / PR merge) tanpa bentrok "user already exists".
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthApiTest {

    private static final String UNIQUE_SUFFIX = String.valueOf(System.currentTimeMillis());
    private static final String EMAIL = "hw20.ci." + UNIQUE_SUFFIX + "@example.com";
    private static final String PASSWORD = "Password123!";

    private final AuthApiClient authApiClient = new AuthApiClient();

    // Dipakai lintas test-case dalam class ini (register -> login -> token)
    static String registeredToken;

    @Test
    public void tc01_registerNewUser_shouldReturn201() {
        JSONObject payload = new JSONObject();
        payload.put("name", "HW20 CI User " + UNIQUE_SUFFIX);
        payload.put("email", EMAIL);
        payload.put("password", PASSWORD);

        Response response = authApiClient.register(payload.toString());

        assertEquals("Status code register harus 201 (Created)", 201, response.getStatusCode());
        assertNotNull("Response body register tidak boleh null", response.getBody());
    }

    @Test
    public void tc02_loginRegisteredUser_shouldReturn200AndToken() {
        JSONObject payload = new JSONObject();
        payload.put("email", EMAIL);
        payload.put("password", PASSWORD);

        Response response = authApiClient.login(payload.toString());

        assertEquals("Status code login harus 200 (OK)", 200, response.getStatusCode());

        String token = response.jsonPath().getString("token");
        assertTrue("Response login harus mengandung token JWT", token != null && !token.isEmpty());

        registeredToken = token;
    }
}
