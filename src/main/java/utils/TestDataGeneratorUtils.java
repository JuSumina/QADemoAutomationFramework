package utils;

import java.util.Random;
import java.util.UUID;
import java.time.format.DateTimeFormatter;

public class TestDataGeneratorUtils {

    private static final Random random = new Random();
    private static final String[] DOMAINS = {
            "example.com", "test.com", "automation.com"
    };
    private static final String[] FIRST_NAMES = {
            "James", "Michael", "John", "Robert", "Elizabeth", "Emma", "Olivia", "Amelia"
    };
    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Davis", "Wilson", "Miller", "Taylor", "Anderson"
    };

    // ========== EMAIL GENERATION ========== //


     //Generates a unique email with timestamp
    public static String generateEmailWithTimestamp() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String domain = DOMAINS[random.nextInt(DOMAINS.length)];
        return "testuser" + timestamp + "@" + domain;
    }


    //Generates email with specific domain
    public static String generateEmailWithDomain(String domain) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return "testuser" + timestamp + "@" + domain;
    }


    //Generates email with name and year pattern
    public static String generateEmail() {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)].toLowerCase();
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)].toLowerCase();
        int year = generateYear(1950, 2025);
        String domain = DOMAINS[random.nextInt(DOMAINS.length)];

        // Various realistic patterns
        String[] patterns = {
                firstName + lastName + year,
                firstName + "." + lastName + year,
                firstName + "_" + lastName + year,
                firstName + lastName.charAt(0) + year,
                firstName.charAt(0) + lastName + year
        };

        String pattern = patterns[random.nextInt(patterns.length)];
        return pattern + "@" + domain;
    }



    // ========== PASSWORD GENERATION ==========


    // Generates valid password meeting requirements
    public static String generatePassword() {
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(8);
        return "Test" + timestamp + "!";
    }


    // Generates strong password with pattern
    public static String generateStrongPassword() {
        String[] patterns = {
                "SecurePass", "TestPassword", "ValidPass"
        };
        String pattern = patterns[random.nextInt(patterns.length)];
        String number = String.valueOf(random.nextInt(9999));
        String special = String.valueOf("!@#$%".charAt(random.nextInt(5)));

        return pattern + number + special;
    }


    // ========== NAME GENERATION ==========

    public static String generateFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }


    public static String generateLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }


    public static String generateFullName() {
        return generateFirstName() + " " + generateLastName();
    }



    // ========== HELPER METHODS ========== //

    public static int generateYear(int startYear, int endYear) {
        return startYear + random.nextInt(endYear - startYear + 1);
    }

    public static class TestDataSet {
        private String email;
        private String password;
        private String firstName;
        private String lastName;

        // Getters and setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }


        @Override
        public String toString() {
            return String.format("TestDataSet{email='%s', firstName='%s', lastName='%s'}",
                    email, firstName, lastName);
        }
    }

    public static TestDataSet generateTestDataSet(String scenarioType) {
        TestDataSet dataSet = new TestDataSet();

        switch (scenarioType.toLowerCase()) {
            case "createaccount":
                dataSet.setEmail(generateEmail());
                dataSet.setPassword(generatePassword());
                dataSet.setFirstName(generateFirstName());
                dataSet.setLastName(generateLastName());
                break;

            default:
                dataSet.setEmail(generateEmail());
                dataSet.setPassword(generatePassword());
        }

        return dataSet;
    }


}
