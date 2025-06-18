package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtils;
import utils.TestLogger;

public class CreateAccountPage {

    private WebDriver driver;

    public CreateAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath="//body/h2")
    public WebElement createAccountHeader;

    @FindBy(id="email")
    public WebElement emailField;

    @FindBy(id="password")
    public WebElement passwordField;

    @FindBy(id="verifyPassword")
    public WebElement verifyPasswordField;

    @FindBy(id="createBtn")
    public WebElement createAccountBtn;

    @FindBy(id="notificationModal")
    public WebElement successPopup;

    @FindBy(xpath="//div[@id='notificationModal']//p")
    public WebElement popupMessage;

    @FindBy(id="notificationOkBtn")
    public WebElement popupOkBtn;


    // ========== ENTER NEW CREDENTIALS METHODS ========== //

    public boolean isCreateAccountHeaderDisplayed() {
        boolean isDisplayed = ElementUtils.isDisplayed(createAccountHeader);
        TestLogger.assertion("Create Account page header is displayed", isDisplayed);
        return isDisplayed;
    }

    public String getCreateAccountHeaderText() {
        String createAccountHeaderText = ElementUtils.getText(createAccountHeader);
        TestLogger.debug("Create Account header text: {}", createAccountHeader);
        return createAccountHeaderText;
    }

    public void enterNewEmail(String newEmail) {
        TestLogger.actionInfo("Enter new Email", newEmail);
        ElementUtils.clearAndType(emailField, newEmail);
    }

    public void enterNewPassword (String newPassword) {
        TestLogger.actionInfo("Enter new Password", newPassword);
        ElementUtils.clearAndType(passwordField, newPassword);
    }

    public void enterVerifyPassword(String verifyPassword) {
        TestLogger.actionInfo("Enter verify Password", verifyPassword);
        ElementUtils.clearAndType(verifyPasswordField, verifyPassword);
    }

    public void clickonCreateAccountBtn() {
        TestLogger.actionInfo("Click", "Create Account button");
        ElementUtils.click(createAccountBtn);
    }

    public void enterAccountCredentials(String email, String password) {
        enterNewEmail(email);
        enterNewPassword(password);
        enterVerifyPassword(password);
    }



    // ========== POPUP METHODS ========== //

    public boolean isSuccessPopupDisplayed() {
        try {
            boolean isDisplayed = ElementUtils.isDisplayed(successPopup);
            TestLogger.assertion("Success popup is displayed", isDisplayed);
            return isDisplayed;
        } catch (NoSuchElementException e) {
            TestLogger.debug("Success popup element not found in DOM: popup is closed");
            return false;
        } catch(Exception e) {
            TestLogger.debug("Exception checking popup display status: {}", e.getMessage());
            return false;
        }
    }

    public String getPopupMessage() {
        String message = ElementUtils.getText(popupMessage);
        TestLogger.debug("Popup message: {}", message);
        return message;
    }

    public void clickOnPopupOkButton() {
        TestLogger.actionInfo("Click", "Popup OK button");
        ElementUtils.click(popupOkBtn);
    }

    public boolean isOkButtonEnabled() {
        boolean isEnabled = ElementUtils.isEnabled(popupOkBtn);
        TestLogger.debug("OK button enabled: {}", isEnabled);
        return isEnabled;
    }


}
