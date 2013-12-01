package cn.hzbook.android.test.chapter9.rununit;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	static {
		// 加载C/C++编写的单元测试用例
		System.loadLibrary("rununit"); 	
	}
	
	// 执行所有的C/C++单元测试用例的原生函数
	public native String runUnitTests();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 执行完毕C/C++单元测试用例之后，将结果
		// 显示在主界面上。
		TextView  tv = new TextView(this);
        tv.setText(runUnitTests());
        setContentView(tv);
	}
}
