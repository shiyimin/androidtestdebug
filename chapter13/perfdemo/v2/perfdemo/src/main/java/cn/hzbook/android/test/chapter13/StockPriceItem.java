package cn.hzbook.android.test.chapter13;

public class StockPriceItem {
        private String _volume;
	private String _date;
	private String _open;
	private String _high;
	private String _low;
	private String _close;
	@SuppressWarnings("unused")
	private String _adjClose;
	
	private float _fHigh = -1.0f;
	
	public void setVolume(String volume) { _volume = volume; }
	public void setDate(String date) { _date = date; }
	public void setOpen(String open) { _open = open; }
	public void setHigh(String high) { _high = high; }
	public void setLow(String low) { _low = low; }
	public void setClose(String close) { _close = close; }
	public void setAdjClose(String adjclose) { _adjClose = adjclose; }
		
	public float getHigh() {
		if ( _fHigh < 0.0f ) {
			_fHigh = Float.parseFloat(_high);			
		}
		return _fHigh;
	}
	
	@Override public String toString()
	{
		return String.format("[%s]: Open - %s, High - %s, Low - %s, Close - %s.", 
				_date, _open, _high, _low, _close);
	}
}
