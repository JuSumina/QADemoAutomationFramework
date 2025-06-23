package pages;

import context.TestContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ElementUtils;
import utils.TestLogger;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath="//body/h1")
    public WebElement pageHeader;

    @FindBy(xpath="//form[@id='user-form']/preceding-sibling::h2")
    public WebElement userLoginHeader;
    @FindBy(id="userEmail")
    public WebElement userUsernameField;

    @FindBy(id="userPassword")
    public WebElement userPasswordField;

    @FindBy(id="userLogin")
    public WebElement userLoginBtn;

    @FindBy(id="userLoginMessage")
    public WebElement userLoginErrorMessage;

    @FindBy(xpath="//a[text()='Create Account']")
    public WebElement createAccountLink;

    @FindBy(xpath="//a[text()='Forgot your password?']")
    public WebElement forgotPasswordLink;

    @FindBy(id="userToggle")
    public WebElement userLoginToggle;

    @FindBy(id="adminToggle")
    public WebElement adminLoginToggle;


    // ========== LOGIN METHODS ========== //
    public boolean isLoginPageHeaderDisplayed() {
        boolean isDisplayed = ElementUtils.isDisplayed(pageHeader);
        TestLogger.assertion("Login page header is displayed", isDisplayed);
        return isDisplayed;
    }


    public void enterEmail(String email) {
        TestLogger.actionInfo("Enter Username", email);
        ElementUtils.clearAndType(userUsernameField, email);
    }

    public void enterPassword(String password) {
        TestLogger.actionInfo("Enter Password", password);
        ElementUtils.clearAndType(userPasswordField, password);
    }

    public void clickOnUserToggle() {
        TestLogger.actionInfo("Click", "User Login toggle");
        ElementUtils.click(userLoginToggle);
    }

    public void clickOnAdminToggle() {
        TestLogger.actionInfo("Click", "Admin Login toggle");
        ElementUtils.click(adminLoginToggle);
    }

    public void clickOnLoginBtn() {
        TestLogger.actionInfo("Click", "Login button");
        ElementUtils.click(userLoginBtn);
    }

    public void clickOnCreateAccountLink() {
        TestLogger.actionInfo("Click", "Create Account link");
        ElementUtils.click(createAccountLink);
    }

    public void clickOnForgotPasswordLink() {
        TestLogger.actionInfo("Click", "Forgot Password link");
        ElementUtils.click(forgotPasswordLink);
    }

    public boolean isUserLoginHeaderDisplayed() {
        boolean isDisplayed = ElementUtils.isDisplayed(userLoginHeader);
        TestLogger.assertion("Login page is displayed", isDisplayed);
        return isDisplayed;
    }

    public String getUserLoginHeaderText() {
        String userLoginHeaderText = ElementUtils.getText(userLoginHeader);
        TestLogger.debug("Login header text: {}", userLoginHeaderText);
        return userLoginHeaderText;
    }

    public String getUserLoginErrorMessage() {
        String userLoginErrorText = ElementUtils.getText(userLoginErrorMessage);
        TestLogger.debug("Login header text: {}", userLoginErrorText);
        return userLoginErrorText;
    }

    public boolean isUserUsernameFieldEnabled() {
        boolean isEnabled = ElementUtils.isEnabled(userUsernameField);
        TestLogger.debug("Username field enabled: {}", isEnabled);
        return isEnabled;
    }


    public boolean isUserPasswordFieldEnabled() {
        boolean isEnabled = ElementUtils.isEnabled(userPasswordField);
        TestLogger.debug("Password field enabled: {}", isEnabled);
        return isEnabled;
    }


    public boolean isUserLoginButtonEnabled() {
        boolean isEnabled = ElementUtils.isEnabled(userLoginBtn);
        TestLogger.debug("Login button enabled: {}", isEnabled);
        return isEnabled;
    }


    public String getUserUsernameFieldValue() {
        String value = ElementUtils.getDomProperty(userUsernameField, "value");
        TestLogger.debug("Username field value: {}", value);
        return value;
    }


    public void clearUserUsername() {
        TestLogger.actionInfo("Clear", "Username field");
        ElementUtils.clear(userUsernameField);
    }

    public void clearUserPassword() {
        TestLogger.actionInfo("Clear", "Password field");
        ElementUtils.clear(userPasswordField);
    }



}
