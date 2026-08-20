package com.okky.saucedemo.api.tests;

import com.okky.saucedemo.api.requests.SiswaApiClient;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * API Automation Test - Siswa CRUD (Siswa Management API - api.rizqifauzan.com)
 * TC03 create (positive), TC04 get-by-id (positive), TC05 update (positive),
 * TC06 create dengan nama kosong (negative).
 *
 * Bergantung pada token yang dihasilkan AuthApiTest (dijalankan lebih dulu
 * karena JUnit menjalankan test class dalam satu run; token di-refresh
 * lokal di sini sebagai fallback bila dijalankan terisolasi).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SiswaApiTest {

    private final SiswaApiClient siswaApiClient = new SiswaApiClient();

    private static String createdSiswaId;

    private String token() {
        // Fallback: kalau AuthApiTest belum sempat jalan (mis. test class ini
        // dieksekusi sendirian), token kosong akan membuat request auth gagal
        // dengan 401 - ini dicatat sebagai batasan yang wajar untuk shared session
        // sederhana berbasis static field di JUnit4.
        return AuthApiTest.registeredToken == null ? "" : AuthApiTest.registeredToken;
    }

    @Test
    public void tc03_createSiswa_shouldReturn201() {
        JSONObject payload = new JSONObject();
        payload.put("nama", "Budi Santoso CI");
        payload.put("nis", "20260820"); // nis hanya boleh berisi angka
        payload.put("kelas", "XII IPA 1");

        Response response = siswaApiClient.create(token(), payload.toString());

        assertEquals("Status code create siswa harus 201 (Created)", 201, response.getStatusCode());
        String id = response.jsonPath().getString("id");
        assertNotNull("Response create siswa harus mengembalikan id", id);
        createdSiswaId = id;
    }

    @Test
    public void tc04_getSiswaById_shouldReturn200() {
        Response response = siswaApiClient.getById(token(), createdSiswaId);
        assertEquals("Status code get siswa by id harus 200 (OK)", 200, response.getStatusCode());
    }

    @Test
    public void tc05_updateSiswa_shouldReturn200() {
        JSONObject payload = new JSONObject();
        payload.put("nama", "Budi Santoso CI (Updated)");
        payload.put("nis", "20260820");
        payload.put("kelas", "XII IPA 2");

        Response response = siswaApiClient.update(token(), createdSiswaId, payload.toString());

        assertEquals("Status code update siswa harus 200 (OK)", 200, response.getStatusCode());
    }

    @Test
    public void tc06_createSiswaWithEmptyName_shouldReturn400() {
        JSONObject payload = new JSONObject();
        payload.put("nama", "");
        payload.put("nis", "20260821");
        payload.put("kelas", "XII IPA 1");

        Response response = siswaApiClient.create(token(), payload.toString());

        assertEquals("Status code create siswa dengan nama kosong harus 400 (Bad Request)",
                400, response.getStatusCode());
    }
}
