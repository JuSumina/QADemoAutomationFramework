package context;

import org.openqa.selenium.WebDriver;
import pages.CreateAccountPage;
import pages.LoginPage;
import pages.UserDashboardPage;
import utils.DriverFactory;
import utils.TestLogger;
import utils.ConfigReader;

public class TestContext {

    public WebDriver driver;
    public ScenarioContext scenarioContext;
    private final String apiBaseUri = ConfigReader.getProperty("baseURI");

    private LoginPage loginPage;
    private UserDashboardPage userDashboardPage;
    private CreateAccountPage createAccountPage;


    public TestContext() {
        this.scenarioContext = ScenarioContext.getInstance();
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }

    public void initializeAllPages() {
        validateDriver();
        TestLogger.info("Initializing ALL page objects...");

        long startTime = System.currentTimeMillis();

        this.loginPage = new LoginPage(driver);
        this.userDashboardPage = new UserDashboardPage(driver);
        this.createAccountPage = new CreateAccountPage(driver);

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

    public CreateAccountPage getCreateAccountPage() {
        if (createAccountPage == null) {
            validateDriver();
            createAccountPage = new CreateAccountPage(driver);
            TestLogger.debug("Lazy initialized LoginPage");
        }
        return createAccountPage;
    }


    private void validateDriver() {
        if (driver == null) {
            throw new RuntimeException("WebDriver must be initialized before creating page objects");
        }
    }

    public String getApiBaseUri() {
        return apiBaseUri;
    }
}
