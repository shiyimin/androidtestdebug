package cn.hzbook.android.test.chapter5.alarm;

import java.util.Date;

public interface IGetDate {
	// 获取现在的时间
	// 通过提取接口的方式，便于在测试时返回任意的时间。
	Date Now();
}
