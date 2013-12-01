package temp;

@SuppressWarnings("serial")
public class CaseErrorException extends Exception
{
    public CaseErrorException(String message) {
    	this(message, null);
    }
 
    public CaseErrorException(Exception inner){
    	this(null, inner);
    }

    public CaseErrorException(String message, Exception inner){
    	super(message == null ? 
    			"测试代码错误，请修复测试代码，查看InnerException属性！" :
    		    String.format("测试代码错误，请修复测试代码，详细错误信息：" + 
				              "%1$s；或者查看InnerException属性！", message),
    		  inner);
    }
}