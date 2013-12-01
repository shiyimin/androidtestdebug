package cn.hzbook.android.test.chapter13.ddms;

import java.util.Date;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.content.ComponentName;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import de.akquinet.android.androlog.Log;
import cn.hzbook.android.test.chapter13.alarmservice.IRemoteService;

public class HelloAndroidActivity extends Activity {
    // Messenger myService = null;
    boolean _isBound;
    IRemoteService _remoteService = null;

    private ServiceConnection myConnection = new ServiceConnection() {
	    public void onServiceConnected(ComponentName className, 
					   IBinder service) {
		// myService = new Messenger(service);
		_remoteService = IRemoteService.Stub.asInterface(service);
		_isBound = true;
	    }

	    public void onServiceDisconnected(ComponentName className) {
		// myService = null;
	    }
	};

    private OnClickListener mStartListener = new OnClickListener() {
	    public void onClick(View v) {
		try {
		    String msg = 
			_remoteService.sayHello("ddmsdemo");
		    TextView text =
			(TextView)findViewById(R.id.txtMessage);
		    text.setText(msg);
		} catch ( RemoteException e ) {
		    Log.i(e.getMessage());
		}
	    }
	};

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initializes the logging
        Log.init();

        // Log a message (only on dev platform)
        Log.i(this, "onCreate");
        setContentView(R.layout.main);
	Button button = (Button) findViewById(R.id.btnStart);
	button.setOnClickListener(mStartListener);

	Date now = new Date();
	Intent intent = new Intent();
	intent.setClassName("cn.hzbook.android.test.chapter13.alarmservice",
			    "cn.hzbook.android.test.chapter13.alarmservice.AlarmService");
	intent.putExtra("TIME", 
			new Date(now.getYear(), 
				 now.getMonth(),
				 now.getDay(), 
				 now.getHours(), 
				 now.getMinutes() + 1, 
				 0));
	bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
	_isBound = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (_isBound) {
            unbindService(myConnection);
	    _isBound = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (_isBound) {
            unbindService(myConnection);
	    _isBound = false;
        }
    }
}