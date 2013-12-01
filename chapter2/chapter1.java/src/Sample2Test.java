import junit.framework.TestCase;

public class Sample2Test extends TestCase {
	public void test相加后整数溢出() {
		try {
			new Sample2().add(2147483640,  8);
			fail("2147483640和8相加后，会导致整数溢出，" +
			     "函数应该检测到这个问题并抛出异常通知！");
		} catch ( ArithmeticException e ) {
		}
	}
}
