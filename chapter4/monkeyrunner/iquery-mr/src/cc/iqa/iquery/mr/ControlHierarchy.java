package cc.iqa.iquery.mr;

import java.util.Set;
import java.util.List;

import org.python.core.ClassDictInit;
import org.python.core.PyObject;

import com.android.monkeyrunner.JythonUtils;
import com.android.monkeyrunner.doc.MonkeyRunnerExported;

import cc.iqa.iquery.ITreeNode;
import cc.iqa.iquery.monkey.LayoutTree;
import cc.iqa.iquery.monkey.LayoutTreeParser;

@SuppressWarnings("serial")
@MonkeyRunnerExported(doc = "QueryableDevice是一个支持使用iQuery语句查找和点击控件的Device")
public class ControlHierarchy extends PyObject implements ClassDictInit {
	@SuppressWarnings("unused")
	private static final Set<String> EXPORTED_METHODS = JythonUtils
			.getMethodNames(ControlHierarchy.class);

	private String[] _viewServerOutput;
	private LayoutTree _tree;
	private List<ITreeNode> _nodes;

	public static void classDictInit(PyObject dict) {
		JythonUtils.convertDocAnnotationsForClass(ControlHierarchy.class, dict);
	}

	@MonkeyRunnerExported(doc = "获取原始的ViewServer的Hierarychy输出。")
	public String[] getViewServerOutput() { return _viewServerOutput; }

	public LayoutTree getLayoutTree() { return _tree; }

	public List<ITreeNode> getAllNodes() { return _nodes; }

	public ControlHierarchy(String[] vsoutput) {
		_viewServerOutput = vsoutput;
		_tree = LayoutTreeParser.parse(_viewServerOutput);
		_nodes = _tree.getAllNodesCopy();
	}
}