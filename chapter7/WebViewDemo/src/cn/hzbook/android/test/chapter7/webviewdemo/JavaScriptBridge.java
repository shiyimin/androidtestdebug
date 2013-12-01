package cn.hzbook.android.test.chapter7.webviewdemo;

import android.content.Context;
import android.widget.Toast;

public class JavaScriptBridge {
	Context _context;
	
	public JavaScriptBridge(Context context) {
		_context = context;
	}
	
	public void alert(String message) {
		 Toast.makeText(_context, "安卓代码里打开的消息提示框！\n" + message, Toast.LENGTH_LONG).show();
	}
}
