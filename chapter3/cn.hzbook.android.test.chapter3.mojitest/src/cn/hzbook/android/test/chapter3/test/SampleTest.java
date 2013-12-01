package cn.hzbook.android.test.chapter3.test;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

@SuppressWarnings("rawtypes")
public class SampleTest extends ActivityInstrumentationTestCase2 {
	private static String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.moji.mjweather.CSplashScreen";
    private Solo _solo;

	@SuppressWarnings("unchecked")
	public SampleTest() throws Exception {
		super(Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME));
	}
	
    public void setUp() throws Exception
    {
        _solo = new Solo(this.getInstrumentation(), this.getActivity());
    }
    
    public void tearDown() throws Exception
    {
        _solo.finishOpenedActivities();
    }

	public void test打开主页() throws InterruptedException {
		Thread.sleep(3000);
		_solo.clickOnText("趋势");
		Thread.sleep(10 * 1000);
	}
}
