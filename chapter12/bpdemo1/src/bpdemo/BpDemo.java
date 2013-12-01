package bpdemo;

import java.util.HashMap;
import java.util.Random;

public class BpDemo {
	public static void main(String[] args) {
		hitCountDemo();

		conditionalBpDemo();
		
		variableLogicalStructureDemo();
		
		new BpDemo().changeResultValue();
		
		exceptionBpDemo();
		
		classLoadBpDemo();
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

	private static void variableLogicalStructureDemo() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("键1", "键1的值");
		map.put("键2", "键2的值");
		
		String value = map.get("键1");
	}

	private int mResult = 0;
	
	public int getResult() { return mResult; }
	
	public void setResult(int value) { mResult = value; }
	
	private void changeResultValue() {
		int value = getResult();
		setResult(value + 2);
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
}
