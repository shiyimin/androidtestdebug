package cn.hzbook.android.test.chapter5.alarm;

import java.util.Date;

public class SimpleGetDate implements IGetDate {
	public Date Now() {
		return new Date();
	}
}
