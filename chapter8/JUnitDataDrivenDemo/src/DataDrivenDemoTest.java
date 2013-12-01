import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DataDrivenDemoTest {
	private String _user;
	private String _password;

	public DataDrivenDemoTest(String user, String password) {
		_user = user;
		_password = password;
	}
	
	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection data() {
		return Arrays.asList(new Object[][] {
				{ "username1", "password1" },
				{ "username2", "password2" }
		});
	}
	
	@Test
	public void 测试用户登录() {
		System.out.println("使用用户名：" + _user + ", 密码：" + _password + " 登录");
	}
}
