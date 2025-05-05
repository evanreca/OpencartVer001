package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verifyAccountRegistration() {

        logger.info("#### STARTING TC001_AccountRegistrationTest ####");

        try{
            HomePage homePage = new HomePage(driver);

            homePage.clickMyAccount();
            logger.info("CLICKED ON MyAccount LINK");

            homePage.clickRegister();
            logger.info("CLICKED ON Register LINK");

            AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);

            logger.info("INPUTTING CUSTOMER REGISTRATION DETAILS...");
            accountRegistrationPage.setTextFieldFirstName(randomString().toUpperCase());
            accountRegistrationPage.setTextFieldLastName(randomString().toUpperCase());
            accountRegistrationPage.setTextFieldEmail(randomString() + "@email.com");
            accountRegistrationPage.setTextFieldTelephone(randomNumber());

            String password = randomAlphanumeric();
            accountRegistrationPage.setTextFieldPassword(password);
            accountRegistrationPage.setTextFieldConfirmPassword(password);

            accountRegistrationPage.setPrivacyPolicy();
            accountRegistrationPage.clickContinue();

            logger.info("VALIDATING EXPECTED MESSAGE...");
            String confirmationMessage = accountRegistrationPage.getConfirmationMessage();
            Assert.assertEquals(confirmationMessage, "Your Account Has Been Created!");
        }
        catch(Exception e) {
            logger.error("********** TEST FAILED **********");
            logger.debug("LOGGING EXCEPTION (DEBUG)...");
            e.printStackTrace();
            Assert.fail();
        }
        logger.info("#### TC001_AccountRegistrationTest COMPLETE ####");
    }
}
