package cn.hzbook.android.test.chapter5.alarm;

import java.io.IOException;
import java.text.*;
import gnu.getopt.Getopt;

public class Program {	
	private static void printHelp() {
		System.out.println("使用方法：");
		System.out.println("alarm -a <闹铃时间> -r <D|W|M>");
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		// 运行程序需要指定参数，否则就打印帮助信息
		if ( args.length == 0 ) {
			printHelp();
			return;
		}
		
		// 创建一个闹铃设置对象
		Option option = new Option();
		// 默认情况下闹铃不重复
		option.repeatSetting = RepeatSetting.NoRepeat;
		// 创建一个将字符串格式的日期解析成Java日期实例的对象。
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// Getopt是用来解析命令行参数的
		// 其具体用法请参见：
		// www.urbanophile.com/arenn/hacking/getopt/gnu.getopt.Getopt.html
		Getopt g = new Getopt("alarm", args, "a:r:");
		int c;
		String arg;
		// 通过循环解析命令行传入的每一个参数并填充闹铃设置。
		while ((c = g.getopt()) != -1) {
			switch (c) {
			// -a表示闹铃时间设置。
			case 'a':
				// 其后需要跟有一个格式为“yyyy-MM-dd hh:mm:ss”的日期字符串
				// 如：-a "2012-12-15 16:15:00"
				arg = g.getOptarg();
				// 将字符串格式的日期解析成一个Java可理解的对象
				option.alarmTime = format.parse(arg);
				break;
				
				// -r 表示闹铃重复设置
			case 'r':
				// 其后只能跟有字符"D“、”W“、”M“
				arg = g.getOptarg();
				// 字符”D“表示每日重复
				if ( "D".compareToIgnoreCase(arg) == 0 ) {
					option.repeatSetting = RepeatSetting.EveryDay;
				} else if ( "W".compareToIgnoreCase(arg) == 0 ) {
					// 字符”W“表示每周重复
					option.repeatSetting = RepeatSetting.EveryWeek;
				} else if ( "M".compareToIgnoreCase(arg) == 0 ) {
					// 字符”M“表示每月重复
					option.repeatSetting = RepeatSetting.EveryMonth;
				}
				break;
				
			default:
				printHelp();
			}
		}
		
		// 去掉下面一行注释，并将TestableAlarm注释去掉，就可以看使用
		// Timer自身提供的功能实现的闹钟效果
		// 使用闹铃设置对象创建闹铃
		// Alarm alarm = new Alarm(option);
		TestableAlarm alarm = new TestableAlarm(option, new SimpleGetDate());
		// 启动闹铃
		alarm.start();
		
		// 一直运行直到用户关闭程序
		System.out.println("按任意键退出程序！");
		System.in.read();
		// 用户打算关闭程序，执行清理操作
		alarm.stop();
	}
}