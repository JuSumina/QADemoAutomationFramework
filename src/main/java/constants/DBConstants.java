package constants;

public class DBConstants {

    private DBConstants() {

    }

    public static final String DB_URL = "jdbc:sqlite:D:/QADemo/my-website/users.db";


    // ========== DB QUERIES ========== //
    public static final String QUERY_USER_COUNT_BY_ID = "SELECT COUNT(*) as user_count, email FROM users WHERE id = ? GROUP BY email";
    public static final String QUERY_USER_EXISTS_BY_ID = "SELECT COUNT(*) as count FROM users WHERE id = ?";

    public static final String QUERY_USER_EXISTS_BY_EMAIL = "SELECT COUNT(*) as count FROM users WHERE email = ?";

    public static final String QUERY_DELETE_USER_BY_EMAIL = "DELETE FROM users WHERE email = ?";

    public static final String QUERY_DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";




    // ========== COLUMN NAMES ========== //
    public static final String COLUMN_USER_COUNT = "user_count";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_COUNT = "count";
    public static final String COLUMN_ID = "id";



    // ========== ERROR MESSAGES ========== //

    public static final String ERROR_DB_URL_NOT_CONFIGURED = "Database URL not configured in properties file";
    public static final String ERROR_DB_CONNECTION_FAILED = "Failed to connect to database";
    public static final String ERROR_USER_NOT_FOUND = "No user found in database with ID: ";
    public static final String ERROR_MULTIPLE_USERS_FOUND = "Expected exactly 1 user with ID %s, but found %d";
    public static final String ERROR_EMAIL_MISMATCH = "Email mismatch. Expected email was: %s, DB email value is: %s";
    public static final String ERROR_DB_QUERY_FAILED = "Database query failed: ";
}
