package ru.khehelk.cityroutes.adminservice.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private WebDriver driver;

    private WebDriverWait wait;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private By loginButton = By.xpath("//button[text()='Вход']");
    private By registrationButton = By.xpath("//button[text()='Регистрация']");
    private By exitButton = By.xpath("//button[text()='Выход']");
    private By directoryButton = By.xpath("//button[text()='Справочник']");

    public void clickLogin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        driver.findElement(loginButton).click();
    }

    public void clickRegistration() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationButton));
        driver.findElement(registrationButton).click();
    }

    public void clickExit() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(exitButton));
        driver.findElement(exitButton).click();
    }

    public void clickDirectoryButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(directoryButton));
        driver.findElement(directoryButton).click();
    }

    public boolean hasExit() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(exitButton));
        return driver.findElement(exitButton) != null;
    }

}
