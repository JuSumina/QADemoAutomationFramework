package stepDefinitions.api;

import constants.APIConstants;
import context.ScenarioContext;
import context.TestContext;
import io.restassured.response.Response;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.APIUtils;
import utils.TestLogger;

public class CreateAccountAPISteps {

    private Response response;
    private TestContext testContext;


    private final ScenarioContext scenarioContext = ScenarioContext.getInstance();

    public CreateAccountAPISteps (TestContext testContext) {
        this.testContext = testContext;
    }


    @When ("user creates an account with auto-generated valid credentials")
    public void a_user_creates_account_with_generated_valid_credentials(){

        TestLogger.stepInfo("Creating account with generated credentials");

        response = APIUtils.registerUserWithGeneratedCredentials(testContext.getApiBaseUri());
    }

    @Then ("the response status code should be {int}")
    public void verify_response_status_code_after_user_creation(int expectedStatusCode){

        APIUtils.validateStatusCode(response, expectedStatusCode);
    }

    @And ("the response should contain a success message")
    public void verify_success_message_after_user_creation() {

        String messageRegisterSuccess = APIConstants.MESSAGE_REGISTER_SUCCESS;

        APIUtils.validateResponseMessage(response, messageRegisterSuccess);

        TestLogger.stepInfo("Register success message is " + messageRegisterSuccess);
    }

    @And ("id key should be present in the response body")
    public void id_should_be_present_in_the_response_body() {

        String fieldKey = APIConstants.RESPONSE_KEY_USER_ID;

        APIUtils.validateFieldKeyExists(response, fieldKey);

        TestLogger.stepInfo("id key is present in the response body");

    }
}
