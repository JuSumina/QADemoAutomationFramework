Feature: Login Functionality

@smoke @login
  Scenario: Valid User Login - Both Credentials Valid
    Given user is on the login page
    When user enters valid email and valid password under User Login
    And clicks on login button
    Then user is logged in successfully
    And gets redirected to User Dashboard

  @smoke @login
  Scenario: Invalid User Login - Both Credentials Invalid
    When user enters invalid email and invalid password under User Login
    And clicks on login button
    Then user sees an error message
    And user remains on the login page