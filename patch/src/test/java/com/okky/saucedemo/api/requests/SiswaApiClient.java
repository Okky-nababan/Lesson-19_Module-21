package com.okky.saucedemo.api.requests;

import com.okky.saucedemo.api.utilities.ApiConfigReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Wrapper request untuk endpoint /api/siswa (CRUD, memerlukan Bearer token).
 */
public class SiswaApiClient {

    private String baseUrl() {
        return ApiConfigReader.getBaseUrl();
    }

    public Response getAll(String token) {
        return given()
                .baseUri(baseUrl())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/siswa");
    }

    public Response getById(String token, String id) {
        return given()
                .baseUri(baseUrl())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/siswa/" + id);
    }

    public Response create(String token, String payloadJson) {
        return given()
                .baseUri(baseUrl())
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(payloadJson)
                .when()
                .post("/api/siswa");
    }

    public Response update(String token, String id, String payloadJson) {
        return given()
                .baseUri(baseUrl())
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(payloadJson)
                .when()
                .put("/api/siswa/" + id);
    }

    public Response delete(String token, String id) {
        return given()
                .baseUri(baseUrl())
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/api/siswa/" + id);
    }
}
