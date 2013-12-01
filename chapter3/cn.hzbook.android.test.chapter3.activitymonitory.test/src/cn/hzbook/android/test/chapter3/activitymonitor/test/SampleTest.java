package cn.hzbook.android.test.chapter3.activitymonitor.test;

import cn.hzbook.android.test.chapter3.activitymonitor.R;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.content.IntentFilter;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;

@SuppressWarnings("rawtypes")
public class SampleTest extends ActivityInstrumentationTestCase2 {
    private static String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "cn.hzbook.android.test.chapter3.activitymonitor.MainActivity";    
    private static String TARGET_PACKAGE_ID = "cn.hzbook.android.test.chapter3.activitymonitor";

	@SuppressWarnings({ "unchecked", "deprecation" })
    public SampleTest() throws Exception {
		super(TARGET_PACKAGE_ID, Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME));
    }
	
	public void test点击链接() {
		final Instrumentation inst = getInstrumentation();
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_VIEW);
		intentFilter.addDataScheme("http");
		intentFilter.addCategory(Intent.CATEGORY_BROWSABLE);
		View link = this.getActivity().findViewById(R.id.link);
		ActivityMonitor monitor = inst.addMonitor(
				intentFilter, null, false);
		assertEquals(0, monitor.getHits());
		TouchUtils.clickView(this, link);
		monitor.waitForActivityWithTimeout(5000);
		assertEquals(1, monitor.getHits());
		inst.removeMonitor(monitor);
	}
}
