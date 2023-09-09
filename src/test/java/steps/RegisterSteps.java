package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SendKeys;
import org.junit.Assert;
import pages.RegisterPage;
import org.openqa.selenium.Keys;


import static org.junit.Assert.assertTrue;

public class RegisterSteps {

    RegisterPage registerPage;


    @And("^User verify all label in register page$")
    public void userRedirectToLoginPage(){
        boolean isVisible = registerPage.verifyAllLabelRegisterPage();
        Assert.assertTrue("Activity label is show", isVisible);

    }

    @And("^User input '(.*)' in name field$")
    public void userInputNameTextfield(String name) {
        registerPage.inputNameTextfield(name);
    }

    @And("^User input '(.*)' in email field$")
    public void userInputEmailTextfield(String email) {
        registerPage.inputEmailTextfield(email);
    }

    @And("^User input random email in email textfield$")
    public void userInputRandomEmailTextfield() {
        registerPage.inputRandomEmailTextfield();
    }

    @And("^User input '(.*)' in password field$")
    public void userInputPasswordTextfield(String password) {
        registerPage.inputPasswordTextfield(password);
    }

    @And("^User input '(.*)' in confirm password field$")
    public void userInputConfirmPasswordTextfield(String confirmPassword) {
        registerPage.inputConfirmPasswordTextfield(confirmPassword);
    }

    @Then("^User see error notification in name textfield$")
    public void userSeeErrorNotificationEnterFullName() {
        boolean isVisible = registerPage.verifyUserSeeErrorNotifEnterFullName();
        Assert.assertTrue("Activity label is show", isVisible);
    }

    @Then("^User see error notification in email textfield$")
    public void userSeeErrorNotifEnterValidEmail() {
        boolean isVisible = registerPage.verifyUserSeeErrorNotifEnterValidEmail();
        Assert.assertTrue("Activity label is show", isVisible);
    }

    @Then("^User see error notification in password textfield$")
    public void userSeeErrorNotifEnterPassword() {
        boolean isVisible = registerPage.verifyUserSeeErrorNotifEnterPassword();
        Assert.assertTrue("Activity label is show", isVisible);
    }

    @Then("^User see error notification in confirm password textfield$")
    public void userSeeErrorNotifPasswordNotMatch() {
        boolean isVisible = registerPage.verifyUserSeeErrorNotifPasswordNotMatch();
        Assert.assertTrue("Activity label is show", isVisible);
    }

    @Then("^User see notification registration success$")
    public void userSeeNotifRegistrationSuccess() {
        boolean isVisible = registerPage.verifyUserSeeNotifRegistrationSuccess();
        Assert.assertTrue("Activity label is show", isVisible);
    }

    @Then("^User see notification email already exists$")
    public void userSeeNotifEmailAlreadyExists() {
        boolean isVisible = registerPage.verifyUserSeeNotifEmailAlreadyExists();
        Assert.assertTrue("Activity label is show", isVisible);
    }

}
