//
// 编译命令：
//     javac -encoding UTF8 RaceConditionFix.java
//
public class RaceConditionFix {
	private static int _sharedCounter = 0;

    private synchronized static void dekker1() {
		_sharedCounter++;
	}

	private synchronized static void dekker2() {
		_sharedCounter++;
	}
	
	public static void main(String[] args) throws Exception {
	    if ( args.length != 1 ) {
		     System.out.println("使用方法: java RaceCondition <循环次数>");
			 return;
		}
		
		final int loopCount = Integer.parseInt(args[0]);
		Thread thread1 = new Thread(new Runnable() {
		    public void run() {
				for ( int i = 0; i < loopCount; ++i ) {
					dekker1();
				}
			}
		});
		Thread thread2 = new Thread(new Runnable() {
		    public void run() {
				for ( int i = 0; i < loopCount; ++i ) {
					dekker2();
				}
			}
		});
		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		int expected_sum = 2 * loopCount;
		if ( _sharedCounter != expected_sum ) {
		     System.out.println(
			     String.format("资源竞争问题: 实际结果 $1%d 不等于期望结果 $2%d", _sharedCounter, expected_sum));
		}
	}
}