package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement textFieldFirstName;

    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement textFieldLastName;

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement textFieldEmail;

    @FindBy(xpath = "//input[@id='input-telephone']")
    WebElement textFieldTelephone;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement textFieldPassword;

    @FindBy(xpath = "//input[@id='input-confirm']")
    WebElement textFieldConfirmPassword;

    @FindBy(xpath = "//input[@name='agree']")
    WebElement checkboxPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement buttonContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement messageConfirmation;

    public void setTextFieldFirstName(String firstName) {
        textFieldFirstName.sendKeys(firstName);
    }

    public void setTextFieldLastName(String lastName) {
        textFieldLastName.sendKeys(lastName);
    }

    public void setTextFieldEmail(String email) {
        textFieldEmail.sendKeys(email);
    }

    public void setTextFieldTelephone(String telephone) {
        textFieldTelephone.sendKeys(telephone);
    }

    public void setTextFieldPassword(String password) {
        textFieldPassword.sendKeys(password);
    }

    public void setTextFieldConfirmPassword(String confirmPassword) {
        textFieldConfirmPassword.sendKeys(confirmPassword);
    }

    public void setPrivacyPolicy() {
        checkboxPolicy.click();
    }
    public void clickContinue() {
        buttonContinue.click();
    }

    public String getConfirmationMessage() {
        try {
            return messageConfirmation.getText();
        }
        catch (Exception e) {
            return e.getMessage();
        }

    }


}
