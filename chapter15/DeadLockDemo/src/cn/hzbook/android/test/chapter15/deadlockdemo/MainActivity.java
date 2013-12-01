package cn.hzbook.android.test.chapter15.deadlockdemo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;

public class MainActivity extends Activity {

	final Object lock1 = new Object();
	final Object lock2 = new Object();

	Thread thread1 = new Thread(new Runnable() {
		@Override public void run() {
			synchronized (lock1) {
				System.out.println("线程1获取lock1");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}
				synchronized (lock2) {
					System.out.println("线程1获取lock2");
				}
			}
		}
	});

	Thread thread2 = new Thread(new Runnable() {
		@Override public void run() {
			synchronized (lock2) {
				System.out.println("线程2获取lock2");
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}
				synchronized (lock1) {
					System.out.println("线程2获取lock1");
				}
			}
		}
	});
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				thread1.start();
				thread2.start();
				v.setEnabled(false);
			}
		});
		
		/*
		final Object lock1 = new Object();
		final Object lock2 = new Object();
 
		Thread thread1 = new Thread(new Runnable() {
			@Override public void run() {
				synchronized (lock1) {
					System.out.println("线程1获取lock1");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {}
					synchronized (lock2) {
						System.out.println("线程1获取lock2");
					}
				}
			}
 
		});
		thread1.start();
 
		Thread thread2 = new Thread(new Runnable() {
			@Override public void run() {
				synchronized (lock2) {
					System.out.println("线程2获取lock2");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {}
					synchronized (lock1) {
						System.out.println("线程2获取lock1");
					}
				}
			}
		});
		thread2.start();
 
		try {
		    thread1.join();
		    thread2.join();
		} catch (InterruptedException e) {}

		Log.i("dead lock demo", "程序执行完毕，基本上不会发生！");
		*/
	}
}
