package pages;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.gargoylesoftware.htmlunit.javascript.host.event.KeyboardEvent;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.Keys;
import webdriver.Utils;
import java.util.Random;

public class RegisterPage  extends PageObject {


    String labelNameXPath = "//TextInputLayout[@text='Name']";
    String labelEmailXPath = "//TextInputLayout[@text='Email']";
    String labelPasswordXPath = "//TextInputLayout[@text='Password']";
    String labelConfirmPasswordXPath = "//TextInputLayout[@text='Confirm Password']";
    String textFieldNameXPath = "//android.widget.EditText[@resource-id='com.loginmodule.learning:id/textInputEditTextName']";
    String textFieldEmailXPath = "//android.widget.EditText[@resource-id='com.loginmodule.learning:id/textInputEditTextEmail']";
    String textFieldPasswordXPath = "//android.widget.EditText[@resource-id='com.loginmodule.learning:id/textInputEditTextPassword']";
    String textFieldConfirmPasswordXPath = "//android.widget.EditText[@resource-id='com.loginmodule.learning:id/textInputEditTextConfirmPassword']";
    String labelEnterFullNameXPath = "//android.widget.TextView[@text='Enter Full Name']";
    String labelEnterValidEmailXPath = "//android.widget.TextView[@text='Enter Valid Email']";
    String labelEnterPasswordXPath = "//android.widget.TextView[@text='Enter Password']";
    String labelPasswordNotMatchXPath = "//android.widget.TextView[@text='Password Does Not Matches']";
    String labelRegistrationSuccessXPath = "//android.widget.TextView[@text='Registration Successful']";
    String labelEmailAlreadyExistsXPath = "//android.widget.TextView[@text='Email Already Exists']";



    public AndroidDriver<MobileElement> driver;


    public boolean verifyAllLabelRegisterPage() {
        try {
            Utils.threadLocal.get().findElement(By.xpath(labelNameXPath));
            Utils.threadLocal.get().findElement(By.xpath(labelEmailXPath));
            Utils.threadLocal.get().findElement(By.xpath(labelPasswordXPath));
            Utils.threadLocal.get().findElement(By.xpath(labelConfirmPasswordXPath));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }

    public void inputNameTextfield(String name) {
        waitABit(3000);
        MobileElement txtName = Utils.findAndWaitMobileElementUntilPresent(textFieldNameXPath);
        txtName.click();
        waitABit(5000);
        txtName.sendKeys(name);
        waitABit(5000);
        Utils.threadLocal.get().navigate().back();
    }

    public void inputEmailTextfield(String email) {
        waitABit(3000);
        MobileElement txtEmail = Utils.findAndWaitMobileElementUntilPresent(textFieldEmailXPath);
        txtEmail.click();
        waitABit(5000);
        txtEmail.sendKeys(email);
        waitABit(5000);
        Utils.threadLocal.get().navigate().back();
    }

    public void inputRandomEmailTextfield() {
        waitABit(3000);
        MobileElement txtEmail = Utils.findAndWaitMobileElementUntilPresent(textFieldEmailXPath);
        txtEmail.click();
        //random user email
        String randChar = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * randChar.length());
            sb.append(randChar.charAt(index));
        }
        String userEmail = sb.toString();

        String[] domains = {"example.com", "gmail.com", "yahoo.com", "outlook.com", "hotmail.com"};
        String domain = domains[new Random().nextInt(domains.length)];

        String fullEmail = userEmail + "@" + domain;
        txtEmail.sendKeys(fullEmail);
        waitABit(5000);

        Utils.threadLocal.get().navigate().back();


    }

    public String getRegEmail() {
        String regEmail = "";
        MobileElement lblAppElement;
        lblAppElement = Utils.findAndWaitMobileElementUntilPresent(textFieldEmailXPath);

        // Retrieve the "text" attribute of the element, which contains the level text.
        regEmail = lblAppElement.getAttribute("text");

        // Wait for a short time (5 seconds).
        waitABit(5000);

        // Return the extracted regEmail value as a String.
        return regEmail;
    }

    public void inputPasswordTextfield(String password) {
        waitABit(3000);
        MobileElement txtPassword = Utils.findAndWaitMobileElementUntilPresent(textFieldPasswordXPath);
        txtPassword.click();
        waitABit(5000);
        txtPassword.sendKeys(password);
        waitABit(5000);
        Utils.threadLocal.get().navigate().back();
    }

    public void inputConfirmPasswordTextfield(String name) {
        waitABit(3000);
        MobileElement txtConfirmPassword = Utils.findAndWaitMobileElementUntilPresent(textFieldConfirmPasswordXPath);
        txtConfirmPassword.click();
        waitABit(5000);
        txtConfirmPassword.sendKeys(name);
        waitABit(5000);
        Utils.threadLocal.get().navigate().back();

    }

    public boolean verifyUserSeeErrorNotifEnterFullName() {
        try {
            Utils.threadLocal.get().findElement(By.xpath(labelEnterFullNameXPath));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }

    public boolean verifyUserSeeErrorNotifEnterValidEmail() {
        try {
            Utils.threadLocal.get().findElement(By.xpath(labelEnterValidEmailXPath));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }

    public boolean verifyUserSeeErrorNotifEnterPassword() {
        try {
            Utils.threadLocal.get().findElement(By.xpath(labelEnterPasswordXPath));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }

    public boolean verifyUserSeeErrorNotifPasswordNotMatch() {
        try {
            Utils.threadLocal.get().findElement(By.xpath(labelPasswordNotMatchXPath));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }

    public boolean verifyUserSeeNotifRegistrationSuccess() {
        try {
            Utils.threadLocal.get().findElement(By.xpath(labelRegistrationSuccessXPath));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }

    public boolean verifyUserSeeNotifEmailAlreadyExists() {
        try {
            Utils.threadLocal.get().findElement(By.xpath(labelEmailAlreadyExistsXPath));

            return true;

        } catch (Exception e) {
            // If any of the elements is not found, catch the exception and return false
            return false;
        }
    }


}
