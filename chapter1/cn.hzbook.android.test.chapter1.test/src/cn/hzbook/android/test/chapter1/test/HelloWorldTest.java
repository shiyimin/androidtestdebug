package cn.hzbook.android.test.chapter1.test;

import cn.hzbook.android.test.chapter1.MainActivity;
import cn.hzbook.android.test.chapter1.R;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class HelloWorldTest extends
 ActivityInstrumentationTestCase2<MainActivity> {

    public HelloWorldTest() {
    	super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
    	super.tearDown();
    }

    public void test第一个测试用例() throws Exception {
    	final MainActivity a = getActivity();
    	assertNotNull(a);
    	final Button b = 
    	    (Button)a.findViewById(R.id.btnAdd);
    	getActivity().runOnUiThread(new Runnable() {
    	     public void run() {
    	 		b.performClick();		
    	     }
    	});
    	 
    	Thread.sleep(5000);
    }
}
