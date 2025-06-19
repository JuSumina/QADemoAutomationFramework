Feature: Create Account API Functionality

  @smoke @createaccountapi
  Scenario: Create Account with Valid Credentials Using API Call
    When a user registers with automatically generated valid credentials
    Then the response status code should be 200
    And the response should contain a success message
    And a userId should be present in the response body