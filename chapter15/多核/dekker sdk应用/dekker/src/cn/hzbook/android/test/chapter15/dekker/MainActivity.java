package cn.hzbook.android.test.chapter15.dekker;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final int loopCount = 10000;
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

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int expected_sum = 2 * loopCount;
		if ( _sharedCounter != expected_sum ) {
		     System.out.println(
			     String.format("��Դ��������: ʵ�ʽ�� %d ������������ %d", _sharedCounter, expected_sum));
		} else {
			System.out.println("Dekker���Թ�����");
		}
	}
}
