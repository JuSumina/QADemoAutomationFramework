package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static Properties properties;

    // Static block to load properties when the class is first loaded
    static {
        try {
            String filePath = "src/test/resources/config/config.properties";
            FileInputStream fis = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(fis);
            fis.close();
            logger.info("Configuration properties loaded successfully from: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to load config.properties file: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in config.properties. Returning null.", key);
        }
        return value;
    }

    public static boolean getBoolean(String key) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }

    public static int getInt(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.error("Property '{}' value '{}' is not a valid integer. Returning 0.", key, value);
            return 0;
        }
    }
}
