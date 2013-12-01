package cn.hzbook.android.test.chapter13;

import java.util.Date;

public class StockPriceItem {
	public Date Date;
	
	public float Open;
	
	public float High;
	
	public float Low;
	
	public float Close;
	
	public long Volume;
	
	public float AdjClose;
	
	@Override public String toString()
	{
		return String.format("[%s]: Open - %.2f, High - %.2f, Low - %.2f, Close - %.2f.", 
				Date, Open, High, Low, Close);
	}
}
