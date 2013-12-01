package cn.hzbook.android.test.chapter7.webviewqunitdemo.test;

import java.util.Stack;

public class JavaScriptTestInterface {
	private Stack<String> _results = new Stack<String>();
	
	public String popResult() {
		return _results.pop();
	}
	
	public void pushResult(String value) {
		_results.push(value);
	}
}
