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

        TestLogger.apiCall("POST", APIConstants.REGISTER_USER, response.getStatusCode());

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

        TestLogger.apiCall("POST", APIConstants.REGISTER_USER, response.getStatusCode());

        return response;
    }



    public static Response loginUser(String baseUri, String email, String password) {

        TestLogger.info("Logging in user - Email: {}", email);

        Response response =  given()
                .baseUri(baseUri)
                .contentType(APIConstants.CONTENT_TYPE_FORM)
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post(APIConstants.LOGIN_USER);

        TestLogger.apiCall("POST", APIConstants.LOGIN_USER, response.getStatusCode());

        return response;
    }



    // ========== RESPONSE VALIDATION METHODS ========== //

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        TestLogger.assertion("Status code validation", expectedStatusCode, actualStatusCode);
        response.then().statusCode(expectedStatusCode);
    }

    public static void validateResponseMessage(Response response, String expectedMessage) {
        String actualMessage = response.jsonPath().getString(APIConstants.RESPONSE_KEY_MESSAGE);
        boolean messagesMatch = expectedMessage.equals(actualMessage);

        TestLogger.assertion("Response message validation - Expected: '{}', Actual: '{}'",
                expectedMessage, actualMessage);

        if(!messagesMatch) {
            throw new AssertionError("Expected message was:'" + expectedMessage +
            "', but actual message is: '" + actualMessage + "'");
        }
    }

    public static void validateFieldKeyExists(Response response, String fieldKey) {
        String fieldValue = response.jsonPath().getString(fieldKey);
        boolean fieldExists = fieldValue != null && !fieldValue.trim().isEmpty();

        String description = "Field '" + fieldKey + "' exists in response";
        TestLogger.assertion(description, fieldExists);

        if(!fieldExists) {
            throw new AssertionError ("Field '" + fieldKey + "' not found or empty in response");
        }
    }

    public static void validateFieldKeyValue (Response response, String fieldKey, String expectedFieldValue) {

        String actualFieldValue = response.jsonPath().getString(fieldKey);
        boolean valueMatch = expectedFieldValue.equals(actualFieldValue);

        String description = String.format("Field '%s' value validation", fieldKey);
        TestLogger.assertion(description, expectedFieldValue, actualFieldValue);

        if(!valueMatch) {
            throw new AssertionError("Field '" + fieldKey + "' expected to be: '" + expectedFieldValue +
                    "', but actually is: '" + actualFieldValue + "'");
        }
    }










}
