// 编译命令：
//    javac DeadLockDemo.java
//

public class DeadLockDemo {
    public static void main(String[] args) {
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

		System.out.println("程序执行完毕，基本上不会发生！");
    }
}
