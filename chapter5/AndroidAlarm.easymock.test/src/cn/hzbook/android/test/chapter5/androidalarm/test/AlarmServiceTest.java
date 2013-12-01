package cn.hzbook.android.test.chapter5.androidalarm.test;

import java.util.Date;

import org.easymock.EasyMock;

import cn.hzbook.android.test.chapter5.androidalarm.Option;
import cn.hzbook.android.test.chapter5.androidalarm.RepeatSetting;
import cn.hzbook.android.test.chapter5.androidalarm.TestableAlarm;
import cn.hzbook.android.test.chapter5.androidalarm.IGetDate;
import cn.hzbook.android.test.chapter5.androidalarm.AlarmService;

import android.content.Intent;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

public class AlarmServiceTest extends ServiceTestCase<AlarmService> {
	public AlarmServiceTest() {
		super(AlarmService.class);
	}

	@SmallTest
	public void testAlarmService() throws InterruptedException {
		Intent startIntent = new Intent(getContext(),AlarmService.class);
		startIntent.putExtra("TIME", new Date().getTime());
		startService(startIntent);
		
		AlarmService service = (AlarmService)getService();
		final IGetDate gd = EasyMock.createMock(IGetDate.class);
		EasyMock.expect(gd.Now()).andReturn(new Date(2111, 12, 15, 16, 15, 0)).atLeastOnce();
		EasyMock.replay(gd);
		
		service.getAlarm().setIGetDate(gd);
		Thread.sleep(2000);		
		assertEquals(1, service.getAlarm().trigerTimes);
		EasyMock.verify(gd);		
	}
	
	// TODO: 把下面的链接添加到书里
	// http://stackoverflow.com/questions/5686194/how-to-mock-getapplicationcontext
	// http://cncc.bingj.com/cache.aspx?q=android-testing-mock-contexts&d=4882987786962439&mkt=zh-CN&setlang=zh-CN&w=tLxH_Shz8VGshqs5g_9JC3UMfC5Ogyxx
    @MediumTest
	public void testAlarm() throws InterruptedException {
		Option option = new Option();
		option.alarmTime = new Date(1, 12, 15, 16, 15, 0);
		option.repeatSetting = RepeatSetting.EveryDay;
				
		final IGetDate gd = EasyMock.createMock(IGetDate.class);
		EasyMock.expect(gd.Now()).andReturn(new Date(1, 12, 15, 16, 15, 0)).atLeastOnce();
		
		// TODO: 要特别强调如果不调用replay的话，就会出NullReferenceException的问题
		EasyMock.replay(gd);
		
		TestableAlarm alarm = new TestableAlarm(option, gd, this.getContext());
		alarm.start();
		// 休眠2秒钟，确保计时器顺利执行！
		Thread.sleep(2000);		
		alarm.stop();		
		assertEquals(1, alarm.trigerTimes);
		EasyMock.verify(gd);
	}
}
