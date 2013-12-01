package cc.iqa.iquery.mr;

import java.io.IOException;
import java.util.List;

import cc.iqa.iquery.ITreeNode;
import cc.iqa.iquery.iQuery;
import cc.iqa.iquery.iQueryParser;

import com.google.common.base.Preconditions;
import com.android.monkeyrunner.JythonUtils;
import com.android.monkeyrunner.doc.MonkeyRunnerExported;

import org.antlr.runtime.RecognitionException;
import org.python.core.ArgParser;
import org.python.core.ClassDictInit;
import org.python.core.PyObject;

@SuppressWarnings("serial")
public class By extends PyObject implements ClassDictInit {
	public static void classDictInit(PyObject dict) {
		JythonUtils.convertDocAnnotationsForClass(By.class, dict);
	}

	private String _selector;
	private iQueryParser _parser;

	By(String selector) throws IOException, RecognitionException {
		_selector = selector;
		_parser = iQuery.createParser(_selector);
	}

	@MonkeyRunnerExported(doc = "使用iQuery查询控件", args = { "selector" }, argDocs = { "iQuery查询语句" })
	public static By iquery(PyObject[] args, String[] kws) throws Exception, RecognitionException {
		ArgParser ap = QueryableDevice.createArgParser(args, kws, By.class, "iquery");
		Preconditions.checkNotNull(ap);

		String selector = ap.getString(0);
		return new By(selector);
	}

	public static By iquery(String iquery) throws IOException, RecognitionException {
		return new By(iquery);
	}

	public String getSelector() { return _selector; }

	public List<ITreeNode> query(List<ITreeNode> candidates) throws RecognitionException {
		return _parser.query(candidates);
	}
}