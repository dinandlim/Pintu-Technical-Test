package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.pages.PageObject;
import webdriver.Utils;
import org.junit.Assert;
import pages.*;

public class CommonSteps{
    CommonPage commonPage;

    @When("^User click '(.*)' button$")
    public void userClickButton(String label) { commonPage.clickFindLabelButton(label); }

    @Then("^User see '(.*)' activity label$")
    public void userSeeActivityLabel(String label) {
        boolean isVisible = commonPage.findLabel(label);
        Assert.assertTrue("Activity label is show", isVisible);
    }

    @Then("^User not see '(.*)' activity label$")
    public void userNotSeeActivityLabel(String label) {
        boolean isVisible = commonPage.findLabelNotSee(label);
        Assert.assertTrue("Activity label is show", isVisible);
    }

    @And("^User close the app$")
    public void userCloseTheApp() {

        Utils.threadLocal.get().quit();
    }

    @And("^User navigate back$")
    public void userNavigateBack() {

        Utils.threadLocal.get().navigate().back();
    }
}
