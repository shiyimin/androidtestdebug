package mobiletest.vowei.com.uiautomatorsendmessage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.*;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.*;

import static org.junit.Assert.*;
import junit.framework.TestCase;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private UiDevice mDevice;
    private static final int LAUNCH_TIMEOUT = 5000;

    /*
    @Rule
    public RepeatRule repeatRule = new RepeatRule();
    */

    @Test
    // @Repeat(2)
    public void test() throws Exception {
        // Default parameters
        String toNumber = "123456";
        String text = "Test message";

        findAndRunApp();
        sendMessage(toNumber, text);
        exitToMainWindow();
    }

    private void findAndRunApp() throws UiObjectNotFoundException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // 去主界面
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // 找到菜单
        UiObject allAppsButton = new UiObject(new UiSelector()
                .description("Apps"));
        // Click on menu button and wait new window
        allAppsButton.clickAndWaitForNewWindow();
        // Find App tab
        UiObject appsTab = new UiObject(new UiSelector()
                .text("Apps"));
        // Click on app tab
        appsTab.click();
        // Find scroll object (menu scroll)
        UiScrollable appViews = new UiScrollable(new UiSelector()
                .scrollable(true));
        // Set the swiping mode to horizontal (the default is vertical)
        appViews.setAsHorizontalList();
        // Find Messaging application
        UiObject settingsApp = appViews.getChildByText(new UiSelector()
                .className("android.widget.TextView"), "Messaging");
        // Open Messaging application
        settingsApp.clickAndWaitForNewWindow();

        // Validate that the package name is the expected one
        UiObject settingsValidation = new UiObject(new UiSelector()
                .packageName("com.android.mms"));
        assertTrue("Unable to detect Messaging",
                settingsValidation.exists());
    }

    private void sendMessage(String toNumber, String text) throws UiObjectNotFoundException {
        // Find and click New message button
        UiObject newMessageButton = new UiObject(new UiSelector()
                .className("android.widget.TextView").description("New message"));
        newMessageButton.clickAndWaitForNewWindow();

        // Find to box and enter the number into it
        UiObject toBox = new UiObject(new UiSelector()
                .className("android.widget.MultiAutoCompleteTextView").instance(0));
        toBox.setText(toNumber);
        // Find text box and enter the message into it
        UiObject textBox = new UiObject(new UiSelector()
                .className("android.widget.EditText").instance(0));
        textBox.setText(text);

        // Find send button and send message
        UiObject sendButton = new UiObject(new UiSelector()
                .className("android.widget.ImageButton").description("Send"));
        sendButton.click();
    }

    private void exitToMainWindow() throws Exception {
        // Find New message button
        UiObject newMessageButton = new UiObject(new UiSelector()
                .className("android.widget.TextView").description("New message"));

        // Press back button while new message button doesn't exist
        mDevice.pressHome();
        mDevice.pressBack();
        /*
        while(!newMessageButton.exists()) {
            mDevice.pressBack();
            Thread.sleep(500);
        }
        */
    }
}

