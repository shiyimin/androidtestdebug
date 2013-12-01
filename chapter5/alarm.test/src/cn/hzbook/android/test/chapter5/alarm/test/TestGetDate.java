package cn.hzbook.android.test.chapter5.alarm.test;

import java.util.Date;
import cn.hzbook.android.test.chapter5.alarm.IGetDate;

public class TestGetDate implements IGetDate {
	private Date _fake;
	
	public TestGetDate(Date fake) {
		_fake = fake;
	}
	
	@Override
	public Date Now() {
		return _fake;
	}
}
