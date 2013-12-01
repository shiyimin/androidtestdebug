package cn.hzbook.android.test.chapter5.alarm.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import cn.hzbook.android.test.chapter5.alarm.IGetDate;
import cn.hzbook.android.test.chapter5.alarm.Option;
import cn.hzbook.android.test.chapter5.alarm.RepeatSetting;
import cn.hzbook.android.test.chapter5.alarm.TestableAlarm;

public class AlarmMockTest {
	@SuppressWarnings("deprecation")
	@Test
	public void 使用模拟IGetDate对象测试() throws InterruptedException {
		Option option = new Option();
		option.alarmTime = new Date(1, 12, 15, 16, 15, 0);
		option.repeatSetting = RepeatSetting.EveryDay;
		
		Mockery context = new Mockery();
		final IGetDate gd = context.mock(IGetDate.class);
		context.checking(new Expectations() {{
			atLeast(1).of(gd).Now(); will(returnValue(new Date(1, 12, 15, 16, 15, 0)));
		}});
		
		TestableAlarm alarm = new TestableAlarm(option, gd);
		alarm.start();
		// 休眠2秒钟，确保计时器顺利执行！
		Thread.sleep(2000);		
		alarm.stop();		
		assertEquals(1, alarm.trigerTimes);
		context.assertIsSatisfied();
	}
}
