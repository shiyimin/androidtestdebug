package cn.hzbook.android.test.chapter6;

public interface IStore {
	void init() throws DataInitException;
	
	Book[] loadAll();
}
