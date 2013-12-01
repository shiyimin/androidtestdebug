package cn.hzbook.android.test.chapter6;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class BookStore implements IStore {
	private Book[] _allBooks;

	public void init() throws DataInitException {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("books.txt"));
			String[] nextLine;
			List<Book> books = new ArrayList<Book>();
			while ((nextLine = reader.readNext()) != null) {
				books.add(new Book(nextLine[0], nextLine[1]));
			}

			_allBooks = books.toArray(new Book[books.size()]);
		} catch (IOException e) {
			throw new DataInitException();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
			}
		}
	}

	public Book[] loadAll() {
		return _allBooks;
	}
}
