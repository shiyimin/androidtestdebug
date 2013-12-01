package cn.hzbook.android.test.chapter11;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class cn.hzbook.android.test.chapter11.MainActivityTest \
 * cn.hzbook.android.test.chapter11.tests/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super("cn.hzbook.android.test.chapter11", MainActivity.class);
    }

}
