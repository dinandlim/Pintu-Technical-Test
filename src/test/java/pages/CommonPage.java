package pages;

import io.appium.java_client.MobileElement;
import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.pages.PageObject;
import webdriver.Utils;

public class CommonPage extends PageObject {

    public void clickFindLabelButton(String label) {
        waitABit(5000);
        String lblXPath = "//*[contains(@content-desc,'" + label + "')]" + "|//*[contains(@text,'" + label + "')]";
        MobileElement lblAppElementButton = Utils.findAndWaitMobileElementUntilPresent(lblXPath);
        lblAppElementButton.click();
    }

    public boolean findLabel(String text) {
        String lblXPath = "//*[contains(@text,'" + text + "')]";
        try {

            Utils.threadLocal.get().findElement(By.xpath(lblXPath));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }

    public boolean findLabelNotSee(String text) {
        String lblXPath = "//*[contains(@content-desc,'" + text + "')]";
        try {

            Utils.threadLocal.get().findElement(By.xpath(lblXPath));

            return false;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return true;
        }
    }




}
