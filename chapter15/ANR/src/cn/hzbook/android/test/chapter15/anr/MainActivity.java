package cn.hzbook.android.test.chapter15.anr;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 模拟一个长时间的操作
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				Toast toast = Toast.makeText(getApplicationContext(), "点击完成！",
						Toast.LENGTH_LONG);
				toast.show();
			}
		});

		button = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"button2被单击了！", Toast.LENGTH_LONG);
				toast.show();
			}
		});

		button = (Button) findViewById(R.id.http_button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String httpUrl = "http://www.baidu.com/";
				HttpGet request = new HttpGet(httpUrl);
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse response = null;
				try {
					response = httpClient.execute(request);
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				}
				if (response != null
						&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					Toast.makeText(getApplicationContext(), "成功执行HTTP请求！",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "HTTP请求执行失败！",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
