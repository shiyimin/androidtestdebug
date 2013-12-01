package cn.hzbook.android.test.chapter7.webviewdemo;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

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
        		new JavaScriptBridge(this), "android");
        
        // 设置点击“浏览”按钮的事件处理函数 - 显示网页
        Button btnBrowse = (Button)findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		EditText txtUrl = (EditText)findViewById(R.id.txtUrl);
        		String url = txtUrl.getText().toString();
        		wv.loadUrl(url);
        	}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
