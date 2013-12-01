package cn.hzbook.android.test.chapter5.alarm.test;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import cn.hzbook.android.test.chapter5.alarm.Option;
import cn.hzbook.android.test.chapter5.alarm.RepeatSetting;
import cn.hzbook.android.test.chapter5.alarm.TestableAlarm;

public class AlarmTest {
	@SuppressWarnings("deprecation")
	@Test
	public void 使用自定义的IGetDate对象测试() throws InterruptedException {
		Option option = new Option();
		option.alarmTime = new Date(1, 12, 15, 16, 15, 0);
		TestableAlarm alarm = new TestableAlarm(option, 
				new TestGetDate(new Date(1, 12, 15, 16, 15, 0)));
		alarm.start();
		// 休眠2秒钟，确保计时器顺利执行！
		Thread.sleep(2000);		
		alarm.stop();		
		assertEquals(1, alarm.trigerTimes);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void 使用自定义的IGetDate对象测试重复设置() throws InterruptedException {
		Option option = new Option();
		option.alarmTime = new Date(1, 12, 15, 16, 15, 0);
		option.repeatSetting = RepeatSetting.EveryDay;
		// 通过设置时间为2012-15-16日，来判断是否遵循每日重复的设置
		TestableAlarm alarm = new TestableAlarm(option, 
				new TestGetDate(new Date(1, 12, 16, 16, 15, 0)));
		alarm.start();
		// 休眠2秒钟，确保计时器顺利执行！
		Thread.sleep(2000);		
		alarm.stop();		
		assertEquals(1, alarm.trigerTimes);
	}
}
