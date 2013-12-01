package cn.hzbook.android.test.chapter15.baduithreadoperation;

import cn.hzbook.android.test.chapter15.baduithreadoperation.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button button = (Button) findViewById(R.id.button1);
		/*
		 * 下面的代码是错误地在工作线程直接操控UI控件，其会导致CalledFromWrongThreadException异常！
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 new Thread(new Runnable() {
					    public void run() {
					        loadImageFromNetwork();
					    	button.setText("图片下载完成!");
					    }
					  }).start();			
				 }
		});
		*/

		/*
		 * 手工创建多线程并向UI线程发送更新UI通知的代码
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 new Thread(new Runnable() {
					    public void run() {
					        loadImageFromNetwork();
					    	button.post(new Runnable() {
					    		public void run() {
							    	button.setText("图片下载完成!");
					    		}
					    	});
					    }
					  }).start();			
				 }
		});
		*/

		button.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				new DownloadImageTask(button).execute("http://www.baidu.com/beauty.jpg");
			}
		});
	}

	private Object loadImageFromNetwork() {
		// 表示一个耗时的操作!
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	class DownloadImageTask extends AsyncTask {
		private Button _button = null;
		
		public DownloadImageTask(Button button) {
			_button = button;
		}
		
		@Override
		protected Object doInBackground(Object... arg0) {
			return loadImageFromNetwork();
		}
		
		@Override
		protected void onPostExecute(Object result) {
			_button.setText("图片下载完成!");
		}
	}
}
