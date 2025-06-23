package stepDefinitions.e2e;

import context.ScenarioContext;
import context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import utils.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateAccountE2ESteps {

    private Response response;
    private TestContext testContext;


    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();

    public CreateAccountE2ESteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given ("user is on the Create Account page")
    public void user_is_on_the_Create_Account_page(){
        testContext.getLoginPage().clickOnCreateAccountLink();

        TestLogger.stepInfo("Clicked on Create Account link");

        String createAccountHeaderText = testContext.getCreateAccountPage().getCreateAccountHeaderText();
        assertEquals("Create Account", createAccountHeaderText);

        TestLogger.stepInfo("Successfully redirected to Create Account page");
    }


    @When ("user creates an account with auto-generated valid test data")
    public void user_creates_an_account_with_generated_valid_test_data() {
        TestLogger.stepInfo("Creating user account with auto-generated valid test data");

        String email = TestDataGeneratorUtils.generateEmail();
        String password = TestDataGeneratorUtils.generatePassword();

        scenarioContext.setString(ScenarioContext.EMAIL, email);
        scenarioContext.setString(ScenarioContext.PASSWORD, password);

        TestLogger.info("Generated test data - Email: {}", email);

        testContext.getCreateAccountPage().enterAccountCredentials(email, password);
        testContext.getCreateAccountPage().clickonCreateAccountBtn();

        WebDriver driver = DriverFactory.getDriver();
        WaitUtils.waitForVisibility(driver, testContext.getCreateAccountPage().successPopup);
        TestLogger.stepInfo("Success popup appeared");
    }


    @And ("user saves the email to the scenario context")
    public void user_saves_the_email_to_the_scenario_context(){
        TestLogger.stepInfo("Verifying email is saved in scenario context");

        String email = scenarioContext.getString(ScenarioContext.EMAIL);
        Assertions.assertNotNull(email, "Email should be saved in scenario context");
        Assertions.assertFalse(email.trim().isEmpty(), "Email should not be empty");

        TestLogger.info("Email confirmed in context: {}", email);
    }


    @Then ("user retrieves the user ID via an API call")
    public void user_retrieves_the_user_ID_via_an_API_call(){
        TestLogger.stepInfo("Getting user ID via API call using APIUtils");

        String email = scenarioContext.getString(ScenarioContext.EMAIL);
        String baseUri = testContext.getApiBaseUri();

        Response response = APIUtils.getUserByEmail(baseUri, email);

        APIUtils.validateStatusCode(response, 200);

        String userId = APIUtils.extractUserId(response);
        scenarioContext.setString(ScenarioContext.USER_ID, userId);

        APIUtils.validateFieldKeyValue(response, "data.email", email);

        TestLogger.info("User ID retrieved and validated: {}", userId);

    }


    @And ("saves the user ID to the scenario context")
    public void saves_the_user_ID_to_the_scenario_context(){
        TestLogger.stepInfo("Verifying user ID is saved in scenario context");

        String userId = scenarioContext.getString(ScenarioContext.USER_ID);

        Assertions.assertNotNull(userId, "User ID should be saved in scenario context");
        Assertions.assertFalse(userId.trim().isEmpty(), "User ID should not be empty");

        TestLogger.info("User ID confirmed in context: {}", userId);
    }


    @And ("verifies the user exists in the database with the correct email")
    public void verifies_the_user_exists_in_the_database_with_the_correct_email(){
        TestLogger.stepInfo("Verifying user exists in database with correct email");

        String userId = scenarioContext.getString(ScenarioContext.USER_ID);
        String email = scenarioContext.getString(ScenarioContext.EMAIL);

        DBUtils.verifyUserEmail(userId, email);
        TestLogger.assertion("Database verification successful", true);

    }


    @And ("user logs in using the newly created credentials")
    public void user_logs_in_using_the_newly_created_credentials(){
        TestLogger.stepInfo("Logging in with new user auto-generated valid credentials");

        String email = scenarioContext.getString(ScenarioContext.EMAIL);
        String password = scenarioContext.getString(ScenarioContext.PASSWORD);

        TestLogger.stepInfo("Clicking on User Login Toggle");
        testContext.getLoginPage().clickOnUserToggle();

        TestLogger.stepInfo("Entering valid credentials saved in ScenarioContext");
        testContext.getLoginPage().enterEmail(email);
        testContext.getLoginPage().enterPassword(password);

        testContext.getLoginPage().clickOnLoginBtn();

        TestLogger.info("New user successfully logged in");
    }


    @And ("user sees the User Dashboard page")
    public void user_sees_the_User_Dashboard_page(){
        TestLogger.stepInfo("Redirecting to User Dashboard");

        WebDriver driver = DriverFactory.getDriver();
        WaitUtils.waitForInvisibility(driver, testContext.getCreateAccountPage().successPopup);

        boolean urlContainsIndex = WaitUtils.waitForUrlContains(testContext.driver, "dashboard");

        String currentUrl = testContext.driver.getCurrentUrl();
        assertTrue(urlContainsIndex,
                "User should get redirected to Login page. Current URL: " + currentUrl);
        TestLogger.info("Current URL: {}", currentUrl);

    }
}
