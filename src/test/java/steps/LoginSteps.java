package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import pages.RegisterPage;
import webdriver.Utils;

import java.net.MalformedURLException;
import pages.LoginPage;


public class LoginSteps {
    LoginPage loginPage;

    RegisterPage registerPage;

    String tempRegEmail;


    @Given("^User open Login Register App$")
    public void userOpenFitaAppForTestCase() throws MalformedURLException {
        loginPage.openLoginRegister();
    }

    @And("^User redirect to login page$")
    public void userRedirectToLoginPage(){
        boolean isVisible = loginPage.verifyTitleLoginPage();
        Assert.assertTrue("Activity label is show", isVisible);

    }

    @And("^User see all text in login page$")
    public void userVerifyAllTextLoginPage(){
        boolean isVisible = loginPage.verifyAllTextLoginPage();
        Assert.assertTrue("Activity label is show", isVisible);

    }

    @And("^User verify inputed email is correct$")
    public void userVerifyInputedEmailCorrect() {
        tempRegEmail = registerPage.getRegEmail();
    }



    @And("^User input registered email$")
    public void userInputRegisteredEmail(){
       loginPage.inputRegisteredEmail(tempRegEmail);

    }

    @Then("^User verify registered user data after login$")
    public void userVerifyRegisteredUserDataShowAfterLogin(){
        loginPage.verifyRegisteredUserDataShowAfterLogin(tempRegEmail);

    }

}
