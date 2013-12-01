package cn.hzbook.android.test.chapter15.memorylog;

public class MemoryLog {
	private LogEntry[] _entries = null;
	private int _index = -1;
	private Object _lock = new Object();

	public void init() {
		_entries = new LogEntry[1024];
	}

	public void add(String msg, Object data) {
		int index = 0;
		synchronized (_lock) {
			index = ++_index % 1024;
		}

		LogEntry entry = new LogEntry();
		entry.Data = data;
		entry.Message = msg;
		Thread thread = Thread.currentThread();
		entry.ThreadId = thread.getId();

		_entries[index] = entry;
	}

	public void printLog() {
		int idx = _index % 1024;
		System.out.println("TID\tMsg\tData");
		for (int i = 0; i < idx; ++i) {
			LogEntry entry = _entries[i];
			System.out.println(String.format(
					"%1$d\t%2$s\t%3$s", entry.ThreadId,
					entry.Message,
					entry.Data.toString()));
		}
	}

	static class LogEntry {
		public long ThreadId;
		public String Message;
		public Object Data;
	}
}