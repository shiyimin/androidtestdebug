package cn.hzbook.android.test.chapter1;

import cn.hzbook.android.test.chapter1.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class BookDetails extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.bookdetails);
	
	final Intent intent = getIntent();
	final String action = intent.getAction();
	TextView text = null;
	
	if ( Intent.ACTION_VIEW.equals(action) ) {
	    text = (TextView)findViewById(R.id.title);
	    String value = intent.getStringExtra("title");
	    text.setText(value);
	    
	    text = (TextView)findViewById(R.id.author);
	    value = intent.getStringExtra("author");
	    text.setText(value);   

	    text = (TextView)findViewById(R.id.copyright);
	    value = intent.getStringExtra("copyright");
	    text.setText(value);
	}

	View.OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) { onEdit(v, EditFlags.EDIT_TITLE); }
	    };
	registerListener(R.id.title_label, listener);
	registerListener(R.id.title, listener);

	listener = new View.OnClickListener() {
		public void onClick(View v) { onEdit(v, EditFlags.EDIT_AUTHOR); }
	    };
	registerListener(R.id.author_label, listener);
	registerListener(R.id.author, listener);

	listener = new View.OnClickListener() {
		public void onClick(View v) { onEdit(v, EditFlags.EDIT_COPYRIGHT); }
	    };
	registerListener(R.id.copyright_label, listener);
	registerListener(R.id.copyright, listener);

	listener = new View.OnClickListener() {
		public void onClick(View v) {onEdit(v, EditFlags.EDIT_ALL);}
	    };
	registerListener(R.id.btnEdit, listener);
	
	listener = new View.OnClickListener() {
		public void onClick(View v) { onBack(v); }
	    };
	registerListener(R.id.btnBack, listener);
    }

	private void registerListener(int id, View.OnClickListener listener) {
	View control = findViewById(id);
	control.setOnClickListener(listener);
    }

    protected void onBack(View v) {
	Intent i = new Intent(this, MainActivity.class);

	startActivityForResult(i, 0);
    }

    protected void onEdit(View v, int editFlag) {
	Intent i = new Intent(this, BookEditor.class);
	final String action = getIntent().getAction();

	if ( Intent.ACTION_VIEW.equals(action) ) {
	    i.setAction(Intent.ACTION_EDIT);
	    i.putExtra("title", ((TextView)findViewById(R.id.title)).getText());
	    i.putExtra("author", ((TextView)findViewById(R.id.author)).getText());
	    i.putExtra("copyright", ((TextView)findViewById(R.id.copyright)).getText());
	    
	    i.putExtra("indics", getIntent().getIntArrayExtra("indics"));
	} else {
	    i.setAction(action);
	}

	i.putExtra("flag", editFlag);
	
	startActivity(i);
    }
}
