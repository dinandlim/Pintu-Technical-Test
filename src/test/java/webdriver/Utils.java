package webdriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utils {
    // Global Variables
    private static Properties PROPERTIES = SystemEnvironmentVariables.createEnvironmentVariables().getProperties();
    public static String currentTestCase;
    public static WebDriver device;
    public static String timeNow = null;
    public static String testName;
    public static int now;
    // ThreadLocal to handle parallel test execution
    public static final ThreadLocal<AndroidDriver<MobileElement>> threadLocal = new ThreadLocal<>();

    public static AndroidDriver<MobileElement> createLocalDC(WebDriver driver)
            throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.loginmodule.learning");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.loginmodule.learning.activities.LoginActivity");
        capabilities.setCapability("autoGrantPermissions", "true");
        System.out.println(capabilities.toString());

        // Quit the existing driver if it's not null to release resources
        if (driver != null) {
            driver.quit();
        }

        // Set up a new AndroidDriver instance and store it in ThreadLocal for parallel execution support
        threadLocal.set(new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities));
        return threadLocal.get();
    }



    public AndroidDriver<MobileElement> newMobileDriver() throws MalformedURLException {

        createLocalDC(threadLocal.get());

        // Output the session ID of the created driver for debugging purposes
        if (threadLocal.get() != null) {
            System.out.println("Session ID: " + threadLocal.get().getSessionId());
        } else {
            System.err.println("Device is null!!");
        }

        // Set the implicit wait timeout for the driver to 30 seconds
        threadLocal.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // Return the created AndroidDriver instance
        return threadLocal.get();
    }


    public static void tap(PointOption coor) {
        // Create a TouchAction instance using the Appium driver from the thread-local context.
        TouchAction touchAction = new TouchAction(threadLocal.get());

        // Perform a tap action at the specified coordinate point.
        touchAction.tap(coor).perform();
    }


    public static void swap(PointOption start, PointOption end) {
        TouchAction touchAction = new TouchAction(threadLocal.get()).press(start).moveTo(end).release();
        touchAction.perform();
    }


    public static void swapNested(PointOption start, PointOption end) {
        TouchAction touchAction = new TouchAction(threadLocal.get()).longPress(start).moveTo(end).release();
        touchAction.perform();
    }



    public static void scrollToFindMobileElement(String widgetScrollXPath, String widgetToBeFindXPath) {
        AndroidDriver<MobileElement> driver = threadLocal.get();
        WebElement contView;
        WebElement widget;

        // Add a delay for 5 seconds to allow time for elements to load.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
            // Do nothing if sleep is interrupted.
        }

        int loop = 0;
        while (loop < 5) {
            Point start, end;

            // Try to scroll using the container view.
            try {
                System.out.println("try to scroll loop: " + loop + " scroll!");
                loop++;
                contView = driver.findElement(By.xpath(widgetScrollXPath));
                start = contView.getLocation();
                end = new Point(start.x, 0);
                Utils.swap(PointOption.point(start), PointOption.point(end));

                // Add a delay for 5 seconds after scrolling to wait for elements to load.
                Thread.sleep(5000);

                // Find the target element and wait until it's present.
                Utils.findAndWaitMobileElementUntilPresent(widgetToBeFindXPath);
                break;
            } catch (Exception ignored) {
                // Do nothing if scrolling using the container view fails.
            }

            // Try to scroll using the target element.
            try {
                widget = driver.findElement(By.xpath(widgetToBeFindXPath));
                start = widget.getLocation();
                end = new Point(start.x, 0);
                Utils.swap(PointOption.point(start), PointOption.point(end));

                // Add a delay for 5 seconds after scrolling to wait for elements to load.
                Thread.sleep(5000);
                break;
            } catch (InterruptedException ignored) {
                // Do nothing if sleep is interrupted.
                System.out.println("Warning: Sleep thread has been interrupted.");
            } catch (Exception ignored) {
                // Do nothing if scrolling using the target element fails.
            }
        }

        // Add a final delay for 5 seconds.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
            // Do nothing if sleep is interrupted.
            System.out.println("Warning: Sleep thread has been interrupted.");
        }
    }


    public static void scrollToFindNestedMobileElement(String widgetToBeFindXPath) {
        // Get the AndroidDriver instance from threadLocal
        AndroidDriver<MobileElement> driver = threadLocal.get();

        // Add a delay of 5 seconds to allow the page to load before starting the scroll
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Do Nothing if interrupted while sleeping.
            System.out.println("Warning: Sleep thread has been interrupted.");
        }

        int loop = 0;
        int maxScrollAttempts = 20;

        while (loop < maxScrollAttempts) {
            try {
                // Scroll to find the nested mobile element
                System.out.println("try to scroll loop: " + loop + " scroll!");
                loop++;

                // Get the size of the screen
                Dimension size = Utils.threadLocal.get().manage().window().getSize();

                // Define the start and end points for the scrolling action
                int start_x = (int) (size.width * 0.5);
                int start_y = (int) (size.height * 0.8);
                int end_x = (int) (size.width * 0.5);
                int end_y = (int) (size.height * 0.4);

                // Perform the scrolling action using the Utils.swapNested method
                Utils.swapNested(PointOption.point(start_x, start_y), PointOption.point(end_x, end_y));

                // Add a delay of 5 seconds after scrolling to allow the content to load
                Thread.sleep(5000);

                // Try to find the nested mobile element with the given XPath
                driver.findElement(By.xpath(widgetToBeFindXPath));

                // If the element is found, break out of the loop
                break;
            } catch (NoSuchElementException e) {
                // The element was not found, continue scrolling.
            } catch (InterruptedException e) {
                // Do Nothing if interrupted while sleeping.
                System.out.println("Warning: Sleep thread has been interrupted.");
            }
        }

        // Add a delay of 5 seconds after the scrolling loop completes
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Do Nothing if interrupted while sleeping.
            System.out.println("Warning: Sleep thread has been interrupted.");
        }
    }


    public static void scrollUpToFindNestedMobileElement(String widgetToBeFindXPath) {
        AndroidDriver<MobileElement> driver = threadLocal.get();

        // Delay for 5 seconds to allow the page to load before scrolling
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Do Nothing if interrupted while sleeping.
            System.out.println("Warning: Sleep thread has been interrupted.");
        }

        int loop = 0;
        final int MAX_SCROLL_ATTEMPTS = 20;

        while (loop < MAX_SCROLL_ATTEMPTS) {
            try {
                System.out.println("Try to scroll, loop: " + loop + " scroll!");
                loop++;
                Dimension size = Utils.threadLocal.get().manage().window().getSize();
                int start_x = (int) (size.width * 0.5);
                int start_y = (int) (size.height * 0.4);
                int end_x = (int) (size.width * 0.5);
                int end_y = (int) (size.height * 0.8);
                Utils.swapNested(PointOption.point(start_x, start_y), PointOption.point(end_x, end_y));
                Thread.sleep(5000);
                driver.findElement(By.xpath(widgetToBeFindXPath));
                break;
            } catch (InterruptedException e) {
                // Do Nothing if interrupted while sleeping.
                System.out.println("Warning: Sleep thread has been interrupted.");
            } catch (Exception e) {
                // The element was not found after scrolling, continue scrolling.
            }
        }

        // Delay for 5 seconds after scrolling to ensure the page stabilizes.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Do Nothing if interrupted while sleeping.
            System.out.println("Warning: Sleep thread has been interrupted.");
        }
    }


    public static void scrollToFindMobileElement(String widgetToBeFindXPath) {
        AndroidDriver<MobileElement> driver = threadLocal.get();

        // Add a delay of 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Do Nothing if interrupted while sleeping.
            System.out.println("Warning: Sleep thread has been interrupted.");
        }

        int loop = 0;
        final int MAX_SCROLL_ATTEMPTS = 10;

        while (loop < MAX_SCROLL_ATTEMPTS) {
            // Attempt to Scroll
            try {
                System.out.println("Attempting to scroll, loop: " + loop);
                loop++;

                Dimension size = Utils.threadLocal.get().manage().window().getSize();
                int start_x = (int) (size.width * 0.5);
                int start_y = (int) (size.height * 0.8);
                int end_x = (int) (size.width * 0.5);
                int end_y = (int) (size.height * 0.4);

                // Perform the scroll action
                Utils.swap(PointOption.point(start_x, start_y), PointOption.point(end_x, end_y));

                // Add a delay of 5 seconds after scrolling
                Thread.sleep(5000);

                // Try to find the element by its XPath
                driver.findElement(By.xpath(widgetToBeFindXPath));
                break; // Element found, exit the loop
            } catch (InterruptedException e) {
                // Do Nothing if interrupted while sleeping.
                System.out.println("Warning: Sleep thread has been interrupted.");
            }catch (Exception e) {
                // Element not found, continue scrolling
            }
        }

        // Add a final delay of 5 seconds after scrolling attempts
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Do Nothing if interrupted while sleeping.
            System.out.println("Warning: Sleep thread has been interrupted.");
        }
    }


    private static void logDCAndError(DesiredCapabilities capabilities, Exception error) {
        // DesiredCapabilities information
        String logMessage = "DesiredCapabilities:{\n";
        logMessage += "deviceName: " + capabilities.getCapability("deviceName") + "\n";
        logMessage += "platformVersion: " + capabilities.getCapability("platformVersion") + "\n";
        logMessage += "build: " + capabilities.getCapability("build") + "\n";
        logMessage += "app: " + capabilities.getCapability("app") + "\n";
        logMessage += "}\n\n\n";

        // Error information
        logMessage += "Message: " + error.getMessage() + "\n";
        logMessage += "getLocalizedMessage: " + error.getLocalizedMessage() + "\n";

        // Print log to the standard error stream (System.err)
        System.err.println(logMessage);

        // The following code is currently commented out as it writes the log to a file,
        // but it is not being used in the current implementation.
        /*
        String os = System.getProperty("os.name").toLowerCase().trim();
        FileWriter myWriter = null;
        switch (os) {
            case "windows":
                try {
                    myWriter = new FileWriter("target\\site\\serenity\\logDCError.txt", true);
                    myWriter.write(logMessage);
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    myWriter = new FileWriter("/target/site/serenity/logDCError.txt", true);
                    myWriter.write(logMessage);
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                break;
        }*/
    }


    public static MobileElement findAndWaitMobileElementUntilPresent(String xPath) {
        try {
            // Find the mobile element using the provided XPath.
            MobileElement mobileElement = (MobileElement) threadLocal.get().findElement(By.xpath(xPath));

            // Wait for the mobile element to be visible on the screen.
            WebDriverWait wait = new WebDriverWait(threadLocal.get(), 30);
            wait.until(ExpectedConditions.visibilityOf(mobileElement));

            return mobileElement;
        } catch (Exception e) {
            // If the mobile element is not found, handle the exception and set the fail flag on LambdaTest.
            System.err.println(e.getMessage());
            e.printStackTrace();
            try {
                ArrayList<String> exceptionCapture = new ArrayList<>();
                exceptionCapture.add(e.getMessage());

                // To print stack error msg
                StringWriter sw = new StringWriter();
                PrintWriter printWriter = new PrintWriter(sw);
                e.printStackTrace(printWriter);
                exceptionCapture.add(sw.toString());

                // Code to report the failure to LambdaTest server (commented out).
                // threadLocal.get().executeScript("lambda-status=failed");
            } catch (Exception ex) {
                //skip
            }
            threadLocal.get().quit();
            return null;
        }
    }





}
