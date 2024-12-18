package ru.khehelk.cityroutes.adminservice;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.khehelk.cityroutes.adminservice.page.AdminPage;
import ru.khehelk.cityroutes.adminservice.page.DirectoryPage;
import ru.khehelk.cityroutes.adminservice.page.LoginPage;
import ru.khehelk.cityroutes.adminservice.page.MainPage;
import ru.khehelk.cityroutes.adminservice.page.RegistrationPage;

class UIIntTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void registerTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        driver.get("http://localhost:5173");
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.clickRegistration();
        RegistrationPage registrationPage = new RegistrationPage(driver, wait);
        registrationPage.enterUsername(UUID.randomUUID().toString())
            .enterPassword(UUID.randomUUID().toString())
            .clickRegistrationButton();
        mainPage.hasExit();
    }

    @Test
    void loginTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        driver.get("http://localhost:5173");
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.clickLogin();
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.enterUsername("test")
            .enterPassword("test")
            .clickLogin();
        mainPage.hasExit();
    }

    @Test
    void adminTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        driver.get("http://localhost:5173");
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.clickLogin();
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.enterUsername("admin@admin.ru")
            .enterPassword("admin")
            .clickLogin();
        mainPage.hasExit();
        mainPage.clickDirectoryButton();
        DirectoryPage directoryPage = new DirectoryPage(driver, wait);
        directoryPage.clickAdminButton();
        String uuid = UUID.randomUUID().toString();
        AdminPage adminPage = new AdminPage(driver, wait);
        adminPage.enterCityRegionField("73")
            .enterCityField(uuid)
            .clickAddCityButton();
        adminPage.enterStopRegionField("73")
            .chooseStopCitySelectBox(uuid.toUpperCase())
            .enterStopNameField(uuid)
            .clickAddStopButton();
        adminPage.enterRouteRegionField("73")
            .chooseRouteCitySelectBox(uuid.toUpperCase())
            .enterRouteNumberField(String.valueOf(new Random().nextInt()))
            .enterRouteRangeStartField("10")
            .enterRouteRangeEndField("15")
            .chooseRouteStopSelectBox(uuid)
            .clickAddRouteButton();
        mainPage.clickDirectoryButton();
        directoryPage.enterRegionCode("73")
            .chooseCity(uuid.toUpperCase())
            .clickSearchButton()
            .checkContentExists();
    }

}
