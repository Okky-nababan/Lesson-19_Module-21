Feature: Login SauceDemo
  Sebagai pengguna website SauceDemo,
  Saya ingin melakukan login menggunakan username dan password,
  Agar saya dapat mengakses halaman produk sesuai dengan status akun saya.

  Background:
    Given saya membuka halaman login SauceDemo

  # ---------- POSITIVE ----------
  @positive
  Scenario: Login berhasil dengan username dan password yang benar
    When saya memasukkan username "standard_user" dan password "secret_sauce"
    And saya menekan tombol login
    Then saya berhasil masuk ke halaman Products

  # ---------- NEGATIVE ----------
  @negative
  Scenario Outline: Login gagal dengan data yang salah
    When saya memasukkan username "<username>" dan password "<password>"
    And saya menekan tombol login
    Then saya melihat pesan error "<pesanError>"

    Examples:
      | username        | password        | pesanError                                                                                   |
      | standard_user   | password_salah  | Epic sadface: Username and password do not match any user in this service                  |
      | user_tidak_ada  | secret_sauce    | Epic sadface: Username and password do not match any user in this service                  |
      | locked_out_user | secret_sauce    | Epic sadface: Sorry, this user has been locked out.                                          |

  # ---------- BOUNDARY ----------
  @boundary
  Scenario: Login gagal ketika username dan password dikosongkan
    When saya menekan tombol login tanpa mengisi username dan password
    Then saya melihat pesan error "Epic sadface: Username is required"

  @boundary
  Scenario: Login gagal ketika password dikosongkan
    When saya memasukkan username "standard_user" dan password ""
    And saya menekan tombol login
    Then saya melihat pesan error "Epic sadface: Password is required"

  @boundary
  Scenario: Login gagal ketika username sangat panjang (di luar data valid)
    When saya memasukkan username "standard_user_dengan_teks_yang_sangat_sangat_panjang_melebihi_batas_wajar_untuk_sebuah_username_pengguna_biasa" dan password "secret_sauce"
    And saya menekan tombol login
    Then saya melihat pesan error "Epic sadface: Username and password do not match any user in this service"
