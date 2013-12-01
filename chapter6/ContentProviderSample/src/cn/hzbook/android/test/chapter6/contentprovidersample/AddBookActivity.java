package cn.hzbook.android.test.chapter6.contentprovidersample;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddBookActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        
        Button button = (Button)findViewById(R.id.add_book_button);
        button.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		insertNewBook();
        		launchBookLister();
        	}
        });
    }
    
    private void insertNewBook() {
    	ContentResolver resolver = getContentResolver();
    	Uri uri = BookContentProvider.BOOK_URI;
    	ContentValues values = new ContentValues();
    	
    	// 找到界面上的标题编辑文本框，并将用户的输入传给ContentValues
    	EditText et = (EditText)findViewById(R.id.title_edit_text);
    	values.put(BookContentProvider.Title, et.getText().toString());
    	// 把界面上的作者文本框的值放到ContentValues里
    	et = (EditText)findViewById(R.id.author_edit_text);
    	values.put(BookContentProvider.Author, et.getText().toString());
    	
    	resolver.insert(uri, values);
    }
    
	private void launchBookLister() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
	}
}
