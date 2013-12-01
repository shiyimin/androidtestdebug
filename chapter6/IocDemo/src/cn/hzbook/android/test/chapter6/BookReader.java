package cn.hzbook.android.test.chapter6;

public class BookReader implements IBookReader {
	@Override
	public Book readBook(String[] columns) {
		return new Book(columns[0], columns[1]);
	}
}
