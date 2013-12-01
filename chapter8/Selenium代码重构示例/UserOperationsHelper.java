package temp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserOperationsHelper {
	private WebDriver driver;
	private String baseUrl = "https://passport.csdn.net";

	public UserOperationsHelper(WebDriver driver) {
		this.driver = driver;
	}

	public void logOn(String username, String password)
			throws CaseErrorException {
		// string.Empty留出来为测试目的服务
		if (username == null)
			throw new CaseErrorException(new IllegalArgumentException(
					"username"));
		if (password == null)
			throw new CaseErrorException(new IllegalArgumentException(
					"password"));

		driver.get(baseUrl + "/account/login");
		driver.findElement(
				By.id(Constants.登录页面.用户名文本框)).clear();
		driver.findElement(
				By.id(Constants.登录页面.用户名文本框)).sendKeys(username);
		driver.findElement(
				By.id(Constants.登录页面.密码文本框)).clear();
		driver.findElement(
				By.id(Constants.登录页面.密码文本框)).sendKeys(password);
		driver.findElement(
				By.id(Constants.登录页面.记住我复选框)).click();
		driver.findElement(
				By.cssSelector(Constants.登录页面.登录按钮)).click();
	}
}
