Feature: Create Account API Functionality

  @smoke @createaccountapi
  Scenario: Create Account with Valid Credentials Using API Call
    When user creates an account with auto-generated valid credentials
    Then the response status code should be 201
    And the response should contain a success message
    And id key should be present in the response body