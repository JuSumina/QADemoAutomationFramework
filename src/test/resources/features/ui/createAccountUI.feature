Feature: Create Account Functionality

  Background: Navigate to Create Account Page
    Given user is on the login page
    When user clicks on Create Account link
    Then user should get redirected to Create Account page

    @smoke @createaccount
    Scenario: Create Account and Validate Success Popup
        When user enters valid email and valid password
        And user clicks on Create Account button
        Then Success popup should appear
        And popup should display 'Your account has been created successfully!' success message
        And user should be able to click on OK button to close the popup