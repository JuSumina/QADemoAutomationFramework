Feature: Login Functionality

@smoke
  Scenario: Valid User Login
    Given user is on the login page
    When user enters valid email and valid password under User Login
    And clicks on login button
    Then user is logged in successfully
    And gets redirected to User Dashboard