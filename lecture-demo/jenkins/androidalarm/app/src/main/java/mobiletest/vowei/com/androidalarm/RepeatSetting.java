package mobiletest.vowei.com.androidalarm;

public enum RepeatSetting
{
	NoRepeat (0),
	EveryDay (1 * 24 * 60 * 60 * 1000),
	EveryWeek (7 * 24 * 60 * 60 * 1000),
	EveryMonth (30 * 24 * 60 * 60 * 1000);
	
	private final long _milliseconds;
	RepeatSetting(long milliseconds) {
		_milliseconds = milliseconds;
	}
	
	long milliseconds () { return _milliseconds; }
}