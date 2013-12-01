package cn.hzbook.android.test.chapter5.alarm;

import java.util.*;

public class TestableAlarm {
	Timer _timer;
	Option _option;
	IGetDate _getDate;
	// 用在测试用例里，以便对比闹铃的触发次数是否是期望值
	public int trigerTimes;
	
	public TestableAlarm(Option option, IGetDate getdate) {
		_timer = new Timer();
		_option = option;
		_getDate = getdate;
	}
	
	public void start() {
		TimerTask task = new TimerTask() {
			private boolean _firstTime = true;
			
			public void run() {
				// 通过接口获取当前时间 - 这样就留了一个口子，允许传入任意的时间
				// 如果将下面的代码注释掉，并取消更下一行的注释，使用TestGetDate
				// 方法的测试用例将无法捕捉到这个问题，而使用模拟对象的用例可以
				// 正确捕捉到这个编码错误。
				Date now = _getDate.Now();
				// Date now = new Date();
				// 获取当前时间和闹铃开始时间的差值
				long diff = _option.alarmTime.getTime() - now.getTime();
				// 距离闹铃时间在一秒之内
				// 如果闹铃设置时间早于当前时间，就会立即执行。
				// 然而为了避免重复执行，需要一个全局变量_firstTime来记录执行次数。
				if ( diff < 1000 && _firstTime) {					
					printAlarm(now);
					_firstTime = false;
				} else if (
						// 如果有重复设置，而且当前时间和闹铃时间的毫秒差值
						// 与重复设置的间隔毫秒时间的余数小于1秒，触发闹铃。
						_option.repeatSetting != RepeatSetting.NoRepeat &&
						diff > _option.repeatSetting.milliseconds() &&
						diff % _option.repeatSetting.milliseconds() < 1000) {					
					printAlarm(now);					
				}
			}
			
			private void printAlarm(Date now) {
				trigerTimes++;
				System.out.println(String.format("闹铃时间：%1$s，重复设置：%2$s，当前时间：%3$s",
						_option.alarmTime.toString(), _option.repeatSetting, now));
			}
		};
		
		// 每一秒启动一次，看看预订的时间以便启动闹铃
		_timer.schedule(task, 0, 1000);
	}
	
	public void stop() {
		_timer.cancel();
	}
}
