package cn.hzbook.android.test.chapter5.androidalarm;

import java.util.Date;

public class SimpleGetDate implements IGetDate {
	public Date Now() {
		return new Date();
	}
}
