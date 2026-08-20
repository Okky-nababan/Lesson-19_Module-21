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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthApiTest {

    // Catatan: field "nama" divalidasi API hanya boleh huruf & spasi (tidak boleh angka),
    // jadi keunikan data per-run CI cukup dijaga lewat email (boleh mengandung angka/simbol).
    private static final String UNIQUE_SUFFIX = String.valueOf(System.currentTimeMillis());
    private static final String NAMA = "HW Automation CI Tester";
    private static final String EMAIL = "hw20.ci." + UNIQUE_SUFFIX + "@example.com";
    private static final String PASSWORD = "Password123!";

    private final AuthApiClient authApiClient = new AuthApiClient();

    static String registeredToken;

    @Test
    public void tc01_registerNewUser_shouldReturn201() {
        JSONObject payload = new JSONObject();
        payload.put("nama", NAMA);
        payload.put("email", EMAIL);
        payload.put("password", PASSWORD);

        Response response = authApiClient.register(payload.toString());

        assertEquals("Status code register harus 201 (Created)", 201, response.getStatusCode());
        assertTrue("Response register harus success=true", response.jsonPath().getBoolean("success"));
        assertNotNull("Response body register tidak boleh null", response.getBody());
    }

    @Test
    public void tc02_loginRegisteredUser_shouldReturn200AndToken() {
        JSONObject payload = new JSONObject();
        payload.put("email", EMAIL);
        payload.put("password", PASSWORD);

        Response response = authApiClient.login(payload.toString());

        assertEquals("Status code login harus 200 (OK)", 200, response.getStatusCode());

        String token = response.jsonPath().getString("data.token");
        assertTrue("Response login harus mengandung token JWT", token != null && !token.isEmpty());

        registeredToken = token;
    }
}
