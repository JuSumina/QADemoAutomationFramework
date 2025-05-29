package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverFactory {

    private static DriverFactory instance;
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final Map<String, WebDriver> driverMap = new ConcurrentHashMap<>();

    private DriverFactory() {}

    public static DriverFactory getInstance() {
        if (instance == null) {
            synchronized (DriverFactory.class) {
                if (instance == null) {
                    instance = new DriverFactory();
                }
            }
        }
        return instance;
    }

    public WebDriver createDriverFromProperties() {
        String browser = ConfigReader.getProperty("browser");
        boolean headless = ConfigReader.getBoolean("headless");
        String gridUrl = ConfigReader.getProperty("grid.url");
        return createDriver(browser, headless, gridUrl);
    }

    public WebDriver createDriver(String browser, boolean headless, String gridUrl) {
        WebDriver driver;

        if (gridUrl != null && !gridUrl.isEmpty()) {
            driver = createRemoteDriver(browser, headless, gridUrl);
        } else {
            driver = createLocalDriver(browser, headless);
        }

        if (driver != null) {
            configureDriver(driver);
            driverThreadLocal.set(driver);
            driverMap.put(String.valueOf(Thread.currentThread().threadId()), driver);
        }

        return driver;
    }

    private WebDriver createLocalDriver(String browser, boolean headless) {
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(getChromeOptions(headless));
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(getFirefoxOptions(headless));
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(getEdgeOptions(headless));
                break;
            case "safari":
                if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
                    throw new RuntimeException("Safari is supported only on macOS");
                }
                driver = new SafariDriver();
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
        return driver;
    }

    private WebDriver createRemoteDriver(String browser, boolean headless, String gridUrl) {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            switch (browser.toLowerCase()) {
                case "chrome":
                    capabilities.merge(getChromeOptions(headless));
                    break;
                case "firefox":
                    capabilities.merge(getFirefoxOptions(headless));
                    break;
                case "edge":
                    capabilities.merge(getEdgeOptions(headless));
                    break;
                default:
                    throw new RuntimeException("Remote browser not supported: " + browser);
            }
            return new RemoteWebDriver(new URL(gridUrl), capabilities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create remote driver: " + e.getMessage());
        }
    }

    private ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions", "--window-size=1920,1080");

        if (headless) options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", prefs);

        return options;
    }

    private FirefoxOptions getFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("--headless");
        options.addPreference("dom.webnotifications.enabled", false);
        return options;
    }

    private EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        if (headless) options.addArguments("--headless");
        return options;
    }

    private void configureDriver(WebDriver driver) {
        int timeout = ConfigReader.getInt("selenium.timeout");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(timeout));
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            System.out.println("Could not maximize window: " + e.getMessage());
        }
    }

    public WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new RuntimeException("WebDriver not initialized. Call createDriver() first.");
        }
        return driver;
    }

    public void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Error quitting driver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
                driverMap.remove(String.valueOf(Thread.currentThread().threadId()));
            }
        }
    }

    public void quitAllDrivers() {
        for (WebDriver driver : driverMap.values()) {
            try {
                if (driver != null) driver.quit();
            } catch (Exception e) {
                System.out.println("Error quitting driver: " + e.getMessage());
            }
        }
        driverMap.clear();
        driverThreadLocal.remove();
    }

    public boolean hasDriver() {
        return driverThreadLocal.get() != null;
    }

    public int getActiveDriverCount() {
        return driverMap.size();
    }
}
