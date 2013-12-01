import java.util.*;

public class MemoryLog {
    private LogEntry[] _entries = null;
    private int _index = -1;
    private Object _lock = new Object();
    
    public void Init() {
	_entries = new LogEntry[1024];
    }
    
    public void Add(String msg, Object data) {
	int index = 0;
	synchronized ( _lock ) {
	    index = ++_index % 1024;
	}
	
	LogEntry entry = new LogEntry();
	entry.Data = data;
	entry.Message = msg;
	entry.Time = new Date();
	Thread thread = Thread.currentThread();
	entry.ThreadId = thread.getId();
	entry.ThreadName = thread.getName();
	
	_entries[index] = entry;
    }

    public void PrintLog() {
	int idx = _index % 1024;
	for ( int i = 0; i < idx; ++i ) {
	    LogEntry entry = _entries[i];
	    System.out.println(String.format("Thread [%1$d - %2$s@%3$s]: %4$s %5$s",  entry.ThreadId,
					     entry.ThreadName,
					     entry.Time.toString(),
					     entry.Message,
					     entry.Data.toString()));
	}
    }

    static class LogEntry {
	public long ThreadId;
	public String ThreadName;
	public String Message;
	public Object Data;
	public Date Time;
    }
}