package cn.hzbook.android.test.chapter9.jnidemo;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {
	static {
		System.loadLibrary("mylib");
	}
	
	public native String getMyData(); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getMyData());
	}
}
