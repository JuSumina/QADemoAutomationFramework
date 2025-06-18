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
    public void user_clicks_on_Create_Account_button() {

        testContext.getCreateAccountPage().clickonCreateAccountBtn();

        TestLogger.stepInfo("Clicked on Create Account button");

        WebDriver driver = DriverFactory.getDriver();
        WaitUtils.waitForVisibility(driver, testContext.getCreateAccountPage().successPopup);

    }

    @Then ("Success popup should appear")
    public void Success_popup_should_appear(){

        WebDriver driver = DriverFactory.getDriver();
        WaitUtils.waitForVisibility(driver, testContext.getCreateAccountPage().popupOkBtn);

        boolean isPopupDisplayed = testContext.getCreateAccountPage().isSuccessPopupDisplayed();
        assertTrue(isPopupDisplayed, "Success popup should appear after account creation");
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

    @And ("user should be able to click on OK button to close the popup")
    public void user_should_be_able_to_click_on_OK_button_to_close_the_popup() {

        boolean isOkBtnEnabled = testContext.getCreateAccountPage().isOkButtonEnabled();
        assertTrue(isOkBtnEnabled, "OK button should be enabled");

        testContext.getCreateAccountPage().clickOnPopupOkButton();
        TestLogger.stepInfo("Clicked on OK button");




        WebDriver driver = DriverFactory.getDriver();

        WebElement element = driver.findElement(By.xpath("//body/h2"));
        Actions actions = new Actions (driver);
        actions.moveToElement(element).perform();

        /*try {
            // Move focus away from the form
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0];scrollIntoView(true), ");
            TestLogger.debug("Removed focus from active element", createAccountHeaderText);
        } catch (Exception e) {
            TestLogger.debug("Could not blur active element: {}", e.getMessage());
        }*/


        boolean urlContainsIndex = WaitUtils.waitForUrlContains(testContext.driver, "index");

        String currentUrl = testContext.driver.getCurrentUrl();
        TestLogger.stepInfo("Current URL: " + currentUrl);

        assertTrue(urlContainsIndex,
                "User should get redirected to Login page. Current URL: " + currentUrl);

    }




}
