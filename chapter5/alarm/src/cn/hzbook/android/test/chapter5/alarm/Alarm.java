package cn.hzbook.android.test.chapter5.alarm;

import java.util.*;

public class Alarm {
	Timer _timer;
	Option _option;
	
	public Alarm(Option option) {
		_timer = new Timer();
		_option = option;
	}
	
	public void start() {
		TimerTask task = new TimerTask() {
			public void run() {
				System.out.println(String.format("闹铃时间：%1$s，重复设置：%2$s，当前时间：%3$s",
						_option.alarmTime.toString(), _option.repeatSetting, new Date()));
			}
		};
		
		if ( _option.repeatSetting == RepeatSetting.NoRepeat ) {
			_timer.schedule(task, _option.alarmTime);			
		} else {
			_timer.schedule(task, _option.alarmTime, _option.repeatSetting.milliseconds());
		}
	}
	
	public void stop() {
		_timer.cancel();
	}
}
