package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ElementUtils {

    private static final Logger logger = LoggerFactory.getLogger(ElementUtils.class);

    private static WebDriver getDriver() {

        return DriverFactory.getInstance().getDriver();
    }


    // ========== CLICK METHODS ==========
    public static void click(WebElement element) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);

            // Add screenshot before action for debugging
            if (ConfigReader.getBoolean("screenshot.on.action")) {
                takeScreenshot("before_click");
            }

            element.click();
            logger.info("Successfully clicked element: {}", getElementDescription(element));
        } catch (Exception e) {
            takeScreenshot("click_failed");
            logger.error("Failed to click element {}: {}", getElementDescription(element), e.getMessage());
            throw new RuntimeException("Click failed: " + e.getMessage(), e);
        }
    }


    public static void jsClick(WebElement element) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForVisibility(driver, element);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
            logger.info("Successfully JavaScript clicked element");
        } catch (Exception e) {
            logger.error("Failed to JavaScript click element: {}", e.getMessage());
            throw new RuntimeException("Failed to JavaScript click element: " + e.getMessage(), e);
        }
    }


    public static void actionsClick(WebElement element) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            Actions actions = new Actions(driver);
            actions.click(element).perform();
            logger.info("Successfully Actions clicked element");
        } catch (Exception e) {
            logger.error("Failed to Actions click element: {}", e.getMessage());
            throw new RuntimeException("Failed to Actions click element: " + e.getMessage(), e);
        }
    }


    public static void doubleClick(WebElement element) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            Actions actions = new Actions(driver);
            actions.doubleClick(element).perform();
            logger.info("Successfully double clicked element");
        } catch (Exception e) {
            logger.error("Failed to double click element: {}", e.getMessage());
            throw new RuntimeException("Failed to double click element: " + e.getMessage(), e);
        }
    }


    public static void rightClick(WebElement element) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            Actions actions = new Actions(driver);
            actions.contextClick(element).perform();
            logger.info("Successfully right clicked element");
        } catch (Exception e) {
            logger.error("Failed to right click element: {}", e.getMessage());
            throw new RuntimeException("Failed to right click element: " + e.getMessage(), e);
        }
    }


    public static void clickAndHold(WebElement element) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            Actions actions = new Actions(driver);
            actions.clickAndHold(element).perform();
            logger.info("Successfully click and hold element");
        } catch (Exception e) {
            logger.error("Failed to click and hold element: {}", e.getMessage());
            throw new RuntimeException("Failed to click and hold element: " + e.getMessage(), e);
        }
    }


    public static void forceClick(WebElement element) {
        Exception lastException = null;

        // Try standard click
        try {
            click(element);
            logger.info("Force click succeeded with standard click");
            return;
        } catch (Exception e) {
            lastException = e;
            logger.warn("Standard click failed, trying JavaScript click");
        }

        // Try JavaScript click
        try {
            jsClick(element);
            logger.info("Force click succeeded with JavaScript click");
            return;
        } catch (Exception e) {
            lastException = e;
            logger.warn("JavaScript click failed, trying Actions click");
        }

        // Try Actions click
        try {
            actionsClick(element);
            logger.info("Force click succeeded with Actions click");
            return;
        } catch (Exception e) {
            lastException = e;
            logger.error("All click methods failed for element");
        }

        throw new RuntimeException("All click methods failed for element", lastException);
    }



    // ========== INPUT METHODS ==========


    public static void clearAndType(WebElement element, String text) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            element.clear();
            element.sendKeys(text);
            logger.info("Successfully cleared and typed text: {}", text);
        } catch (Exception e) {
            logger.error("Failed to clear and type text '{}': {}", text, e.getMessage());
            throw new RuntimeException("Failed to clear and type text: " + e.getMessage(), e);
        }
    }


    public static void type(WebElement element, String text) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            element.sendKeys(text);
            logger.info("Successfully typed text: {}", text);
        } catch (Exception e) {
            logger.error("Failed to type text '{}': {}", text, e.getMessage());
            throw new RuntimeException("Failed to type text: " + e.getMessage(), e);
        }
    }


    public static void clear(WebElement element) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            element.clear();
            logger.info("Successfully cleared element text");
        } catch (Exception e) {
            logger.error("Failed to clear element: {}", e.getMessage());
            throw new RuntimeException("Failed to clear element: " + e.getMessage(), e);
        }
    }



    // ========== DROPDOWN METHODS ==========


    public static void selectByText(WebElement element, String text) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            Select select = new Select(element);
            select.selectByVisibleText(text);
            logger.info("Successfully selected by text: {}", text);
        } catch (Exception e) {
            logger.error("Failed to select by text '{}': {}", text, e.getMessage());
            throw new RuntimeException("Failed to select by text: " + e.getMessage(), e);
        }
    }


    public static void selectByValue(WebElement element, String value) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            Select select = new Select(element);
            select.selectByValue(value);
            logger.info("Successfully selected by value: {}", value);
        } catch (Exception e) {
            logger.error("Failed to select by value '{}': {}", value, e.getMessage());
            throw new RuntimeException("Failed to select by value: " + e.getMessage(), e);
        }
    }


    public static void selectByIndex(WebElement element, int index) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForClickability(driver, element);
            Select select = new Select(element);
            select.selectByIndex(index);
            logger.info("Successfully selected by index: {}", index);
        } catch (Exception e) {
            logger.error("Failed to select by index {}: {}", index, e.getMessage());
            throw new RuntimeException("Failed to select by index: " + e.getMessage(), e);
        }
    }



    // ========== UTILITY METHODS ========== //


    public static String getText(WebElement element) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForVisibility(driver, element);
            String text = element.getText();
            logger.debug("Retrieved text from element: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", e.getMessage());
            throw new RuntimeException("Failed to get text from element: " + e.getMessage(), e);
        }
    }


    public static String getDomAttribute(WebElement element, String attributeName) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForVisibility(driver, element);
            String value = element.getDomAttribute(attributeName);
            logger.debug("Retrieved DOM attribute '{}' from element: {}", attributeName, value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get DOM attribute '{}' from element: {}", attributeName, e.getMessage());
            throw new RuntimeException("Failed to get DOM attribute: " + e.getMessage(), e);
        }
    }


    public static String getDomProperty(WebElement element, String propertyName) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForVisibility(driver, element);
            String value = element.getDomProperty(propertyName);
            logger.debug("Retrieved DOM property '{}' from element: {}", propertyName, value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get DOM property '{}' from element: {}", propertyName, e.getMessage());
            throw new RuntimeException("Failed to get DOM property: " + e.getMessage(), e);
        }
    }


    public static boolean isDisplayed(WebElement element) {
        try {
            boolean displayed = element.isDisplayed();
            logger.debug("Element displayed status: {}", displayed);
            return displayed;
        } catch (Exception e) {
            logger.debug("Element not displayed or not found: {}", e.getMessage());
            return false;
        }
    }


    public static boolean isEnabled(WebElement element) {
        try {
            boolean enabled = element.isEnabled();
            logger.debug("Element enabled status: {}", enabled);
            return enabled;
        } catch (Exception e) {
            logger.debug("Element not enabled or not found: {}", e.getMessage());
            return false;
        }
    }


    public static boolean isSelected(WebElement element) {
        try {
            boolean selected = element.isSelected();
            logger.debug("Element selected status: {}", selected);
            return selected;
        } catch (Exception e) {
            logger.debug("Element not selected or not found: {}", e.getMessage());
            return false;
        }
    }


    public static void scrollIntoView(WebElement element) {
        try {
            WebDriver driver = getDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            WaitUtils.staticWait(500); // Small delay for scroll completion
            logger.info("Successfully scrolled element into view");
        } catch (Exception e) {
            logger.error("Failed to scroll element into view: {}", e.getMessage());
            throw new RuntimeException("Failed to scroll element into view: " + e.getMessage(), e);
        }
    }


    public static void highlightElement(WebElement element) {
        try {
            WebDriver driver = getDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid red'", element);
            WaitUtils.staticWait(1000);
            js.executeScript("arguments[0].style.border=''", element);
            logger.debug("Highlighted element for debugging");
        } catch (Exception e) {
            logger.warn("Failed to highlight element: {}", e.getMessage());
        }
    }


    public static List<WebElement> getElements(By locator) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForPresence(driver, locator);
            List<WebElement> elements = driver.findElements(locator);
            logger.debug("Found {} elements with locator: {}", elements.size(), locator);
            return elements;
        } catch (Exception e) {
            logger.error("Failed to get elements with locator {}: {}", locator, e.getMessage());
            throw new RuntimeException("Failed to get elements: " + e.getMessage(), e);
        }
    }


    public static void waitForPageLoad() {
        try {
            WebDriver driver = getDriver();
            WaitUtils.fluentWaitForElement(driver, webDriver -> {
                String readyState = ((JavascriptExecutor) webDriver).executeScript("return document.readyState").toString();
                return readyState.equals("complete") ? driver.findElement(By.tagName("body")) : null;
            });
            logger.info("Page loaded completely");
        } catch (Exception e) {
            logger.warn("Page load wait failed: {}", e.getMessage());
        }
    }


    public static void waitForTextInElement(WebElement element, String text) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForTextToBePresentInElement(driver, element, text);
            logger.info("Text '{}' found in element", text);
        } catch (Exception e) {
            logger.error("Failed to find text '{}' in element: {}", text, e.getMessage());
            throw new RuntimeException("Failed to find text in element: " + e.getMessage(), e);
        }
    }



    // ========== SCREENSHOT METHODS ========== //
    public static String takeScreenshot(String screenshotName) {
        try {
            WebDriver driver = getDriver();
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);


            String screenshotDir = "target/screenshots";
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filename = String.format("%s_%s.png", screenshotName, timestamp);
            String filePath = screenshotDir + "/" + filename;


            Files.write(Paths.get(filePath), screenshot);

            logger.info("Screenshot saved: {}", filePath);
            return filePath;

        } catch (Exception e) {
            logger.error("Failed to take screenshot '{}': {}", screenshotName, e.getMessage());
            return null;
        }
    }


    public static String takeElementScreenshot(WebElement element, String screenshotName) {
        try {
            WebDriver driver = getDriver();
            WaitUtils.waitForVisibility(driver, element);

            byte[] screenshot = element.getScreenshotAs(OutputType.BYTES);


            String screenshotDir = "target/screenshots/elements";

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filename = String.format("%s_element_%s.png", screenshotName, timestamp);
            String filePath = screenshotDir + "/" + filename;


            Files.write(Paths.get(filePath), screenshot);

            logger.info("Element screenshot saved: {}", filePath);
            return filePath;

        } catch (Exception e) {
            logger.error("Failed to take element screenshot '{}': {}", screenshotName, e.getMessage());
            return null;
        }
    }



// ========== ELEMENT DESCRIPTION METHODS ========== //


    public static String getElementDescription(WebElement element) {
        try {
            StringBuilder description = new StringBuilder();

            // Get tag name
            String tagName = element.getTagName();
            description.append(tagName);

            // Get ID if available
            String id = element.getDomAttribute("id");
            if (id != null && !id.isEmpty()) {
                description.append("#").append(id);
            }

            // Get class if available
            String className = element.getDomAttribute("class");
            if (className != null && !className.isEmpty()) {
                // Just get the first class to keep it concise
                String firstClass = className.split(" ")[0];
                description.append(".").append(firstClass);
            }

            // Get text content (first 30 characters)
            String text = element.getText();
            if (text != null && !text.trim().isEmpty()) {
                String shortText = text.length() > 30 ? text.substring(0, 30) + "..." : text;
                description.append("[text='").append(shortText).append("']");
            }

            // Get other useful attributes
            String name = element.getDomAttribute("name");
            if (name != null && !name.isEmpty()) {
                description.append("[name='").append(name).append("']");
            }

            String type = element.getDomAttribute("type");
            if (type != null && !type.isEmpty()) {
                description.append("[type='").append(type).append("']");
            }

            return description.toString();

        } catch (Exception e) {
            logger.debug("Failed to get element description: {}", e.getMessage());
            return "UnknownElement";
        }
    }


    public static String getDetailedElementInfo(WebElement element) {
        try {
            StringBuilder info = new StringBuilder();
            info.append("Element Details:\n");
            info.append("- Tag: ").append(element.getTagName()).append("\n");
            info.append("- Text: ").append(element.getText()).append("\n");
            info.append("- Displayed: ").append(element.isDisplayed()).append("\n");
            info.append("- Enabled: ").append(element.isEnabled()).append("\n");
            info.append("- Selected: ").append(element.isSelected()).append("\n");

            // Get common attributes
            String[] commonAttributes = {"id", "class", "name", "type", "value", "placeholder", "href", "src"};
            for (String attr : commonAttributes) {
                String value = element.getDomAttribute(attr);
                if (value != null && !value.isEmpty()) {
                    info.append("- ").append(attr).append(": ").append(value).append("\n");
                }
            }

            return info.toString();

        } catch (Exception e) {
            logger.debug("Failed to get detailed element info: {}", e.getMessage());
            return "Failed to get element details: " + e.getMessage();
        }
    }

}
