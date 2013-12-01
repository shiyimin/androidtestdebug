import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class 测试博客增删改用例集合 extends TestSuite {	
	public static Test suite() {
		return new TestSetup(new TestSuite(博客测试2.class)) {
			protected void setUp() {
				System.out.println("用户登录");
			}
			
			protected void tearDown() {
				System.out.println("用户注销");
			}
		};
	}
}
