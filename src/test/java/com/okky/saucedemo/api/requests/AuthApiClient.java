package com.okky.saucedemo.api.requests;

import com.okky.saucedemo.api.utilities.ApiConfigReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthApiClient {

    private String baseUrl() {
        return ApiConfigReader.getBaseUrl();
    }

    public Response register(String payloadJson) {
        return given()
                .baseUri(baseUrl())
                .contentType("application/json")
                .body(payloadJson)
                .when()
                .post("/api/auth/register");
    }

    public Response login(String payloadJson) {
        return given()
                .baseUri(baseUrl())
                .contentType("application/json")
                .body(payloadJson)
                .when()
                .post("/api/auth/login");
    }

    public Response me(String token) {
        return given()
                .baseUri(baseUrl())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/auth/me");
    }

    public Response logout(String token) {
        return given()
                .baseUri(baseUrl())
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/api/auth/logout");
    }
}
