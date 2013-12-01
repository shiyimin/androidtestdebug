package cc.iqa.iquery.mr;

import org.python.util.PythonInterpreter;
import com.google.common.base.Predicate;

public class Plugin implements Predicate<PythonInterpreter> {
	@Override
	public boolean apply(PythonInterpreter python) {
		return true;
	}
}