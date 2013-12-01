package cn.hzbook.android.test.chapter6;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public class App {
	// private static BookStore _store;
	private static IStore _store;
	
	public static void main(String[] args) throws Exception {
		if ( args.length != 1 ) {
			System.out.println("使用方法：java App [书名|作者]");
			return;
		}
		
		传统新建实例的方式(args[0]);
		使用容器的方式(args[0]);
		使用服务定位器的方式(args[0]);
	}
	
	private static void 传统新建实例的方式(String author) throws DataInitException {
		_store = new BookStore();
		_store.init();
		authorBy(author);
	}
	
	private static void 使用容器的方式(String author) throws DataInitException {
		MutablePicoContainer 容器 = 初始化容器();
		
		_store = 容器.getComponent(IStore.class);
		_store.init();
		authorBy(author);
	}
	
	private static MutablePicoContainer 初始化容器() {
		MutablePicoContainer pico = new DefaultPicoContainer();
		pico.addComponent(IocBookStore.class);
		pico.addComponent(BookReader.class);
		
		return pico;
	}

	private static void 使用服务定位器的方式(String author) throws DataInitException {
		初始化服务定位器();
		_store = (IStore)ServiceLocator.get("service://localhost/BookStore");
		_store.init();
		authorBy(author);
	}

	private static void 初始化服务定位器() {
		ServiceLocator.init();
	}

	private static void authorBy(String author) {
		Book[] books = _store.loadAll();
		for (int i = 0; i < books.length; ++i ) {
			if ( books[i].author.equals(author) ) {
				print(books[i]);
			}
		}
	}
	
	private static void print(Book book) {
		System.out.println(String.format("书名：%1$s\n作者：%2$s\n", book.title, book.author));
	}
}
