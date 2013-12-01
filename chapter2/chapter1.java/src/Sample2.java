
public class Sample2 {
	public int add(int left, int right) {
		if ( Integer.MAX_VALUE - left < right ||
			 Integer.MAX_VALUE - right < left ) {
			throw new ArithmeticException(String.format("%d和%d相加会导致整数溢出！", left, right));
		}
		
		return left + right;
	}
}
