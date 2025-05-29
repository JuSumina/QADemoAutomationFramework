package stepDefinitions;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import utils.TestLogger;
import utils.ConfigReader;
import utils.WaitUtils;

import static org.junit.jupiter.api.Assertions.*;
public class LoginSteps{

    private TestContext testContext;

    public LoginSteps(TestContext testContext) {
        this.testContext = testContext;
    }


    // ========== VALID LOGIN ========== //

    @Given("user is on the login page")
    public void user_is_on_the_login_page() {

        TestLogger.stepInfo("Browser opened and application launched");
        boolean isLoginPageDisplayed = testContext.getLoginPage().isLoginPageDisplayed();
        TestLogger.assertion("Login page is displayed after launching application", isLoginPageDisplayed);
        assertTrue(isLoginPageDisplayed, "Login page should be displayed after launching application");
    }

    @When("user enters valid email and valid password under User Login")
    public void user_enters_valid_email_and_valid_password_under_User_Login() {

        String userHeaderText = testContext.getLoginPage().getUserLoginHeaderText();
        assertEquals("User Login", userHeaderText);

        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");

        testContext.getLoginPage().enterUsername(username);
        testContext.getLoginPage().enterPassword(password);

        TestLogger.stepInfo("Valid credentials entered for user: " + username);
    }

    @And("clicks on login button")
    public void clicks_on_login_button() {
        testContext.getLoginPage().clickOnLoginBtn();
        TestLogger.stepInfo("Login button clicked");
    }

    @Then("user is logged in successfully")
    public void user_is_logged_in_successfully() {

        boolean urlContainsDashboard = WaitUtils.waitForUrlContains(testContext.driver, "dashboard");

        String currentUrl = testContext.driver.getCurrentUrl();
        TestLogger.stepInfo("Current URL after login: " + currentUrl);

        assertTrue(urlContainsDashboard,
                "User should be redirected to dashboard page. Current URL: " + currentUrl);

        TestLogger.assertion("User logged in successfully", urlContainsDashboard);

    }

    @And("gets redirected to User Dashboard")
    public void user_gets_redirected_to_User_Dashboard() {

        String userDashboardHeaderText = testContext.getUserDashboardPage().getUserDashboardHeaderText();
        assertEquals("User Dashboard", userDashboardHeaderText);

        TestLogger.stepInfo("Successfully redirected to User Dashboard");
    }

    // ========== INVALID LOGIN ========== //

    @When("user enters invalid email and invalid password under User Login")
    public void user_enters_invalid_email_and_invalid_password_under_User_Login() {

        String userHeaderText = testContext.getLoginPage().getUserLoginHeaderText();
        assertEquals("User Login", userHeaderText);

        String invalidUsername = ConfigReader.getProperty("invalid.username");
        String invalidPassword = ConfigReader.getProperty("invalid.password");

        testContext.getLoginPage().enterUsername(invalidUsername);
        testContext.getLoginPage().enterPassword(invalidPassword);

        TestLogger.stepInfo("Invalid credentials entered for user: " + invalidUsername);
    }

    @Then("user sees an error message")
    public void user_sees_an_error_message(){

        String userLoginErrorText = testContext.getLoginPage().getUserLoginErrorMessage();
        assertEquals("Login failed. Your email or password is incorrect.", userLoginErrorText);

        TestLogger.stepInfo("Login Error Message for user: " + userLoginErrorText);
    }

    @And("user remains on the login page")
    public void user_remains_on_the_login_page() {

        boolean urlContainsIndex = WaitUtils.waitForUrlContains(testContext.driver, "index");

        String currentUrl = testContext.driver.getCurrentUrl();
        TestLogger.stepInfo("Current URL: " + currentUrl);

        assertTrue(urlContainsIndex,
                "User should stay on Login page. Current URL: " + currentUrl);

        boolean isLoginPageDisplayed = testContext.getLoginPage().isLoginPageDisplayed();
        assertTrue(isLoginPageDisplayed, "Login page should still be displayed after failed login");

        TestLogger.stepInfo("User remained on login page after invalid login attempt");
        TestLogger.assertion("User remains on login page after failed login", true);
    }

}
