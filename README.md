# HW19 — UI Automation Framework: Cucumber + Selenium + Java + Gradle

**Pekerjaan Rumah 19 | Module 21 — QA Engineer New (JayJay School)**
Author: Okky Alexander Nababan

## 1. Ringkasan

Framework otomatisasi ini menguji **tampilan dan fungsi halaman Login** pada website demo **[SauceDemo / Swag Labs](https://www.saucedemo.com/)**. Framework dibangun dengan:

| Komponen | Tool | Peran |
|---|---|---|
| Test runner language | Java 17+ | Bahasa implementasi step definitions & page object |
| Build tool | Gradle | Resolusi dependency, kompilasi, eksekusi test |
| BDD / skenario uji | Cucumber (Gherkin) | Menulis skenario uji dalam format cerita (Given/When/Then) |
| Browser automation | Selenium WebDriver | Menjalankan interaksi nyata di browser (Chrome) |
| Driver management | WebDriverManager (Boni Garcia) | Auto-download & setup ChromeDriver sesuai versi Chrome lokal |
| Test execution | JUnit 4 (Cucumber JUnit runner) | Menjalankan seluruh skenario Cucumber sebagai satu test suite |
| Report | Cucumber HTML/JSON plugin | Laporan hasil eksekusi otomatis (`build/reports/cucumber/`) |

## 2. Struktur Project

```
hw19-cucumber-selenium/
├── build.gradle
├── settings.gradle
├── README.md
├── evidence/
│   └── positive_login_success.jpg      # bukti eksekusi manual skenario positif di saucedemo.com
├── src/
│   ├── main/
│   │   ├── java/com/okky/saucedemo/
│   │   │   ├── pages/
│   │   │   │   ├── BasePage.java       # operasi generik Selenium (wait, click, type, readText)
│   │   │   │   ├── LoginPage.java      # Page Object halaman Login (locator + fungsi)
│   │   │   │   └── ProductsPage.java   # Page Object halaman Products (verifikasi login sukses)
│   │   │   └── utilities/
│   │   │       ├── ConfigReader.java   # baca base URL / wait / mode headless dari config.properties
│   │   │       └── DriverFactory.java  # inisialisasi & quit WebDriver (environment setup)
│   │   └── resources/
│   │       └── config.properties       # base.url, implicit.wait.seconds, headless
│   └── test/
│       ├── java/com/okky/saucedemo/
│       │   ├── hooks/Hooks.java             # @Before buka browser, @After tutup browser, screenshot on fail
│       │   ├── stepdefinitions/
│       │   │   └── LoginStepDefinitions.java # implementasi step Gherkin -> pemanggilan LoginPage
│       │   └── runner/TestRunner.java        # @RunWith(Cucumber.class) + konfigurasi report
│       └── resources/
│           └── features/
│               └── login.feature       # skenario Gherkin: positive, negative, boundary
```

## 3. Pola Desain: Page Object Model (POM)

Setiap halaman website direpresentasikan oleh 1 kelas Java (`LoginPage`, `ProductsPage`) yang mewarisi `BasePage`. Kelas-kelas ini:

- Menyimpan **locator** elemen (`By.id`, `By.className`, dst) sebagai konstanta privat.
- Menyediakan **fungsi aksi** bertingkat tinggi (`enterUsername`, `enterPassword`, `clickLoginButton`, `login`) untuk klik tombol & isi formulir.
- Menyediakan **fungsi pembacaan** (`getErrorMessageText`, `isErrorMessageDisplayed`, `getPageHeading`) untuk membaca teks/status dari halaman.

Keuntungan: jika struktur HTML SauceDemo berubah, hanya `LoginPage`/`ProductsPage` yang perlu diperbarui — seluruh skenario Gherkin dan step definitions tidak perlu disentuh. Ini juga memisahkan **"apa yang diuji"** (feature file, bahasa bisnis) dari **"bagaimana cara mengujinya"** (page object, detail teknis Selenium).

## 4. Skenario Uji (`login.feature`)

| Tag | Skenario | Tipe |
|---|---|---|
| `@positive` | Login dengan `standard_user` / `secret_sauce` → masuk ke halaman Products | Positive |
| `@negative` | Login dengan password salah → pesan error "Username and password do not match..." | Negative |
| `@negative` | Login dengan username yang tidak terdaftar → pesan error sama seperti di atas | Negative |
| `@negative` | Login dengan `locked_out_user` → pesan error "Sorry, this user has been locked out." | Negative |
| `@boundary` | Login tanpa mengisi apa pun → pesan error "Username is required" | Boundary (empty) |
| `@boundary` | Login dengan password dikosongkan → pesan error "Password is required" | Boundary (empty) |
| `@boundary` | Login dengan username sangat panjang → tidak match, error sama seperti negative | Boundary (long input) |

Total 7 skenario (1 `Scenario` positif, 1 `Scenario Outline` negatif dengan 3 baris data, dan 3 `Scenario` boundary) — memenuhi ketentuan uji positif, negatif, dan batas (boundary) pada satu halaman fungsional (Login).

## 5. Cara Menjalankan

```bash
git clone <URL_REPOSITORY_INI>
cd hw19-cucumber-selenium
./gradlew test
```

Prasyarat:
- JDK 17+ terpasang dan `JAVA_HOME` ter-set.
- Google Chrome terpasang di komputer (WebDriverManager akan mengunduh ChromeDriver versi yang sesuai secara otomatis saat test pertama kali dijalankan — butuh koneksi internet untuk resolusi dependency Gradle dari Maven Central dan untuk download ChromeDriver).
- Untuk melihat browser berjalan (bukan mode headless) saat debugging, ubah `headless=false` di `src/main/resources/config.properties`.

Setelah eksekusi selesai, laporan HTML tersedia di:
```
build/reports/cucumber/cucumber-report.html
```
dan ringkasan test JUnit standar di:
```
build/reports/tests/test/index.html
```

## 6. Catatan Verifikasi

Ketujuh skenario di atas (username kosong, password kosong, username & password salah, username tidak terdaftar, akun terkunci `locked_out_user`, username sangat panjang, dan login sukses `standard_user`) sudah **dijalankan secara nyata (live)** terhadap `https://www.saucedemo.com/` melalui browser automation untuk memverifikasi bahwa teks pesan error dan perilaku aplikasi yang di-assert dalam `login.feature` & `LoginStepDefinitions.java` sudah tepat 1:1 dengan perilaku aplikasi sesungguhnya (lihat `evidence/positive_login_success.jpg` untuk bukti skenario positif). Source code Java/Gradle di repository ini adalah implementasi produksi dari skenario yang telah diverifikasi tersebut; eksekusi penuh via `./gradlew test` memerlukan resolusi dependency dari Maven Central yang tidak dapat dijangkau dari sandbox pembuatan kode ini, sehingga eksekusi akhir dengan `./gradlew test` perlu dilakukan di komputer dengan akses internet normal.
