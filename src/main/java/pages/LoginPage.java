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

    @FindBy(xpath="//form[@id='user-form']/preceding-sibling::h2")
    public WebElement userLoginHeader;
    @FindBy(id="user-email")
    public WebElement userUsernameField;

    @FindBy(id="user-password")
    public WebElement userPasswordField;

    @FindBy(id="user-login")
    public WebElement userLoginBtn;


    // ========== LOGIN METHODS ========== //
    public boolean isLoginPageDisplayed() {
        boolean isDisplayed = ElementUtils.isDisplayed(userLoginHeader);
        TestLogger.assertion("Login page is displayed", isDisplayed);
        return isDisplayed;
    }


    public void enterUsername(String username) {
        TestLogger.actionInfo("Enter Username", username);
        ElementUtils.clearAndType(userUsernameField, username);
    }

    public void enterPassword(String password) {
        TestLogger.actionInfo("Enter Password", password);
        ElementUtils.clearAndType(userPasswordField, password);
    }

    public void clickOnLoginBtn() {
        TestLogger.actionInfo("Click", "Login Button");
        ElementUtils.click(userLoginBtn);
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
