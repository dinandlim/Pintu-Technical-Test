package pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.pages.PageObject;
import webdriver.Utils;

import java.net.MalformedURLException;

public class LoginPage  extends PageObject {


    String labelTitleLoginPage = "//android.widget.TextView[@text='VERSION - V4']";
    String labelEmailLoginPage = "//TextInputLayout[@text='Email']";
    String labelPasswordLoginPage = "//TextInputLayout[@text='Password']";
    String textFieldEmailXPath = "//android.widget.EditText[@resource-id='com.loginmodule.learning:id/textInputEditTextEmail']";
    String labelAccountNameXPath = "//android.widget.TextView[@resource-id='com.loginmodule.learning:id/textViewName']";
    String labelAccountPasswordXPath = "//android.widget.TextView[@resource-id='com.loginmodule.learning:id/textViewPassword']";
    String labelAccountEmailXPath = "//android.widget.TextView[@resource-id='com.loginmodule.learning:id/textViewEmail']";
    String labelHelloAccountNameXPath = "//android.widget.TextView[@text='Hello,']/../following-sibling::android.widget.TextView[@resource-id='com.loginmodule.learning:id/textViewName']";


    public static AndroidDriver<MobileElement> driver;
    public static Utils utils = new Utils();


    public AndroidDriver<MobileElement> openAPP() throws MalformedURLException {
        driver = utils.newMobileDriver();
        driver.resetApp();
        waitABit(5000);
        return driver;
    }

    public AndroidDriver<MobileElement> openLoginRegister() throws MalformedURLException {
        return openAPP();
    }

    public boolean verifyTitleLoginPage() {
        try {
            Utils.threadLocal.get().findElement(By.xpath(labelTitleLoginPage));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }

    public boolean verifyAllTextLoginPage() {
        try {
            Utils.threadLocal.get().findElement(By.xpath(labelTitleLoginPage));
            Utils.threadLocal.get().findElement(By.xpath(labelEmailLoginPage));
            Utils.threadLocal.get().findElement(By.xpath(labelPasswordLoginPage));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }



    public void inputRegisteredEmail(String regEmail) {
        waitABit(3000);
        MobileElement txtEmail = Utils.findAndWaitMobileElementUntilPresent(textFieldEmailXPath);
        txtEmail.click();
        waitABit(5000);
        txtEmail.sendKeys(regEmail);
        waitABit(5000);
        Utils.threadLocal.get().navigate().back();
    }


    public boolean verifyRegisteredUserDataShowAfterLogin(String regEmail) {

        waitABit(5000);

        String labelAccountNameXPath = "//android.widget.TextView[@text='TesterOne']";
        String labelAccountPasswordXPath = "//android.widget.TextView[@text='Testing123']";
        String labelAccountEmailXPath = "//android.widget.LinearLayout/android.widget.TextView[contains(@text,'"+regEmail+"')]";
        String labelHelloAccountNameXPath = "//android.widget.TextView[@text='Hello,']/../following-sibling::android.widget.TextView[contains(@text,'"+regEmail+"')]";

        try {
            Utils.threadLocal.get().findElement(By.xpath(labelHelloAccountNameXPath));
            Utils.threadLocal.get().findElement(By.xpath(labelAccountEmailXPath));
            Utils.threadLocal.get().findElement(By.xpath(labelAccountNameXPath));
            Utils.threadLocal.get().findElement(By.xpath(labelAccountPasswordXPath));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }
}





