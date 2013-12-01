package cn.hzbook.android.test.chapter5.androidalarm;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

// 安卓服务都应该从Service类里继承
public class AlarmService extends Service {
	// 保存安卓系统的通知区域的引用
	private NotificationManager _mNM;
	// 闹铃服务内部默认还是使用TestableAlarm来执行闹铃操作
	private TestableAlarm _alarm;

	public TestableAlarm getAlarm() { return _alarm; }
	
	// 无论服务是通过startService还是bindService启动
	// 都会调用onCreate函数启动服务
	@Override
	public void onCreate() {
		// 获取安卓系统通知区域的引用
		_mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 在通知区域打印消息提示服务启动状态
		showNotification();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		onStartCommand(intent, 0, startId);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("DEBUG", "调用onStartCommand！");
		Option option = new Option();
		// 因为Activity是通过startService启动服务
		// 安卓系统会调用服务的onStartCommand，这里可以获取MainActivity启动服务时
		// 使用的意图对象，进而可以拿到附在意图对象的闹铃时间信息，为了支持跨进程的传递，
		// 意图对象上是闹铃时间对应的Long型整数，需要使用new Date(Long l)构造函数
		// 重建闹铃时间。
		option.alarmTime = new Date((intent.getLongExtra("TIME", 0)));
		
		// 创建并启动闹铃定时器
		_alarm = new TestableAlarm(option, new SimpleGetDate(), this.getApplicationContext());
		_alarm.start();
		Log.i("DEBUG", "onStartCommand已经被调用！");

		return START_STICKY;
	}

	// 处理停止服务的销毁资源的函数
	@Override
	public void onDestroy() {
		Log.i("DEBUG", "调用onDestroy！");
		// 首先停止闹铃定时器
		_alarm.stop();
		// 删除通知区域的服务启动状态提示消息
		_mNM.cancel(R.string.local_service_started);
		// 弹出一个提示框提示用户服务已经停止
		Toast.makeText(this, R.string.local_service_stopped, 
				Toast.LENGTH_SHORT).show();
	}

	// 这里服务不支持bindService的方式启动
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void showNotification() {
		// 获取在安卓通知区域打印服务状态消息的文本
		CharSequence text = getText(R.string.local_service_started);
		Notification notification = new Notification(
				R.drawable.ic_action_search, text, System.currentTimeMillis());
		// 设置当用户点击通知区域，服务状态提示消息时，要显示的活动
		// 这里是显示MainActivity的界面
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class), 0);
		notification.setLatestEventInfo(this,
				getText(R.string.local_service_label), text, contentIntent);
		// 在通知栏里显示消息
		_mNM.notify(R.string.local_service_started, notification);
	}
}
