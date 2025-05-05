package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

// data is valid ---> login success ---> test passed ---> logout
// data is valid ---> login failed ---> test failed

// data is invalid ---> login success ---> test failed ---> logout
// data is invalid ---> login failed ---> test passed



public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataDriven") // getting the data provider from a different class in a different package
    public void verifyLoginDDT(String email, String password, String expectedResult) {

        logger.info("------- STARTING TEST CASE TC003_LoginDDT -------");

        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            homePage.clickLogin();

            LoginPage loginPage = new LoginPage(driver);

            loginPage.setTextFieldEmailAddress(email);
            loginPage.setTextFieldPassword(password);

            loginPage.clickLoginButton();

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean targetPage = myAccountPage.didMyAccountPageLoad();

            if (expectedResult.equalsIgnoreCase("Valid")) {
                if (targetPage == true) {
                    myAccountPage.clickLogoutButton();
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue(false);
                }
            }
            if (expectedResult.equalsIgnoreCase("Invalid")) {
                if (targetPage == false) {
                    Assert.assertTrue(true);
                } else {
                    myAccountPage.clickLogoutButton();
                    Assert.assertTrue(false);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            logger.info("TEST FAILED");
            Assert.fail();
        }
        finally {
            logger.info("------- TEST CASE TC003_LoginDDT COMPLETE -------");
        }

    }

}
