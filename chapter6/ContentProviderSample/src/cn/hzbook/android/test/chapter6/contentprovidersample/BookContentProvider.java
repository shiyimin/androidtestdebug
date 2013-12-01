package cn.hzbook.android.test.chapter6.contentprovidersample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class BookContentProvider extends ContentProvider {
	// 数据集的MIME类型应该以vnd.android.cursor.dir/开头
    public static final String BOOKS_TYPE = "vnd.android.cursor.dir/book";
    // 单一数据的MIME类型以vnd.android.cursor.item/开头
    public static final String BOOK_ITEM_TYPE = "vnd.android.cursor.item/book";
	// 本内容供应组件的官方名称
    public static final String AUTHORITY =
    		"cn.hzbook.android.test.chapter6.contentprovidersample";
	// 两个常量，用于匹配URI的格式
    public static final int BOOKS = 1;
    public static final int BOOK = 2;
    
	// 访问单个书籍记录的URI
    public static final Uri BOOK_URI = 
	    Uri.parse("content://" + AUTHORITY + "/book");
	// 访问书籍列表的URI
    public static final Uri BOOK_LIST_URI =
	    Uri.parse("content://" + AUTHORITY + "/books");
	// 书籍表里的列名
    public static final String Id = "_id";
    public static final String Title = "TITLE";
    public static final String Author = "AUTHOR";

	// 内容供应组件下面封装的数据库里保存书籍记录的表名：books
	private static final String TABLE_NAME = "books";
	// 在SQLite数据库里创建表的SQL语句
	// 当第一次启动内容供应组件并访问数据时，会使用它在数据库里建表
	private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME +
	    "(" +
			" _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			" TITLE TEXT, " +
			" AUTHOR TEXT" +
        ")";
	// 内容供应组件所使用的数据库
	private MainDatabaseHelper _db;
	
	// 辅助类型，用来封装内容供应组件创建和升级其内部使用到的SQLite数据库的相关操作
    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {		
    	public MainDatabaseHelper(Context context) {
			super(context, "BOOKSTORE", null, 1);
			Log.i("DEBUG", "MainDatabaseHelper.ctor");
		}

		// 当内容供应组件调用getReadableDatabase()或getWritableDatabase()
		// 函数时，如果数据库还没有创建，那么会调用该函数创建数据库
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i("DEBUG", "MainDatabaseHelper.onCreate");
			db.execSQL(CREATE_TABLE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// 只是一个简单的示例，不支持任何的升级场景 - 仅是简单重建数据表
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}    	
    }
    
    private static UriMatcher _uriMatcher;
    static {
    	_uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    	// 使用 content://cn.hzbook.android.test.chapter6.contentprovidersample/books 
    	// 访问所有书籍列表
    	_uriMatcher.addURI(AUTHORITY, "books", BOOKS);
    	// 使用 content://cn.hzbook.android.test.chapter6.contentprovidersample/book/1 
    	// 访问单本书的详细信息
    	_uriMatcher.addURI(AUTHORITY, "book/#", BOOK);
    	
    }
    
	// 删除数据操作，
	// uri指明要操作的数据表，selection过滤出符合条件的数据，
	// selectionArgs是替换selection条件里“?”的参数值列表
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// 打开数据库，如果数据库不存在，会创建数据库并建表
		SQLiteDatabase db = _db.getWritableDatabase();
		// delete会返回删除的行数，这个值需要传递给客户组件。
		int rowCount = db.delete(TABLE_NAME, selection, selectionArgs);
		// 通知其他监听内容组件内部数据更新操作的监听组件
		getContext().getContentResolver().notifyChange(uri, null);
		return rowCount;
	}

	// 根据数据的定位uri返回数据类型，以便客户组件正确处理数据
	@Override
	public String getType(Uri uri) {
		int code = _uriMatcher.match(uri);
		switch ( code ) {
		case BOOKS:
			return BOOKS_TYPE;
		case BOOK:
			return BOOK_ITEM_TYPE;
		default:
			return null;
		}
	}

	// 添加数据，向uri指定的数据集合里增加一行数据，行上每一列的数据
	// 都由键值对集合values指定。
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = _db.getWritableDatabase();
		// 添加新数据后，会返回新的一行的主键ID
		long newRowId = db.insert(TABLE_NAME, null, values);
		// 跟据新行的主键创建唯一定位行的uri。
		Uri newItemUri = Uri.withAppendedPath(
				BOOK_URI, Long.toString(newRowId));
		
		getContext().getContentResolver().notifyChange(BOOK_URI, null);
		return newItemUri;
	}

	@Override
	public boolean onCreate() {
		_db = new MainDatabaseHelper(getContext());
		return true;
	}

	// 查询操作
	// 其参数列表与ContentResolver.query完全一样
	// projection：过滤出每行要返回的列
	// selection：查询条件，如果条件里有“？”参数，由selectionArgs参数的列表提供参数值
	// sortOrder：排序条件
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// 因为SQL语句一般来说都比较复杂，直接使用字符串拼接的方式很容易出错
		// 所以建议用SQLiteQueryBuilder这个辅助类来拼接SQL查询语句的不同部分
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_NAME);
		
		Cursor c = builder.query(_db.getReadableDatabase(),
				projection, selection, selectionArgs, null, null, sortOrder);
		
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	// 更新操作
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = _db.getWritableDatabase();		
		int updateCount = db.update(TABLE_NAME, values, selection, selectionArgs);
		
		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}

}
