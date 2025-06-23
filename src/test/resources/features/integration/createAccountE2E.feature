Feature: Complete User Registration Flow with API and Database Verification

  @e2e
  Scenario: End-to-end user account creation and login with API and DB validation
    Given user is on the Create Account page
    When user creates an account with auto-generated valid test data
    And user saves the email to the scenario context
    Then user retrieves the user ID via an API call
    And saves the user ID to the scenario context
    And verifies the user exists in the database with the correct email
    And user logs in using the newly created credentials
    And user sees the User Dashboard page