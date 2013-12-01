public class Dekker {	
    private static volatile int _flag1 = 0;
	private static volatile int _flag2 = 0;
	private static volatile int _turn = 1;
	private static volatile int _sharedCounter = 0;

    private static void dekker1() {
	    _flag1 = 1;
		_turn = 2;
		while ((_flag2 == 1) && (_turn == 2));
		_sharedCounter++;
		_flag1 = 0;
	}

	private static void dekker2() {
	    _flag2 = 1;
		_turn = 1;
		while ((_flag1 == 1) && (_turn == 1));
		_sharedCounter++;
		_flag2 = 0;
	}
	
	public static void main(String[] args) throws Exception {
	    if ( args.length != 1 ) {
		     System.out.println("使用方法: java Dekker <循环次数>");
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
		} else { 
                     System.out.println("Dekker算法可以使用。");
                }
	}
}
