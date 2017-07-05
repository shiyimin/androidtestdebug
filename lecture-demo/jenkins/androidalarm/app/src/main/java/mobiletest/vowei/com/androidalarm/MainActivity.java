package mobiletest.vowei.com.androidalarm;

import java.util.Calendar;

import mobiletest.vowei.com.androidalarm.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class MainActivity extends Activity {
	// 处理单击“启动服务”按钮的事件处理程序
	private OnClickListener mStartListener = new OnClickListener() {
		public void onClick(View v) {
			// 从界面上的日期选择控件和时间选择控件的值创建一个Date实例
			DatePicker dp = (DatePicker)findViewById(R.id.datePicker);
			TimePicker tp = (TimePicker)findViewById(R.id.timePicker);			
			// 创建设置闹铃时间所需要的Date实例
			Calendar calendar = Calendar.getInstance();
			calendar.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), 
					tp.getCurrentHour(), tp.getCurrentMinute());

			// 创建启动服务的意图对象，第二个参数指定了要启动的服务类名 - AlarmService。
			Intent intent =
					new Intent(MainActivity.this, AlarmService.class);
			// 将闹铃时间附在启动服务的意图对象上，因为它是一个额外的数据，
			// 所以使用putExtra来设置，启动的服务可以通过键值“TIME”来获取闹铃时间。
			// calendar.getTime()返回一个Date实例，因为意图对象有可能会跨进程传递
			// 所以要求附带在意图对象上的数据都应该是可序列化的，而Date并不能直接被序列化
			// 这里使用Date.getTime()函数，获取日期对象对应的Long型的数值
			// 来达到支持序列化的效果。
			intent.putExtra("TIME", calendar.getTime().getTime());
			// 启动服务
			startService(intent);
		}
	};

	// 处理单击“停止服务”按钮的事件处理程序
	private OnClickListener mStopListener = new OnClickListener() {
		public void onClick(View v) {
			// 停止服务
			stopService(new Intent(MainActivity.this, AlarmService.class));
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 主界面的布局代码
		setContentView(R.layout.activity_main);

		// 设置“启动服务”和“停止服务”按钮的事件处理函数
		Button button = (Button) findViewById(R.id.btnStart);
		button.setOnClickListener(mStartListener);
		button = (Button) findViewById(R.id.btnStop);
		button.setOnClickListener(mStopListener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
