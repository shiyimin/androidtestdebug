import java.lang.Thread;

public class gcdemo {
    public static void main(String[] args) throws Exception {
	generateGarbage();
	System.gc();
	Thread.sleep(1000);

	System.gc();
	Thread.sleep(1000);

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
	super.finalize();
    }
}