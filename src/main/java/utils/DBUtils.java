package utils;

import constants.DBConstants;
import org.openqa.selenium.support.PageFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

    private DBUtils() {

    }

    public static Connection getConnection() throws SQLException{
        try {
            return DriverManager.getConnection(DBConstants.DB_URL);
        } catch (SQLException e) {
            TestLogger.error("{}: {}", DBConstants.ERROR_DB_CONNECTION_FAILED, e.getMessage());
            throw e;
        }
    }

    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            boolean isValid = connection !=null && !connection.isClosed();
            TestLogger.info("Database connection test: {}", isValid ? "SUCCESS" : "FAILED");
            return isValid;
        } catch (SQLException e) {
            TestLogger.error("Database connection test failed: {}", e.getMessage());
            return false;
        }
    }

    public static void verifyUserEmail (String userId, String expectedEmail) {
        TestLogger.info("Verifying user ID {} has email {} in database", userId, expectedEmail);

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DBConstants.QUERY_USER_COUNT_BY_ID)) {
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                int userCount = resultSet.getInt(DBConstants.COLUMN_USER_COUNT);
                String dbEmail = resultSet.getString(DBConstants.COLUMN_EMAIL);

                if (userCount != 1) {
                    String errorMsg = String.format(DBConstants.ERROR_MULTIPLE_USERS_FOUND, userId, userCount);
                    TestLogger.error(errorMsg);
                    throw new AssertionError(errorMsg);
                }

                if (!expectedEmail.equals(dbEmail)) {
                    String errorMsg = String.format(DBConstants.ERROR_EMAIL_MISMATCH, expectedEmail, dbEmail);
                    TestLogger.error(errorMsg);
                    throw new AssertionError(errorMsg);
                }

                TestLogger.info("Database verification successful - User ID: {}, Email: {}", userId, dbEmail);
            } else {
                String errorMsg = DBConstants.ERROR_USER_NOT_FOUND + userId;
                TestLogger.error(errorMsg);
                throw new AssertionError(errorMsg);
            }
        } catch (SQLException e) {
            TestLogger.error("Database verification failed: {}", e.getMessage());
            throw new AssertionError(DBConstants.ERROR_DB_QUERY_FAILED + e.getMessage(), e);
        }
    }

    public static boolean userExists(String userId) {
        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(DBConstants.QUERY_USER_EXISTS_BY_ID)) {

            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                int count = resultSet.getInt(DBConstants.COLUMN_COUNT);
                TestLogger.debug("User ID {} exists check: {}", userId, count > 0);
                return count > 0;
            }
        } catch (SQLException e) {
            TestLogger.error("Failed to check if user exists: {}", e.getMessage());
        }

        return false;
    }


    public static boolean deleteUserByEmail(String email) {
        TestLogger.info("Deleting user with email: {}", email);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DBConstants.QUERY_DELETE_USER_BY_EMAIL)) {

            statement.setString(1, email);
            int rowsAffected = statement.executeUpdate();

            boolean deleted = rowsAffected > 0;
            TestLogger.info("User deletion by email {}: {} (rows affected: {})",
                    email, deleted ? "SUCCESS" : "NO USER FOUND", rowsAffected);
            return deleted;

        } catch (SQLException e) {
            TestLogger.error("Failed to delete user by email {}: {}", email, e.getMessage());
            return false;
        }
    }


    public static boolean deleteUserById(String userId) {
        TestLogger.info("Deleting user with ID: {}", userId);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DBConstants.QUERY_DELETE_USER_BY_ID)) {

            statement.setString(1, userId);
            int rowsAffected = statement.executeUpdate();

            boolean deleted = rowsAffected > 0;
            TestLogger.info("User deletion by ID {}: {} (rows affected: {})",
                    userId, deleted ? "SUCCESS" : "NO USER FOUND", rowsAffected);
            return deleted;

        } catch (SQLException e) {
            TestLogger.error("Failed to delete user by ID {}: {}", userId, e.getMessage());
            return false;
        }
    }

}
