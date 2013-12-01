package cn.hzbook.android.test.chapter13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private List<StockPriceItem> _stockPrices = new ArrayList<StockPriceItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Debug.startMethodTracing("tracedemo");
		long startTime = System.nanoTime();
		ReadCsv();
		long readCsvEndTime = System.nanoTime();

		SortPriceFromHighestToLowest("High");
		long sortEndTime = System.nanoTime();

		Debug.stopMethodTracing();
		
		final ListView listview = (ListView) findViewById(R.id.listview);
		ArrayAdapter<StockPriceItem> adapter = new ArrayAdapter<StockPriceItem>(this,
				android.R.layout.simple_list_item_1, _stockPrices);
		listview.setAdapter(adapter);
		long endTime = System.nanoTime();

		Log.i("V1", "ReadCsv函数的使用时间： " + (readCsvEndTime - startTime));
		Log.i("V1", "Sort函数的使用时间： " + (sortEndTime - readCsvEndTime));
		Log.i("V1", "onCreate函数整体使用时间： " + (endTime - startTime));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void SortPriceFromHighestToLowest(String column)
	{
		if ( column.compareTo("High") == 0 ) {
			// 使用冒泡法
			for ( int i = 0; i < _stockPrices.size(); ++i ) {
				for ( int j = i; j <  _stockPrices.size(); ++j ) {
					if ( _stockPrices.get(i).High < _stockPrices.get(j).High ) {
						StockPriceItem temp = _stockPrices.get(i);
						_stockPrices.set(i, _stockPrices.get(j));
						_stockPrices.set(j, temp);
					}
				}
			}
		}
	}
	
	private void ReadCsv()
	{		
		try {
		    BufferedReader reader = new BufferedReader(
		        new InputStreamReader(getAssets().open("stockprice.csv"), "UTF-8")); 
		    
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    String line = reader.readLine();
		    Boolean isFirstLine = true;		    
		    
		    while (line != null) {
		    	if ( isFirstLine ) {
		    		isFirstLine = false;
		    	} else {
			       String[] parts = line.split(",");
			       StockPriceItem item = new StockPriceItem();
			       
			       try {
				       item.Date = format.parse(parts[0]);
				       item.Open = Float.parseFloat(parts[1]);
				       item.High = Float.parseFloat(parts[2]);
				       item.Low = Float.parseFloat(parts[3]);
				       item.Close = Float.parseFloat(parts[4]);
				       item.Volume = Long.parseLong(parts[5]);
				       item.AdjClose = Float.parseFloat(parts[6]);
				       _stockPrices.add(item);
			       } catch ( ParseException ei ) {
			         // 忽略有错误日期的那一行
			       }
		    	}
		    	
		       line = reader.readLine(); 
		    }

		    reader.close();
		} catch (IOException e) {
		}
	}
}
