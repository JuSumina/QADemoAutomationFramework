package stepDefinitions.ui;

import context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.TestDataGeneratorUtils;
import utils.TestLogger;
import utils.WaitUtils;
import utils.DriverFactory;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateAccountUISteps {

    private TestContext testContext;

    public CreateAccountUISteps (TestContext testContext) {
        this.testContext = testContext;
    }

    // ========== BACKGROUND STEPS ========== //

    @When("user clicks on Create Account link")
    public void user_clicks_on_Create_Account_link() {
        testContext.getLoginPage().clickOnCreateAccountLink();

        TestLogger.stepInfo("Clicked on Create Account link");
    }

    @Then ("user should get redirected to Create Account page")
    public void user_should_get_redirected_to_Create_Account_page() {
        String createAccountHeaderText = testContext.getCreateAccountPage().getCreateAccountHeaderText();
        assertEquals("Create Account", createAccountHeaderText);

        TestLogger.stepInfo("Successfully redirected to Create Account page");
    }


    // ========== CREATE ACCOUNT STEPS ========== //

    @When ("user enters valid email and valid password")
    public void user_enters_valid_email_and_valid_password() {
        String newEmail = TestDataGeneratorUtils.generateEmail();
        String newPassword = TestDataGeneratorUtils.generatePassword();

        testContext.getCreateAccountPage().enterAccountCredentials(newEmail, newPassword);

        TestLogger.stepInfo("Entered valid email: " + newEmail);
    }

    @And ("user clicks on Create Account button")
    public void user_clicks_on_Create_Account_button() throws InterruptedException {

        testContext.getCreateAccountPage().clickonCreateAccountBtn();

        TestLogger.stepInfo("Clicked on Create Account button");

        //Thread.sleep(2000);



    }

    @Then ("Success popup should appear")
    public void Success_popup_should_appear(){

        WebDriver driver = DriverFactory.getDriver();

        WaitUtils.waitForVisibility(driver, testContext.getCreateAccountPage().successPopup);
        TestLogger.stepInfo("Success popup appeared");

    }

    @And ("popup should display 'Your account has been created successfully!' success message")
    public void popup_should_display_expected_success_message() {

        boolean isMessageDisplayed = testContext.getCreateAccountPage().isSuccessPopupDisplayed();
        assertTrue(isMessageDisplayed, "Success popup should be displayed after clicking on Create Account button");

        String actualSuccessMessage = testContext.getCreateAccountPage().getPopupMessage();
        String expectedSuccessMessage = "Your account has been created successfully!";

        assertEquals(expectedSuccessMessage, actualSuccessMessage);
        TestLogger.stepInfo("Popup displays correct Success message: " + actualSuccessMessage);

    }

    @And ("user should get redirected to Login page")
    public void user_should_get_redirected_to_login_page() {

        WebDriver driver = DriverFactory.getDriver();
        WaitUtils.waitForInvisibility(driver, testContext.getCreateAccountPage().successPopup);

        boolean urlContainsIndex = WaitUtils.waitForUrlContains(testContext.driver, "index");

        String currentUrl = testContext.driver.getCurrentUrl();
        TestLogger.stepInfo("Current URL: " + currentUrl);

        assertTrue(urlContainsIndex,
                "User should get redirected to Login page. Current URL: " + currentUrl);

    }




}
