package cn.hzbook.android.test.chapter7.webviewqunitdemo;

import cn.hzbook.android.test.chapter7.webviewqunitdemo.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final WebView wv = (WebView)findViewById(R.id.webview);
        // 启用JavaScript解释功能
        WebSettings setting = wv.getSettings();
        setting.setJavaScriptEnabled(true);
        
        // 扩展前台网页的JavaScript API
        wv.addJavascriptInterface(
        		new JavaScriptBridge(), "android");
        
        // wv.loadUrl("http://10.0.2.2/qtest.html");
        wv.loadUrl("file:///android_asset/qtest.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
