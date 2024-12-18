package ru.khehelk.cityroutes.adminservice.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {

    private WebDriver driver;

    private WebDriverWait wait;

    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private By usernameField = By.cssSelector("input[placeholder='Введите email']");
    private By passwordField = By.cssSelector("input[placeholder='Введите пароль']");
    private By registrationButton = By.xpath("//button[text()='Зарегистрироваться']");

    public RegistrationPage enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        WebElement usernameInput = driver.findElement(usernameField);
        usernameInput.clear();
        usernameInput.sendKeys(username);
        return this;
    }

    public RegistrationPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        WebElement passwordInput = driver.findElement(passwordField);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public RegistrationPage clickRegistrationButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationButton));
        driver.findElement(registrationButton).click();
        return this;
    }

}
