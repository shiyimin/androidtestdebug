package cn.hzbook.android.test.chapter13.alarmservice;

import java.util.Date;

public class SimpleGetDate implements IGetDate {
	public Date Now() {
		return new Date();
	}
}
