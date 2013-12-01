import junit.framework.Test;
import junit.framework.TestSuite;

public class SampleSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("chapter1.java工程里的所有测试");

		suite.addTestSuite(Sample1Test.class);
		suite.addTest(TestSuite.createTest(Sample2Test.class, "test相加后整数溢出"));
		return suite;
	}
}
