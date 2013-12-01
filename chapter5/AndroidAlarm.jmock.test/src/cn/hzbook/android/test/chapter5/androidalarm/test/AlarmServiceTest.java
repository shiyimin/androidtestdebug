package cn.hzbook.android.test.chapter5.androidalarm.test;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;

import android.content.Intent;
import android.test.ServiceTestCase;
import cn.hzbook.android.test.chapter5.androidalarm.IGetDate;
import cn.hzbook.android.test.chapter5.androidalarm.Option;
import cn.hzbook.android.test.chapter5.androidalarm.RepeatSetting;
import cn.hzbook.android.test.chapter5.androidalarm.TestableAlarm;
import cn.hzbook.android.test.chapter5.androidalarm.AlarmService;

// TODO: 需要在书里记录jmock两个依赖库在打包的时候，会因为有重复的license.txt文件而停止工作的解决方案
public class AlarmServiceTest  extends ServiceTestCase<AlarmService> {
	public AlarmServiceTest() {
		super(AlarmService.class);
	}
	
	// 如果要重现NullReferenceException异常的话，请删除AndroidAlarm工程里AlarmService
	// 的onStart实现。
	public void testExpectDestoryServiceFail() throws InterruptedException {
		Intent startIntent = new Intent(getContext(),AlarmService.class);
		startIntent.putExtra("TIME", new Date().getTime());
		startService(startIntent);	
	}
	
	public void testAlarmService() throws InterruptedException {		
		Intent startIntent = new Intent(getContext(),AlarmService.class);
		startIntent.putExtra("TIME", 
				new Date(2111, 12, 15, 16, 15, 0).getTime());
		startService(startIntent);
		
		AlarmService service = (AlarmService)getService();
		Mockery context = new Mockery();
		final IGetDate gd = context.mock(IGetDate.class);
		context.checking(new Expectations() {{
			atLeast(1).of(gd).Now(); will(returnValue(
					new Date(2111, 12, 15, 16, 15, 0)));
		}});
		
		service.getAlarm().setIGetDate(gd);
		Thread.sleep(2000);		
		assertEquals(1, service.getAlarm().trigerTimes);
		context.assertIsSatisfied();
	}

	public void testAlarm2() throws InterruptedException {
		Option option = new Option();
		option.alarmTime = new Date(1, 12, 15, 16, 15, 0);
		option.repeatSetting = RepeatSetting.EveryDay;
		
		Mockery context = new Mockery();
		final IGetDate gd = context.mock(IGetDate.class);
		context.checking(new Expectations() {{
			atLeast(1).of(gd).Now(); will(returnValue(new Date(1, 12, 15, 16, 15, 0)));
		}});
		
		TestableAlarm alarm = new TestableAlarm(option, gd, this.getContext());
		alarm.start();
		// 休眠2秒钟，确保计时器顺利执行！
		Thread.sleep(2000);		
		alarm.stop();		
		assertEquals(1, alarm.trigerTimes);
		context.assertIsSatisfied();
	}
}
