package cn.hzbook.android.test.chapter1;

import java.util.ArrayList;
import java.util.List;

import cn.hzbook.android.test.chapter1.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class BookEditor extends Activity {
    // private boolean _addNew = false;
    
    @Override protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.bookeditor);
	
	final Intent intent = getIntent();
	final String action = intent.getAction();
	if ( Intent.ACTION_EDIT.equals(action) ) {
	    int flag = intent.getIntExtra("flag", EditFlags.EDIT_ALL);

	    EditText text = null;
	    String value = null;
	    if ( (flag & EditFlags.EDIT_TITLE) == EditFlags.EDIT_TITLE ) {
		text = (EditText)findViewById(R.id.title);
		value = intent.getStringExtra("title");
		text.setText(value);
	    }
	    
	    if ( (flag & EditFlags.EDIT_AUTHOR) == EditFlags.EDIT_AUTHOR ) {
		text = (EditText)findViewById(R.id.author);
		value = intent.getStringExtra("author");
		text.setText(value);   
	    }

	    if ( (flag & EditFlags.EDIT_COPYRIGHT) == EditFlags.EDIT_COPYRIGHT ) {
		text = (EditText)findViewById(R.id.copyright);
		value = intent.getStringExtra("copyright");
		text.setText(value);
	    }
	} /* else if (Intent.ACTION_INSERT.equals(action)) {
	    _addNew = true;
	    } */
	
	Button button = (Button)findViewById(R.id.btnOk);
	button.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {onList(v);}
	    });
    }

    protected void onList(View v) {
	Intent i = new Intent(this, BookDetails.class);	    
	Intent iin = getIntent();
	int flag = iin.getIntExtra("flag", EditFlags.EDIT_ALL);
	String title = iin.getStringExtra("title");
	String author = iin.getStringExtra("author");
	String copyright = iin.getStringExtra("copyright");
	int[] indics = iin.getIntArrayExtra("indics");
	
	if ( indics == null ) {
	    EditText text = null;

	    if ( (flag & EditFlags.EDIT_AUTHOR) == EditFlags.EDIT_AUTHOR ) {
		text = (EditText)findViewById(R.id.author);
		author = text.getText().toString();
		MainActivity.getAuthors().add(author);
	    }

	    if ( (flag & EditFlags.EDIT_TITLE) == EditFlags.EDIT_TITLE ) {
		text = (EditText)findViewById(R.id.title);
		title = text.getText().toString();
		List<String> newList = new ArrayList<String>();
		newList.add(title);
		MainActivity.getBooks().add(newList);
	    }
	} else {
	    EditText text = null;
	    if ( (flag & EditFlags.EDIT_TITLE) == EditFlags.EDIT_TITLE ) {
		text = (EditText)findViewById(R.id.title);
		title = text.getText().toString();
		MainActivity.getBooks().get(indics[0]).set(indics[1], title);
	    }
	    
	    if ( (flag & EditFlags.EDIT_AUTHOR) == EditFlags.EDIT_AUTHOR ) {
		text = (EditText)findViewById(R.id.author);
		author = text.getText().toString(); 
		MainActivity.getAuthors().set(indics[0], author);
	    }  
	}

	i.setAction(Intent.ACTION_VIEW);
	i.putExtra("author", author);
	i.putExtra("title", title);
	i.putExtra("copyright", copyright); 
	i.putExtra("indics", indics);

	startActivity(i);
    }
}
