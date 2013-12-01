package cn.hzbook.android.test.chapter6.contentprovidersample.test;

import cn.hzbook.android.test.chapter6.contentprovidersample.BookContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

public class BookContentProviderTestCase extends
		ProviderTestCase2<BookContentProvider> {
	public BookContentProviderTestCase() {
		super(BookContentProvider.class, BookContentProvider.AUTHORITY);
	}

	public void test测试添加insert函数() {
		Uri newRowUri = null;

		try {
			newRowUri = 添加测试数据("test测试添加insert函数");
			ContentResolver resolver = getMockContentResolver();
			Cursor cursor = resolver.query(newRowUri, null, null, null, null);
			assertTrue(cursor.moveToNext());
		} finally {
			删除数据(newRowUri);
		}
	}

	public void test测试查询query函数() {
		Uri newRowUri = 添加测试数据("test测试查询query函数");
		try {
			Uri uri = BookContentProvider.BOOK_URI;
			ContentResolver resolver = getMockContentResolver();
			String[] projection = new String[] {
					BookContentProvider.Id,
					BookContentProvider.Author };
			
			String selection = String.format("%1$s LIKE ?",
					BookContentProvider.Title);
			String[] selectionArgs = new String[] { "test测试查询query函数%" };
			/*
			String selection = String.format("%1$s = ?",
					BookContentProvider.Title);
			String[] selectionArgs = new String[] { "test测试查询query函数标题" };
			*/
			Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, null);
			int occurrence = 0;
			while ( cursor.moveToNext() ) {
				occurrence++;
				assertEquals(2, cursor.getColumnCount());
				
				String expected = newRowUri.getLastPathSegment();
				String actual = Long.toString(cursor.getInt(
						cursor.getColumnIndex(BookContentProvider.Id)));
				assertEquals(expected, actual);

				expected = "test测试查询query函数作者";
				actual = cursor.getString(
						cursor.getColumnIndex(BookContentProvider.Author));
				assertEquals(expected, actual);
			}
			
			assertEquals(1, occurrence);
		} finally {
			// 因为ProviderTestCase2在setUp函数里，每次在测试用例运行之前
			// 就会重建一次数据库，因此在最终不删除数据也没有关系
			//
			// 删除数据(newRowUri);			
		}
	}

	public void test测试删除delete函数() {
		Uri newRowUri = 添加测试数据("test测试查询query函数");
		删除数据(newRowUri);
		assertFalse(getMockContentResolver().query(newRowUri, null, null, null, null).moveToNext());
	}
	
	private void 删除数据(Uri uri) {
		ContentResolver resolver = getMockContentResolver();
		resolver.delete(uri, null, null);
	}

	private Uri 添加测试数据(String 前缀) {
		Uri uri = BookContentProvider.BOOK_URI;
		ContentResolver resolver = getMockContentResolver();
		ContentValues values = new ContentValues();
		values.put(BookContentProvider.Title, 前缀 + "标题");
		values.put(BookContentProvider.Author, 前缀 + "作者");
		return resolver.insert(uri, values);
	}
}
