package utils;

import constants.APIConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class APIUtils {

    public static Response registerUser(String baseUri, String email, String password) {

        TestLogger.info("Registering user with provided credentials - Email: {}", email);


    Response response = given()
            .baseUri(baseUri)
            .contentType(APIConstants.CONTENT_TYPE_FORM)
            .formParam("email", email)
            .formParam("password", password)
            .when()
            .post(APIConstants.REGISTER_USER);

        return response;
    }

    public static Response registerUserWithGeneratedCredentials(String baseUri) {

        String email = TestDataGeneratorUtils.generateEmail();
        String password = TestDataGeneratorUtils.generatePassword();

        TestLogger.info("Registering user with generated credentials - Email: {}", email);


        Response response = given()
                .baseUri(baseUri)
                .contentType(APIConstants.CONTENT_TYPE_FORM)
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post(APIConstants.REGISTER_USER);

        return response;
    }



    public static Response loginUser(String baseUri, String email, String password) {
        return given()
                .contentType(APIConstants.CONTENT_TYPE_FORM)
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post(APIConstants.LOGIN_USER);
    }



    // ========== RESPONSE VALIDATION METHODS ========== //

    public static void validateResponseMessage(Response response, String expectedMessage) {
        String actualMessage = response.jsonPath().getString(APIConstants.RESPONSE_KEY_MESSAGE);
        boolean messagesMatch = expectedMessage.equals(actualMessage);

        TestLogger.stepInfo("Expected Success message was: " +expectedMessage+ " Actual Success message is: " + actualMessage);
    }



}
