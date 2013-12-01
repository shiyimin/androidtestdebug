package cn.hzbook.android.test.chapter7.webviewqunitdemo.test;

import junit.framework.Assert;
import cn.hzbook.android.test.chapter7.webviewqunitdemo.MainActivity;
import cn.hzbook.android.test.chapter7.webviewqunitdemo.R;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.webkit.WebView;

public class QUnitMixTest extends ActivityInstrumentationTestCase2<MainActivity>  {

	public QUnitMixTest() {
		super(MainActivity.class);
	}

	public void test通过远程测试网页执行安卓与QUnit混合测试() throws InterruptedException {
		Activity activity = getActivity();
        final WebView wv = (WebView)activity.findViewById(R.id.webview);
        JavaScriptTestInterface jsti = new JavaScriptTestInterface();
        wv.addJavascriptInterface(jsti, "qunit");
        wv.loadUrl("http://10.0.2.2/qtest.html");
        Thread.sleep(10000);
        wv.loadUrl("javascript:qunit.pushResult($('p#qunit-testresult.result').text());");
        Thread.sleep(1000);
        String value = jsti.popResult();
        Assert.assertTrue(value.endsWith("1 assertions of 1 passed, 0 failed."));
	}

	public void test通过本地资源网页执行安卓与QUnit混合测试() throws InterruptedException {
		// 找到待测应用的活动对象的引用
		Activity activity = getActivity();
		// 找到待测应用主界面上的WebView对象
        final WebView wv = (WebView)activity.findViewById(R.id.webview);
        // 注册收集测试结果的JavaScript桥接对象
        JavaScriptTestInterface jsti = new JavaScriptTestInterface();
        wv.addJavascriptInterface(jsti, "qunit");
        // 加载QUnit测试网页
        wv.loadUrl("file:///android_asset/qtest.html");
        // 等待QUnit用例执行完毕
        Thread.sleep(10000);
        // 收集QUnit测试结果
        wv.loadUrl("javascript:qunit.pushResult($('p#qunit-testresult.result').text());");
        Thread.sleep(1000);
        String value = jsti.popResult();
        // 对比QUnit结果中的统计数据与期望值是否匹配
        Assert.assertTrue(value.endsWith("1 assertions of 1 passed, 0 failed."));
	}
}
