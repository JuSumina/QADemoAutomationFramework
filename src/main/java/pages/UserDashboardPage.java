package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtils;
import utils.TestLogger;

public class UserDashboardPage {

    private WebDriver driver;

    public UserDashboardPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath="//body/h2")
    public WebElement userDashboardHeader;

    public boolean isUserDashboardHeaderDisplayed() {
        boolean isDisplayed = ElementUtils.isDisplayed(userDashboardHeader);
        TestLogger.assertion("Login page is displayed", isDisplayed);
        return isDisplayed;
    }

    public String getUserDashboardHeaderText() {
        String userDashboardHeaderText = ElementUtils.getText(userDashboardHeader);
        TestLogger.debug("Login header text: {}", userDashboardHeaderText);
        return userDashboardHeaderText;
    }
}
