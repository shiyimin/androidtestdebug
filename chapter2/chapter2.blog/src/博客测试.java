import junit.framework.TestCase;

public class 博客测试 extends TestCase {
	public void setUp() {
		System.out.println("用户登录");
	}
	
	public void tearDown() {
		System.out.println("用户注销");
	}
	
	public void test新增博客文章() {
		System.out.println("博客文章已添加！");
	}
	
	public void test修改博客() {
		System.out.println("博客文章已被修改！");
	}
	
	public void test删除博客() {
		System.out.println("查询要删除的博客文章！");
		fail("找不到博客文章！");
		System.out.println("博客文章已删除！");
	}
}
