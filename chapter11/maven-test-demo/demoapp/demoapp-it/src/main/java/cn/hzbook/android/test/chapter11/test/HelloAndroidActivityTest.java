package cn.hzbook.android.test.chapter11.test;

import android.test.ActivityInstrumentationTestCase2;
import cn.hzbook.android.test.chapter11.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testActivity() {
        HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);
    }
}

