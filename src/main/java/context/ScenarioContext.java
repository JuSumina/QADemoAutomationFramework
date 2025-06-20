package context;

import io.restassured.response.Response;
import utils.TestLogger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScenarioContext {

    private static ScenarioContext instance;
    private final Map<String, Object> scenarioData = new ConcurrentHashMap<>();

    private ScenarioContext() {}

    public static ScenarioContext getInstance() {
        if (instance == null) {
            instance = new ScenarioContext();
        }
        return instance;
    }

    // ========== CONTEXT KEYS ========== //
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USER_ID = "userId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String GENERATED_TEST_DATA = "generatedTestData";
    public static final String SESSION_TOKEN = "sessionToken";
    public static final String CURRENT_URL = "currentUrl";
    public static final String EXPECTED_MESSAGE = "expectedMessage";
    public static final String ACTUAL_MESSAGE = "actualMessage";
    public static final String TEST_DATA = "testData";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String RESPONSE_DATA = "responseData";
    public static final String PREVIOUS_PAGE = "previousPage";
    public static final String API_RESPONSE = "apiResponse";



    // ========== BASIC CRUD OPERATIONS ========== //

    public void setContext(String key, Object value) {
        scenarioData.put(key, value);
        TestLogger.debug("Stored in context: {} = {}", key, value);
    }

    public Object getContext(String key) {
        Object value = scenarioData.get(key);
        TestLogger.debug("Retrieved from context: {} = {}", key, value);
        return value;
    }


    @SuppressWarnings("unchecked")
    public <T> T getContext(String key, Class<T> type) {
        Object value = getContext(key);
        if (value == null) {
            return null;
        }
        try {
            return (T) value;
        } catch (ClassCastException e) {
            TestLogger.error("Failed to cast context value '{}' to type {}: {}", key, type.getSimpleName(), e.getMessage());
            throw e;
        }
    }


    public boolean containsKey(String key) {
        boolean exists = scenarioData.containsKey(key);
        TestLogger.debug("Context contains key '{}': {}", key, exists);
        return exists;
    }


    public Object removeContext(String key) {
        Object removedValue = scenarioData.remove(key);
        TestLogger.debug("Removed from context: {} = {}", key, removedValue);
        return removedValue;
    }



    // ========== COMMON DATA TYPE HELPERS ========== //


    public void setString(String key, String value) {
        setContext(key, value);
    }


    public String getString(String key) {
        return getContext(key, String.class);
    }


    public void setInteger(String key, Integer value) {
        setContext(key, value);
    }


    public Integer getInteger(String key) {
        return getContext(key, Integer.class);
    }


    public void setBoolean(String key, Boolean value) {
        setContext(key, value);
    }


    public Boolean getBoolean(String key) {
        return getContext(key, Boolean.class);
    }

    // ========== CONVENIENCE METHODS FOR COMMON TEST DATA ========== //


    public void setUsername(String username) {
        setString(USERNAME, username);
    }


    public String getUsername() {
        return getString(USERNAME);
    }


    public void setPassword(String password) {
        setString(PASSWORD, password);
    }


    public String getPassword() {
        return getString(PASSWORD);
    }


    public void setUserId(String userId) {
        setString(USER_ID, userId);
    }


    public String getUserId() {
        return getString(USER_ID);
    }


    public void setSessionToken(String token) {
        setString(SESSION_TOKEN, token);
    }


    public String getSessionToken() {
        return getString(SESSION_TOKEN);
    }


    public void setExpectedMessage(String message) {
        setString(EXPECTED_MESSAGE, message);
    }


    public String getExpectedMessage() {
        return getString(EXPECTED_MESSAGE);
    }


    public void setActualMessage(String message) {
        setString(ACTUAL_MESSAGE, message);
    }


    public String getActualMessage() {
        return getString(ACTUAL_MESSAGE);
    }



    // ========== API METHODS ========== //

    public void setApiResponse(Response response) {
        setContext(API_RESPONSE, response);
    }

    public Response getApiResponse() {
        return getContext(API_RESPONSE, Response.class);
    }



    // ========== UTILITY METHODS ========== //


    public void clearContext() {
        int size = scenarioData.size();
        scenarioData.clear();
        TestLogger.debug("Cleared {} items from scenario context", size);
    }


    public int getContextSize() {
        return scenarioData.size();
    }


    public java.util.Set<String> getAllKeys() {
        return scenarioData.keySet();
    }


    public void printContext() {
        TestLogger.debug("=== SCENARIO CONTEXT DEBUG ===");
        if (scenarioData.isEmpty()) {
            TestLogger.debug("Context is empty");
        } else {
            scenarioData.forEach((key, value) ->
                    TestLogger.debug("  {} = {}", key, value)
            );
        }
        TestLogger.debug("=== END CONTEXT DEBUG ===");
    }


    public String getContextAsString() {
        if (scenarioData.isEmpty()) {
            return "Context is empty";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ScenarioContext Data:\n");
        scenarioData.forEach((key, value) ->
                sb.append(String.format("  %s = %s\n", key, value))
        );
        return sb.toString();
    }



    // ========== ADVANCED FEATURES ========== //


    public void setTestData(String key, Map<String, Object> testData) {
        setContext(key, testData);
    }


    @SuppressWarnings("unchecked")
    public Map<String, Object> getTestData(String key) {
        return getContext(key, Map.class);
    }


    public void mergeContext(Map<String, Object> additionalData) {
        if (additionalData != null) {
            scenarioData.putAll(additionalData);
            TestLogger.debug("Merged {} items into scenario context", additionalData.size());
        }
    }

}
