package hooks;

import context.ScenarioContext;
import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }



    // ========== BEFORE HOOKS ========== //
    @Before ("not e2e")
    public void preCondition(Scenario scenario) {
        // Initialize logger context for this scenario
        TestLogger.initializeContext(scenario.getName(), String.valueOf(Thread.currentThread().threadId()));
        TestLogger.scenarioStart(scenario.getName());

        // Log thread information for parallel execution debugging
        TestLogger.threadInfo();

        try {
            // Create WebDriver instance
            TestLogger.info("Initializing WebDriver for scenario: {}", scenario.getName());
            WebDriver driver = DriverFactory.getInstance().createDriverFromProperties();
            testContext.driver = driver;

            // Log driver configuration
            String browser = ConfigReader.getProperty("browser");
            boolean headless = ConfigReader.getBoolean("headless");
            TestLogger.driverInfo(browser, "latest", headless);

            // Initialize page objects after driver is created
            testContext.initializeAllPages();
            TestLogger.info("Page objects initialized successfully");

            // Navigate to application URL
            String url = ConfigReader.getProperty("url");
            if (url != null && !url.isEmpty()) {
                long startTime = System.currentTimeMillis();
                driver.get(url);
                long duration = System.currentTimeMillis() - startTime;

                TestLogger.browserAction("Navigate", url);
                TestLogger.performanceLog("Page navigation", duration);

                // Log slow page load warning
                int pageLoadThreshold = ConfigReader.getInt("page.load.threshold.ms");
                if (pageLoadThreshold > 0 && duration > pageLoadThreshold) {
                    TestLogger.slowActionWarning("Page navigation", duration, pageLoadThreshold);
                }

                TestLogger.environmentInfo("Test Environment", url);
            } else {
                TestLogger.error("Base URL is not defined in config.properties!");
                throw new RuntimeException("Base URL is not defined in config.properties!");
            }

            TestLogger.separator();

        } catch (Exception e) {
            TestLogger.testFailure(scenario.getName(), "Failed during test setup", e);
            // Take screenshot on setup failure
            if (testContext.driver != null) {
                String screenshotPath = ElementUtils.takeScreenshot("setup_failure_" + scenario.getName());
                if (screenshotPath != null) {
                    attachScreenshotToReport(scenario, screenshotPath);
                }
            }
            throw e;
        }
    }


    @Before ("e2e")
    public void preConditionE2E(Scenario scenario) {

        TestLogger.initializeContext(scenario.getName(), String.valueOf(Thread.currentThread().threadId()));
        TestLogger.scenarioStart(scenario.getName());

        TestLogger.threadInfo();

        TestLogger.info("Initializing WebDriver for scenario: {}", scenario.getName());
        WebDriver driver = DriverFactory.getInstance().createDriverFromProperties();
        testContext.driver = driver;

        cleanUpExistingTestData();
    }



    // ========== AFTER HOOKS ========== //

    @After ("not e2e")
    public void tearDown(Scenario scenario) {
        WebDriver driver = testContext.driver;

        try {
            if (scenario.isFailed() && driver != null) {
                TestLogger.testFailure(scenario.getName(), "Scenario execution failed");

                // Take screenshot using our utility method
                String screenshotPath = ElementUtils.takeScreenshot("failed_" + scenario.getName());

                // Attach screenshot to Cucumber report
                if (screenshotPath != null) {
                    attachScreenshotToReport(scenario, screenshotPath);
                    TestLogger.info("Screenshot attached to Cucumber report for failed scenario");
                } else {
                    // Fallback to direct screenshot if utility method fails
                    TestLogger.warn("Utility screenshot failed, trying direct screenshot capture");
                    try {
                        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                        scenario.attach(screenshot, "image/png", "Failed_" + scenario.getName());
                        TestLogger.info("Direct screenshot attached to Cucumber report");
                    } catch (Exception e) {
                        TestLogger.error("Failed to take any screenshot for scenario '{}': {}", scenario.getName(), e.getMessage());
                    }
                }

            } else if (!scenario.isFailed()) {
                TestLogger.info("Scenario '{}' completed successfully", scenario.getName());
            }

            // Log final scenario status
            String status = scenario.isFailed() ? "FAILED" : "PASSED";
            TestLogger.scenarioEnd(scenario.getName(), status);

        } catch (Exception e) {
            TestLogger.error("Error during teardown for scenario '{}': {}", scenario.getName(), e.getMessage());
        } finally {
            // Always quit driver and clean up
            if (driver != null) {
                try {
                    DriverFactory.getInstance().quitDriver();
                    TestLogger.browserAction("Quit Driver", "WebDriver closed successfully");
                } catch (Exception e) {
                    TestLogger.error("Error quitting WebDriver: {}", e.getMessage());
                }
            }

            // Clear logger context
            TestLogger.clearContext();
            TestLogger.separator();
        }
    }

    @After ("e2e")
    public void tearDownE2E(Scenario scenario) {
        WebDriver driver = testContext.driver;

        try {
            if (scenario.isFailed() && driver != null) {
                TestLogger.testFailure(scenario.getName(), "End-to-End execution failed");

                // Take screenshot using our utility method
                String screenshotPath = ElementUtils.takeScreenshot("failed_" + scenario.getName());

                // Attach screenshot to Cucumber report
                if (screenshotPath != null) {
                    attachScreenshotToReport(scenario, screenshotPath);
                    TestLogger.info("Screenshot attached to Cucumber report for failed scenario");
                } else {
                    // Fallback to direct screenshot if utility method fails
                    TestLogger.warn("Utility screenshot failed, trying direct screenshot capture");
                    try {
                        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                        scenario.attach(screenshot, "image/png", "Failed_" + scenario.getName());
                        TestLogger.info("Direct screenshot attached to Cucumber report");
                    } catch (Exception e) {
                        TestLogger.error("Failed to take any screenshot for scenario '{}': {}", scenario.getName(), e.getMessage());
                    }
                }

            } else if (!scenario.isFailed()) {
                TestLogger.info("Scenario '{}' completed successfully", scenario.getName());
            }

            // Log final scenario status
            String status = scenario.isFailed() ? "FAILED" : "PASSED";
            TestLogger.scenarioEnd(scenario.getName(), status);

        } catch (Exception e) {
            TestLogger.error("Error during teardown for scenario '{}': {}", scenario.getName(), e.getMessage());
        } finally {
            // Always quit driver and clean up
            if (driver != null) {
                try {
                    DriverFactory.getInstance().quitDriver();
                    TestLogger.browserAction("Quit Driver", "WebDriver closed successfully");
                } catch (Exception e) {
                    TestLogger.error("Error quitting WebDriver: {}", e.getMessage());
                }
            }

            cleanUpTestData(scenario);

            cleanUpWebDriver(scenario);

        }
    }



    // ========== HOOK METHODS ========== //

    //Attach screenshot to Cucumber report
    private void attachScreenshotToReport(Scenario scenario, String screenshotPath) {
        try {
            if (screenshotPath != null) {
                // Read screenshot file and attach to scenario
                byte[] screenshot = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(screenshotPath));
                scenario.attach(screenshot, "image/png", scenario.getName() + "_screenshot");
            }
        } catch (Exception e) {
            TestLogger.error("Failed to attach screenshot to report: {}", e.getMessage());
        }
    }


    private void cleanUpExistingTestData(){
        try{
            if (!DBUtils.testConnection()) {
                TestLogger.warn("Database connection test failed; skipping cleanup");
                return;
            }
            TestLogger.info("Pre-test cleanup: Database connection verified");
        } catch (Exception e) {
            TestLogger.warn("Pre-test cleanup failed: {}", e.getMessage());
        }
    }


    private void cleanUpTestData(Scenario scenario){
        TestLogger.stepInfo("Starting test data cleanup for scenario: {}", scenario.getName());
        try {
            ScenarioContext scenarioContext = testContext.getScenarioContext();

            // Try to delete by user ID first (more specific)
            String userId = scenarioContext.getString(ScenarioContext.USER_ID);
            if (userId != null && !userId.trim().isEmpty()) {
                boolean deletedById = DBUtils.deleteUserById(userId);
                if (deletedById) {
                    TestLogger.info("Successfully cleaned up user by ID: {}", userId);
                    return; // If deleted by ID, no need to try by email
                }
            }

            // Fallback: delete by email if ID deletion didn't work
            String email = scenarioContext.getString(ScenarioContext.EMAIL);
            if (email != null && !email.trim().isEmpty()) {
                boolean deletedByEmail = DBUtils.deleteUserByEmail(email);
                if (deletedByEmail) {
                    TestLogger.info("Successfully cleaned up user by email: {}", email);
                } else {
                    TestLogger.warn("No user found to clean up with email: {}", email);
                }
            }

        } catch (Exception e) {
            TestLogger.error("Test data cleanup failed: {}", e.getMessage());
            // Don't fail the test because of cleanup issues
        }

        // Always clear scenario context
        testContext.scenarioContext.clearContext();
        TestLogger.info("Test data cleanup completed");
    }

    private void cleanUpWebDriver(Scenario scenario) {
        if (testContext.driver != null) {
            try {
                DriverFactory.getInstance().quitDriver();
                TestLogger.info("WebDriver closed for scenario: {}", scenario.getName());
            } catch (Exception e) {
                TestLogger.warn("Error closing WebDriver: {}", e.getMessage());
            }
        }

        TestLogger.scenarioEnd(scenario.getName(), scenario.getStatus().toString());
        TestLogger.clearContext();
    }



    // ========== HOOK GLOBAL SETUP ========== //

    @Before(order = 0)
    public void globalSetup() {
        // Log test run information
        TestLogger.separator();
        TestLogger.testStart("Automation Test Suite");

        TestLogger.environmentInfo("Test Environment", ConfigReader.getProperty("url"));
        TestLogger.configInfo("Java Version", System.getProperty("java.version"));
        TestLogger.configInfo("OS", System.getProperty("os.name"));
        TestLogger.configInfo("Browser", ConfigReader.getProperty("browser"));
        TestLogger.configInfo("Headless Mode", String.valueOf(ConfigReader.getBoolean("headless")));

        TestLogger.separator();
    }


    @After(order = 0)
    public void globalTeardown() {
        // Log test run completion
        TestLogger.separator();
        TestLogger.testEnd("Automation Test Suite", "COMPLETED");
        TestLogger.separator();
    }
}
