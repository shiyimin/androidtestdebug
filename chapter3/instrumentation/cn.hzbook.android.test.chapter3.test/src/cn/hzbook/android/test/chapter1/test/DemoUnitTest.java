package cn.hzbook.android.test.chapter1.test;

import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

@SuppressWarnings("rawtypes")
public class DemoUnitTest extends ActivityInstrumentationTestCase2 {
    private static String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "cn.hzbook.android.test.chapter1.MainActivity";    
    private static String TARGET_PACKAGE_ID = "cn.hzbook.android.test.chapter1";
    private Solo _solo;

	@SuppressWarnings("unchecked")
    public DemoUnitTest() throws Exception {
		super(TARGET_PACKAGE_ID, Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME));
    }
	
    public void setUp() throws Exception
    {
        _solo = new Solo(this.getInstrumentation(), this.getActivity());
    }
    
    public void tearDown() throws Exception
    {
        _solo.finishOpenedActivities();
    }
    
	public void test添加书籍() throws Exception {
		_solo.clickOnText("添加");		
		_solo.sleep(500);
		_solo.clickOnText("编辑");
		
		//
		// robotiumli里，getEditText会过滤出所有EditText类型的控件
		// 而getEditText函数参数是过滤后EditText控件的索引号！
		//
		
		// 在标题文本框里输入Moonlight
		EditText text = _solo.getEditText(0);
		_solo.clearEditText(text);
		_solo.enterText(text, "Moonlight");
		
		// 在作者文本框里输入David
		text = _solo.getEditText(1);
		_solo.clearEditText(text);
		_solo.enterText(text, "David");

		// 在版权文本框里输入出版日期
		text = _solo.getEditText(2);
		_solo.clearEditText(text);
		_solo.enterText(text, "Feb 21, 2011");
		
		_solo.clickOnText("保存");		
		_solo.clickOnText("保存");		
	}
    
}
