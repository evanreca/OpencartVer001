package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

import java.io.IOException;

public class TC002_LoginTest extends BaseClass {

    @Test(groups = {"Sanity", "Master"})
    public void verifyLogin() throws IOException {
        logger.info("------- STARTING TC002_LoginTest -------");

        try{
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            homePage.clickLogin();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setTextFieldEmailAddress(properties.getProperty("email"));
            loginPage.setTextFieldPassword(properties.getProperty("password"));
            loginPage.clickLoginButton();

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            Assert.assertTrue(myAccountPage.didMyAccountPageLoad());
        }
        catch (Exception e) {
            logger.error("Test failed: verifyLogin() ", e);
            logger.debug("Current URL: {}", driver.getCurrentUrl());
            logger.debug("Page title: {}", driver.getTitle());
            Assert.fail();
        }
        logger.info("------- TC002_LoginTest COMPLETE -------");
    }
}
