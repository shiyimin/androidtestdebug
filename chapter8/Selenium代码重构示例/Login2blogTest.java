package temp;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Login2blogTest {
	private WebDriver driver;
	private UserOperationsHelper helper;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts()
				.implicitlyWait(Constants.页面加载最大容忍时间, TimeUnit.SECONDS);
		helper = new UserOperationsHelper(driver);
	}

	@Test
	public void testLogin2blog() throws Exception {
		helper.logOn("donjuan", "password");
		driver.findElement(By.linkText("博客")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
