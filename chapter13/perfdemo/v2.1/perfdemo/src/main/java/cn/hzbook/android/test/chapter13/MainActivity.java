package cn.hzbook.android.test.chapter13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private StockPriceItemRaw[] _stockPrices = null;
    
    @Override protected void onCreate(Bundle savedInstanceState) {
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
	ArrayAdapter<StockPriceItemRaw> adapter = new ArrayAdapter<StockPriceItemRaw>(this,
										      android.R.layout.simple_list_item_1, _stockPrices);
	listview.setAdapter(adapter);
	long endTime = System.nanoTime();

        Log.i("V2.1", "ReadCsv函数的使用时间： " + (readCsvEndTime - startTime));
	Log.i("V2.1", "Sort函数的使用时间： " + (sortEndTime - readCsvEndTime));
	Log.i("V2.1", "onCreate函数整体使用时间： " + (endTime - startTime));
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
     	    StockPriceItem[] forSort = new StockPriceItem[_stockPrices.length];
	    for ( int i = 0; i < _stockPrices.length; ++i ) {
	        StockPriceItem item = new StockPriceItem();
	        item.High = Float.parseFloat(_stockPrices[i].High);
	        forSort[i] = item;
	    }
	
	    for ( int i = 0; i < forSort.length; ++i ) {
		for ( int j = i; j < forSort.length; ++j ) {
		    if ( forSort[i].High < forSort[j].High ) {
			StockPriceItem temp = forSort[i];
			forSort[i] = forSort[j];
			forSort[j] = temp;

			StockPriceItemRaw raw = _stockPrices[i];
			_stockPrices[i] = _stockPrices[j];
			_stockPrices[j] = raw;
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
	    
	    String line = reader.readLine();
	    Boolean isFirstLine = true;		    

	    List<StockPriceItemRaw> stockPrices = new ArrayList<StockPriceItemRaw>();	    
	    while (line != null) {
		if ( isFirstLine ) {
		    isFirstLine = false;
		} else {
		    String[] parts = line.split(",");
		    StockPriceItemRaw item = new StockPriceItemRaw();
		    
		    item.Date = parts[0];
		    item.Open = parts[1];
		    item.High = parts[2];
		    item.Low = parts[3];
		    item.Close = parts[4];
		    item.Volume = parts[5];
		    item.AdjClose = parts[6];
		    stockPrices.add(item);
		}
		
		line = reader.readLine(); 
	    }
	    
	    _stockPrices = stockPrices.toArray(new StockPriceItemRaw[0]);
	    reader.close();
	} catch (IOException e) {
	    //log the exception
	}
    }
    
    public class HighComparator implements Comparator<StockPriceItem> {
	public int compare(StockPriceItem o1, StockPriceItem o2) {
	    return o1.High - o2.High > 0 ? 1 : -1;
	}
    }
    
    public class StockPriceItemRaw {
	public String Date;
	public String Volume;
	public String Open;
	public String High;
	public String Low;
	public String Close;
	public String AdjClose;

	@Override public String toString()
	{
	    return String.format("[%s]: Open - %s, High - %s, Low - %s, Close - %s.", 
				 Date, Open, High, Low, Close);
	}
    }   
}
