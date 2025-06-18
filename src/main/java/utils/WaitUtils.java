package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import java.time.Duration;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitUtils {


    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);


    private WaitUtils() {
    }


    private static WebDriverWait getWebDriverWait(WebDriver driver) {
        long explicitWaitTime = Long.parseLong(ConfigReader.getProperty("explicitWait"));
        return new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
    }


    public static WebElement waitForVisibility(WebDriver driver, WebElement element) {
        logger.debug("Waiting for element visibility: {}", element);
        return getWebDriverWait(driver).until(ExpectedConditions.visibilityOf(element));
    }


    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        logger.debug("Waiting for element visibility using locator: {}", locator);
        return getWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static boolean waitForInvisibility(WebDriver driver, WebElement element) {
        logger.debug("Waiting for element to become invisible: {}", element);
        return getWebDriverWait(driver).until(ExpectedConditions.invisibilityOf(element));
    }

    public static boolean waitForInvisibility(WebDriver driver, By locator) {
        logger.debug("Waiting for element to become invisible using locator: {}", locator);
        return getWebDriverWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }


    public static WebElement waitForClickability(WebDriver driver, WebElement element) {
        logger.debug("Waiting for element clickability: {}", element);
        return getWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }


    public static WebElement waitForClickability(WebDriver driver, By locator) {
        logger.debug("Waiting for element clickability using locator: {}", locator);
        return getWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }


    public static WebElement waitForPresence(WebDriver driver, By locator) {
        logger.debug("Waiting for element presence using locator: {}", locator);
        return getWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    public static boolean waitForTextToBePresentInElement(WebDriver driver, WebElement element, String text) {
        logger.debug("Waiting for text '{}' to be present in element: {}", text, element);
        return getWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElement(element, text));
    }


    public static boolean waitForTextToBePresentInElementLocated(WebDriver driver, By locator, String text) {
        logger.debug("Waiting for text '{}' to be present in element located by: {}", text, locator);
        return getWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }


    public static boolean waitForUrlContains(WebDriver driver, String urlContains) {
        logger.debug("Waiting for URL to contain: {}", urlContains);
        return getWebDriverWait(driver).until(ExpectedConditions.urlContains(urlContains));
    }


    public static WebElement fluentWaitForElement(WebDriver driver, Function<WebDriver, WebElement> condition) {
        long explicitWaitTime = Long.parseLong(ConfigReader.getProperty("explicitWait"));
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(explicitWaitTime))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        logger.debug("Applying fluent wait for an element-specific condition.");
        return wait.until(condition);
    }


    // Utility for Thread.sleep
    public static void staticWait(long milliseconds) {
        try {
            logger.warn("Performing a static wait for {} ms. Consider using explicit waits instead.", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            logger.error("Static wait interrupted: {}", e.getMessage());
        }
    }

}
