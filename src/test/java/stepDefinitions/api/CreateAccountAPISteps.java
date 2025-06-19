package stepDefinitions.api;

import context.TestContext;
import io.restassured.response.Response;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.APIUtils;
import utils.TestLogger;

public class CreateAccountAPISteps {

    private TestContext testContext;

    Response response;

    public CreateAccountAPISteps (TestContext testContext) {
        this.testContext = testContext;
    }


    @When ("a user registers with automatically generated valid credentials")
    public void a_user_registers_with_automatically_generated_valid_credentials(){

        Response response = APIUtils.registerUserWithGeneratedCredentials(testContext.getApiBaseUri());
        this.response = response;

        TestLogger.stepInfo("Sent POST request to create user account");
    }

    @Then ("the response status code should be {int}")
    public void verify_response_status_code_after_user_creation(Integer responseCode){

        response.then().statusCode(responseCode);

        TestLogger.stepInfo("Response code is " + response.statusCode());
        TestLogger.assertion("Response code should be 200", true);

    }

    @And ("the response should contain a success message")
    public void verify_success_message_after_user_creation() {



    }

    @And ("a userId should be present in the response body")
    public void a_userId_should_be_present_in_the_response_body() {

    }
}
