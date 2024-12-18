package ru.khehelk.cityroutes.adminservice.page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPage {

    private WebDriver driver;

    private WebDriverWait wait;

    public AdminPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private By regionField = By.cssSelector("input[placeholder='Введите код региона']");
    private By cityNameField = By.cssSelector("input[placeholder='Введите название города']");
    private By stopNameField = By.cssSelector("input[placeholder='Введите название остановки']");
    private By routeNumberField = By.cssSelector("input[placeholder='Введите номер маршрута']");
    private By rangeStartField = By.cssSelector("input[placeholder='Введите начальное значение диапазона времени ожидания (минуты)']");
    private By rangeEndField = By.cssSelector("input[placeholder='Введите конечное значение диапазона времени ожидания (минуты)']");
    private By citySelectBox = By.xpath("//select[option[text()='Выберите город']]");
    private By stopSelectBox = By.xpath("//select[option[text()='Выберите остановку']]");
    private By addCityButton = By.xpath("//button[text()='Добавить новый город']");
    private By addStopButton = By.xpath("//button[text()='Добавить новую остановку']");
    private By addRouteButton = By.xpath("//button[text()='Добавить новый маршрут']");

    public AdminPage enterCityRegionField(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(regionField));
        List<WebElement> regionInput = driver.findElements(regionField);
        regionInput.get(0).clear();
        regionInput.get(0).sendKeys(username);
        return this;
    }

    public AdminPage enterCityField(String cityName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityNameField));
        WebElement cityInput = driver.findElement(cityNameField);
        cityInput.clear();
        cityInput.sendKeys(cityName);
        return this;
    }

    public AdminPage enterStopRegionField(String region) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(regionField));
        List<WebElement> regionInput = driver.findElements(regionField);
        regionInput.get(1).clear();
        regionInput.get(1).sendKeys(region);
        return this;
    }

    public AdminPage chooseStopCitySelectBox(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(citySelectBox));
        List<WebElement> selectElement = driver.findElements(citySelectBox);
        selectElement.get(0).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Select select = new Select(selectElement.get(0));
        select.selectByVisibleText(text);
        return this;
    }

    public AdminPage enterStopNameField(String cityName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(stopNameField));
        WebElement cityInput = driver.findElement(stopNameField);
        cityInput.clear();
        cityInput.sendKeys(cityName);
        return this;
    }

    public AdminPage enterRouteRegionField(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(regionField));
        List<WebElement> regionInput = driver.findElements(regionField);
        regionInput.get(2).clear();
        regionInput.get(2).sendKeys(username);
        return this;
    }

    public AdminPage chooseRouteCitySelectBox(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(citySelectBox));
        List<WebElement> selectElement = driver.findElements(citySelectBox);
        selectElement.get(1).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Select select = new Select(selectElement.get(1));
        select.selectByVisibleText(text);
        return this;
    }

    public AdminPage enterRouteNumberField(String number) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(regionField));
        WebElement routeNumberInput = driver.findElement(routeNumberField);
        routeNumberInput.clear();
        routeNumberInput.sendKeys(number);
        return this;
    }

    public AdminPage enterRouteRangeStartField(String start) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(regionField));
        WebElement startInput = driver.findElement(rangeStartField);
        startInput.clear();
        startInput.sendKeys(start);
        return this;
    }

    public AdminPage enterRouteRangeEndField(String end) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(regionField));
        WebElement endInput = driver.findElement(rangeEndField);
        endInput.clear();
        endInput.sendKeys(end);
        return this;
    }

    public AdminPage chooseRouteStopSelectBox(String stopName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(stopSelectBox));
        WebElement stopElement = driver.findElement(stopSelectBox);
        stopElement.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Select select = new Select(stopElement);
        select.selectByVisibleText(stopName);
        return this;
    }

    public AdminPage clickAddCityButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addCityButton));
        driver.findElement(addCityButton).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        return this;
    }

    public AdminPage clickAddStopButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addStopButton));
        driver.findElement(addStopButton).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        return this;
    }

    public AdminPage clickAddRouteButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addRouteButton));
        driver.findElement(addRouteButton).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        return this;
    }

}
