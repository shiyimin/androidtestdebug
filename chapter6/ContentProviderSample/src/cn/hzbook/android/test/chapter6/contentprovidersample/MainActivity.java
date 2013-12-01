package cn.hzbook.android.test.chapter6.contentprovidersample;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        populateBookList();
        setButtonClickListeners();
    }

    private void populateBookList() {
		Log.i("DEBUG", "MainActivity.poplateBookList");
		Uri uri = BookContentProvider.BOOK_LIST_URI;
		// 在列表里，我们只显式书籍的名称，
		// ID是用来在查询书籍的时候用到
		// TODO: 需要写明如果查询没有_id列的时候，SimpleCursorAdapter会报错
		// 而且数据库里的表也一定要有_id列，而且类型必须是 INTEGER PRIMARY KEY AUTOINCREMENT!
		String[] projection = new String[] {
				BookContentProvider.Id,
				BookContentProvider.Title
		};

		// 根据书籍的名称排序，否则默认是根据主键排序
        String sortOrder = BookContentProvider.Title 
        		+ " COLLATE LOCALIZED ASC";
        
        // Cursor c = managedQuery(uri, projection, null, null, sortOrder);
        ContentResolver resolver = getContentResolver();
        Cursor c = resolver.query(uri, projection, null, null, sortOrder);
        String[] fields = new String[] {
        		BookContentProvider.Title
        };
        // 如果用最新的SimpleCursorAdapter（有flags参数的版本）
        // 在Android 2.2上没办法运行起来！
        @SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
        		this, 
        		R.layout.book_entry, // 显示书籍详细信息的界面 
        		c, // 从数据库抓取的数据列表游标
                fields, // 在列表上显示的列， 只显示书籍列
                new int[] {R.id.bookTitle} // book_entry里显示数据的控件
                );

        ListView bookList = (ListView) findViewById(R.id.book_list);
        bookList.setAdapter(adapter);
	}

	private void setButtonClickListeners() {
		Button button = (Button)findViewById(R.id.addBookButton);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				launchAddBook();
			}
		});
	}
	
	private void launchAddBook() {
        Intent i = new Intent(this, AddBookActivity.class);
        startActivity(i);
	}
}
