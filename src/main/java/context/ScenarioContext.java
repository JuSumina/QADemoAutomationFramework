package context;

import utils.TestLogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScenarioContext {

    // Thread-safe map to store key-value pairs
    private final Map<String, Object> scenarioData = new ConcurrentHashMap<>();

    // Common context keys as constants to avoid typos
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String USER_ID = "userId";
    public static final String SESSION_TOKEN = "sessionToken";
    public static final String CURRENT_URL = "currentUrl";
    public static final String EXPECTED_MESSAGE = "expectedMessage";
    public static final String ACTUAL_MESSAGE = "actualMessage";
    public static final String TEST_DATA = "testData";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String RESPONSE_DATA = "responseData";
    public static final String PREVIOUS_PAGE = "previousPage";

    // ========== BASIC CRUD OPERATIONS ==========

    /**
     * Store a value in scenario context
     * @param key Key to store the value under
     * @param value Value to store
     */
    public void setContext(String key, Object value) {
        scenarioData.put(key, value);
        TestLogger.debug("Stored in context: {} = {}", key, value);
    }

    /**
     * Retrieve a value from scenario context
     * @param key Key to retrieve
     * @return Value associated with the key, or null if not found
     */
    public Object getContext(String key) {
        Object value = scenarioData.get(key);
        TestLogger.debug("Retrieved from context: {} = {}", key, value);
        return value;
    }

    /**
     * Retrieve a value with a specific type
     * @param key Key to retrieve
     * @param type Expected type of the value
     * @return Value cast to the specified type
     * @throws ClassCastException if the value cannot be cast to the specified type
     */
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

    /**
     * Check if a key exists in the context
     * @param key Key to check
     * @return true if key exists, false otherwise
     */
    public boolean containsKey(String key) {
        boolean exists = scenarioData.containsKey(key);
        TestLogger.debug("Context contains key '{}': {}", key, exists);
        return exists;
    }

    /**
     * Remove a key from the context
     * @param key Key to remove
     * @return The value that was removed, or null if key didn't exist
     */
    public Object removeContext(String key) {
        Object removedValue = scenarioData.remove(key);
        TestLogger.debug("Removed from context: {} = {}", key, removedValue);
        return removedValue;
    }

    // ========== COMMON DATA TYPE HELPERS ==========

    /**
     * Store a string value
     */
    public void setString(String key, String value) {
        setContext(key, value);
    }

    /**
     * Retrieve a string value
     */
    public String getString(String key) {
        return getContext(key, String.class);
    }

    /**
     * Store an integer value
     */
    public void setInteger(String key, Integer value) {
        setContext(key, value);
    }

    /**
     * Retrieve an integer value
     */
    public Integer getInteger(String key) {
        return getContext(key, Integer.class);
    }

    /**
     * Store a boolean value
     */
    public void setBoolean(String key, Boolean value) {
        setContext(key, value);
    }

    /**
     * Retrieve a boolean value
     */
    public Boolean getBoolean(String key) {
        return getContext(key, Boolean.class);
    }

    // ========== CONVENIENCE METHODS FOR COMMON TEST DATA ==========

    /**
     * Store username for the current scenario
     */
    public void setUsername(String username) {
        setString(USERNAME, username);
    }

    /**
     * Get username for the current scenario
     */
    public String getUsername() {
        return getString(USERNAME);
    }

    /**
     * Store password for the current scenario
     */
    public void setPassword(String password) {
        setString(PASSWORD, password);
    }

    /**
     * Get password for the current scenario
     */
    public String getPassword() {
        return getString(PASSWORD);
    }

    /**
     * Store user ID after login
     */
    public void setUserId(String userId) {
        setString(USER_ID, userId);
    }

    /**
     * Get user ID
     */
    public String getUserId() {
        return getString(USER_ID);
    }

    /**
     * Store session token
     */
    public void setSessionToken(String token) {
        setString(SESSION_TOKEN, token);
    }

    /**
     * Get session token
     */
    public String getSessionToken() {
        return getString(SESSION_TOKEN);
    }

    /**
     * Store expected message for validation
     */
    public void setExpectedMessage(String message) {
        setString(EXPECTED_MESSAGE, message);
    }

    /**
     * Get expected message
     */
    public String getExpectedMessage() {
        return getString(EXPECTED_MESSAGE);
    }

    /**
     * Store actual message for validation
     */
    public void setActualMessage(String message) {
        setString(ACTUAL_MESSAGE, message);
    }

    /**
     * Get actual message
     */
    public String getActualMessage() {
        return getString(ACTUAL_MESSAGE);
    }

    // ========== UTILITY METHODS ==========

    /**
     * Clear all context data
     */
    public void clearContext() {
        int size = scenarioData.size();
        scenarioData.clear();
        TestLogger.debug("Cleared {} items from scenario context", size);
    }

    /**
     * Get the size of the context
     */
    public int getContextSize() {
        return scenarioData.size();
    }

    /**
     * Get all keys in the context
     */
    public java.util.Set<String> getAllKeys() {
        return scenarioData.keySet();
    }

    /**
     * Print all context data for debugging
     */
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

    /**
     * Get context data as a formatted string
     */
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

    // ========== ADVANCED FEATURES ==========

    /**
     * Store complex objects (like test data maps, lists, etc.)
     */
    public void setTestData(String key, Map<String, Object> testData) {
        setContext(key, testData);
    }

    /**
     * Get test data as a map
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getTestData(String key) {
        return getContext(key, Map.class);
    }

    /**
     * Merge another map into context
     */
    public void mergeContext(Map<String, Object> additionalData) {
        if (additionalData != null) {
            scenarioData.putAll(additionalData);
            TestLogger.debug("Merged {} items into scenario context", additionalData.size());
        }
    }

}
