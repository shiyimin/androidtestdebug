package mobiletest.vowei.com.androidalarm;

import java.util.Date;

public class SimpleGetDate implements IGetDate {
	public Date Now() {
		return new Date();
	}
}
