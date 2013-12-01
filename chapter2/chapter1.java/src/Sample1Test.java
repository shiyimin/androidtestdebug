import junit.framework.TestCase;

public class Sample1Test extends TestCase {
	public void testAdd() {
		assertEquals(3, new Sample1().add(1, 2));
	}
}
