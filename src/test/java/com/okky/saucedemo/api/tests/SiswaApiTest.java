package com.okky.saucedemo.api.tests;

import com.okky.saucedemo.api.requests.SiswaApiClient;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SiswaApiTest {

    private final SiswaApiClient siswaApiClient = new SiswaApiClient();

    // nis harus 5-20 digit angka; pakai timestamp supaya unik tiap kali CI jalan
    // (menghindari error "NIS sudah terdaftar" pada run berikutnya).
    private static final String NIS = String.valueOf(System.currentTimeMillis());

    private static String createdSiswaId;

    private String token() {
        return AuthApiTest.registeredToken == null ? "" : AuthApiTest.registeredToken;
    }

    @Test
    public void tc03_createSiswa_shouldReturn201() {
        JSONObject payload = new JSONObject();
        payload.put("nama", "Budi Santoso CI");
        payload.put("nis", NIS);
        // Format kelas yang divalidasi API: <romawi 1-2 huruf>-<jurusan>-<nomor>, mis. X-IPA-1 / XI-IPA-1
        payload.put("kelas", "X-IPA-1");
        payload.put("jurusan", "IPA");

        Response response = siswaApiClient.create(token(), payload.toString());

        assertEquals("Status code create siswa harus 201 (Created)", 201, response.getStatusCode());
        String id = response.jsonPath().getString("data.id");
        assertNotNull("Response create siswa harus mengembalikan data.id", id);
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
        payload.put("nama", "Budi Santoso CI Updated");
        payload.put("nis", NIS);
        payload.put("kelas", "X-IPA-2");
        payload.put("jurusan", "IPA");

        Response response = siswaApiClient.update(token(), createdSiswaId, payload.toString());

        assertEquals("Status code update siswa harus 200 (OK)", 200, response.getStatusCode());
    }

    @Test
    public void tc06_createSiswaWithEmptyName_shouldReturn400() {
        JSONObject payload = new JSONObject();
        payload.put("nama", "");
        payload.put("nis", NIS + "1");
        payload.put("kelas", "X-IPA-1");
        payload.put("jurusan", "IPA");

        Response response = siswaApiClient.create(token(), payload.toString());

        assertEquals("Status code create siswa dengan nama kosong harus 400 (Bad Request)",
                400, response.getStatusCode());
    }
}
