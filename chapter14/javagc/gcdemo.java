import java.lang.Thread;

public class gcdemo {
    public static void main(String[] args) throws Exception {
	generateGarbage();
	System.out.println("第一次gc，找出垃圾对象DemoClass！");
	System.gc();
	Thread.sleep(1000);

	System.out.println("第二次gc，调用完finalize之后回收垃圾！");
	System.gc();
	Thread.sleep(1000);

	System.out.println("第三次gc！");
	System.gc();
	Thread.sleep(1000);
    }

    public static void generateGarbage() {
	DemoClass g = new DemoClass();
	g.X = 123;
	g.testMethod();
    }
}

class DemoClass {
    public int X;

    public void testMethod() {
	System.out.println("X: " + new Integer(X).toString());
    }
    
    @Override
    protected void finalize () throws Throwable {
	System.out.println("finalize函数被调用了！");
	// 实现自定义的资源清除逻辑！
	super.finalize();
    }
}