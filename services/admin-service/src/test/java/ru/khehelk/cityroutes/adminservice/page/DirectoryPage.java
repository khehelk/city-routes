package ru.khehelk.cityroutes.adminservice.page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DirectoryPage {

    private WebDriver driver;

    private WebDriverWait wait;

    public DirectoryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private By regionСodeField = By.cssSelector("input[placeholder='Введите код региона']");
    private By citySelectBox = By.xpath("//select[option[text()='Выберите город']]");
    private By adminButton = By.xpath("//button[text()='Панель администратора']");
    private By searchButton = By.xpath("//button[./img]");
    private By content = By.className("routes-list");

    public void clickAdminButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminButton));
        driver.findElement(adminButton).click();
    }

    public DirectoryPage enterRegionCode(String number) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(regionСodeField));
        WebElement regionCodeInput = driver.findElement(regionСodeField);
        regionCodeInput.clear();
        regionCodeInput.sendKeys(number);
        return this;
    }

    public DirectoryPage chooseCity(String upperCase) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(citySelectBox));
        WebElement stopElement = driver.findElement(citySelectBox);
        stopElement.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Select select = new Select(stopElement);
        select.selectByVisibleText(upperCase);
        return this;
    }

    public DirectoryPage clickSearchButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
        driver.findElement(searchButton).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        return this;
    }

    public DirectoryPage checkContentExists() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(content));
        List<WebElement> contentList = driver.findElement(content).findElements(By.tagName("div"));
        if (contentList.isEmpty()) {
            throw new RuntimeException();
        }
        return this;
    }
}
