package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement textFieldEmailAddress;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement textFieldPassword;

    @FindBy(xpath = "//input[@value='Login']")
    WebElement buttonLogin;

    public void setTextFieldEmailAddress(String emailAddress) {
        textFieldEmailAddress.sendKeys(emailAddress);
    }

    public void setTextFieldPassword(String password) {
        textFieldPassword.sendKeys(password);
    }

    public void clickLoginButton() {
        buttonLogin.click();
    }
}
