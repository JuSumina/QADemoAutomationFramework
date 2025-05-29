package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestLogger {

    private static final Logger logger = LoggerFactory.getLogger(TestLogger.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TestLogger() {

    }

    public static void initializeContext(String scenarioName, String threadId) {
        MDC.put("scenario", scenarioName);
        MDC.put("thread", threadId);
        MDC.put("timestamp", LocalDateTime.now().format(formatter));
    }

    public static void clearContext() {
        MDC.clear();
    }

    // ========== INFO LEVEL METHODS ==========

    public static void info(String message) {
        logger.info(message);
    }

    public static void info(String message, Object... args) {
        logger.info(message, args);
    }

    public static void stepInfo(String stepDescription) {
        logger.info("STEP: {}", stepDescription);
    }

    public static void actionInfo(String action, String element) {
        logger.info("ACTION: {} on element: {}", action, element);
    }

    // ========== DEBUG LEVEL METHODS ==========

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    public static void elementDebug(String action, String elementInfo) {
        logger.debug("ELEMENT: {} - {}", action, elementInfo);
    }

    // ========== WARN LEVEL METHODS ==========

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    public static void retryWarn(String action, int attempt, int maxAttempts) {
        logger.warn("RETRY: {} - Attempt {}/{}", action, attempt, maxAttempts);
    }

    // ========== ERROR LEVEL METHODS ==========

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Object... args) {
        logger.error(message, args);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void testFailure(String testName, String reason) {
        logger.error("TEST FAILED: {} - Reason: {}", testName, reason);
    }

    public static void testFailure(String testName, String reason, Throwable throwable) {
        logger.error("TEST FAILED: {} - Reason: {}", testName, reason, throwable);
    }

    // ========== SPECIALIZED LOGGING METHODS ==========

    public static void browserAction(String action, String details) {
        logger.info("BROWSER: {} - {}", action, details);
    }

    public static void apiCall(String method, String endpoint, int statusCode) {
        logger.info("API: {} {} - Status: {}", method, endpoint, statusCode);
    }

    public static void assertion(String description, boolean result) {
        if (result) {
            logger.info("ASSERTION PASSED: {}", description);
        } else {
            logger.error("ASSERTION FAILED: {}", description);
        }
    }

    public static void testStart(String testName) {
        logger.info("==================== TEST STARTED: {} ====================", testName);
    }

    public static void testEnd(String testName, String status) {
        logger.info("==================== TEST ENDED: {} - Status: {} ====================", testName, status);
    }

    public static void scenarioStart(String scenarioName) {
        logger.info(">>> SCENARIO STARTED: {}", scenarioName);
    }

    public static void scenarioEnd(String scenarioName, String status) {
        logger.info("<<< SCENARIO ENDED: {} - Status: {}", scenarioName, status);
    }

    // ========== CONFIGURATION AND SETUP LOGGING ==========

    public static void configInfo(String configName, String value) {
        logger.info("CONFIG: {} = {}", configName, value);
    }

    public static void driverInfo(String browserName, String version, boolean headless) {
        logger.info("DRIVER: Browser={}, Version={}, Headless={}", browserName, version, headless);
    }

    public static void environmentInfo(String environment, String baseUrl) {
        logger.info("ENVIRONMENT: {} - Base URL: {}", environment, baseUrl);
    }

    // ========== PERFORMANCE LOGGING ==========

    public static void performanceLog(String action, long durationMs) {
        logger.info("PERFORMANCE: {} completed in {}ms", action, durationMs);
    }

    public static void slowActionWarning(String action, long durationMs, long thresholdMs) {
        logger.warn("SLOW ACTION: {} took {}ms (threshold: {}ms)", action, durationMs, thresholdMs);
    }

    // ========== UTILITY METHODS ==========


     //Log with custom prefix
    public static void logWithPrefix(String prefix, String message) {
        logger.info("{}: {}", prefix, message);
    }


    // Log separator for better readability
    public static void separator() {
        logger.info("================================================================");
    }


    // Log current thread information
    public static void threadInfo() {
        logger.debug("Thread Info: {} - ID: {}",
                Thread.currentThread().getName(),
                Thread.currentThread().threadId());
    }


}
