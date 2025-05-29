package context;

import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.UserDashboardPage;
import utils.DriverFactory;
import utils.TestLogger;

public class TestContext {

    public WebDriver driver;
    public ScenarioContext scenarioContext;

    private LoginPage loginPage;
    private UserDashboardPage userDashboardPage;

    public TestContext() {
        this.scenarioContext = new ScenarioContext();
    }

    public void initializeAllPages() {
        validateDriver();
        TestLogger.info("Initializing ALL page objects...");

        long startTime = System.currentTimeMillis();

        this.loginPage = new LoginPage(driver);
        this.userDashboardPage = new UserDashboardPage(driver);

        long duration = System.currentTimeMillis() - startTime;
        TestLogger.performanceLog("All pages initialization", duration);
        TestLogger.info("All page objects initialized successfully");
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            validateDriver();
            loginPage = new LoginPage(driver);
            TestLogger.debug("Lazy initialized LoginPage");
        }
        return loginPage;
    }

    public UserDashboardPage getUserDashboardPage() {
        if (userDashboardPage == null) {
            validateDriver();
            userDashboardPage = new UserDashboardPage(driver);
            TestLogger.debug("Lazy initialized UserDashboardPage");
        }
        return userDashboardPage;
    }


    private void validateDriver() {
        if (driver == null) {
            throw new RuntimeException("WebDriver must be initialized before creating page objects");
        }
    }
}
