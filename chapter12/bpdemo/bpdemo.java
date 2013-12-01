import java.util.*;

public class bpdemo {
	private int mResult = 0;
	
	public int getResult() { return mResult; }
	
	public void setResult(int value) { mResult = value; }
	
	private void changeResultValue() {
		int value = getResult();
		setResult(value + 2);
	}
	
	public static void main(String[] args) {
		hitCountDemo();

		conditionalBpDemo();
		
		new bpdemo().changeResultValue();

		System.out.println(String.format("exceptionBpDemo returns: %1$b",
				exceptionBpDemo()));

		classLoadBpDemo();

		dropToFrameDemo();
	}

	private static void hitCountDemo() {
		for (int i = 0; i < 100; ++i) {
			System.out.println(i);
		}
	}

	private static void conditionalBpDemo() {
		Random r = new Random();
		boolean terminateIt = false;

		while (!terminateIt) {
			terminateIt = r.nextBoolean();
			System.out.println(terminateIt);
		}
	}

	private static void buggyMethod() {
		throw new IllegalStateException();
	}

	private static boolean exceptionBpDemo() {
		try {
			buggyMethod();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static void classLoadBpDemo() {
		ClbDemoClass.testMethod();
	}

	private static void printResult(int i) {
		for (int j = 0; j < i; ++j) {
			System.out.println(i * j);
		}
	}

	private static void dropToFrameDemo() {
		for (int i = 0; i < 10; ++i) {
			printResult(i);
		}
	}
}
