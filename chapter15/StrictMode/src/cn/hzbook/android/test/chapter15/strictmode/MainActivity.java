package cn.hzbook.android.test.chapter15.strictmode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;

public class MainActivity extends Activity {
	private boolean mInDevelopingMode = true;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if ( mInDevelopingMode ) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads()
				.detectDiskWrites()
				.detectNetwork()
				.penaltyLog().penaltyDialog().build());
		}
		
		File file = new File("test.txt");
		if ( !file.exists() ) {
			try {
				file.createNewFile();
			} catch ( IOException e ) {
			}
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("一段简短的消息");
			bw.close();			
		} catch ( Exception any ) {
		}
	}
}
