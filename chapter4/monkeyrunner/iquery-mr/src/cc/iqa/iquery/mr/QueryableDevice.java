package cc.iqa.iquery.mr;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.runtime.RecognitionException;
import org.python.core.ArgParser;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;

import cc.iqa.iquery.ITreeNode;

import com.android.chimpchat.core.TouchPressType;
import com.android.monkeyrunner.JythonUtils;
import com.android.monkeyrunner.MonkeyDevice;
import com.android.monkeyrunner.doc.MonkeyRunnerExported;
import com.google.common.base.Preconditions;

@SuppressWarnings("serial")
@MonkeyRunnerExported(doc = "QueryableDevice是一个支持使用iQuery语句查找和点击控件的Device")
public class QueryableDevice extends PyObject implements ClassDictInit {
	@SuppressWarnings("unused")
	private static final Set<String> EXPORTED_METHODS = JythonUtils
			.getMethodNames(QueryableDevice.class);
	private static final Logger LOG = Logger.getLogger(QueryableDevice.class
			.getCanonicalName());

	private MonkeyDevice _device;
	// private Socket _socket;
	private String _viewServerHost;
	private int _viewServerPort;

	public static void classDictInit(PyObject dict) {
		JythonUtils.convertDocAnnotationsForClass(QueryableDevice.class, dict);
	}

	@MonkeyRunnerExported(doc = "根据一个 MonkeyDevice实例创建QueryableDevice.", args = { "device" }, argDocs = { "要扩展的MonkeyDevice实例." })
	public QueryableDevice(MonkeyDevice device) {
		// 在获取HierarchyViewer引用的时候，会启动手机上的ViewServer。
		// 因为HierarchyViewer这个类型提供的函数实在是太少，基本上就抛弃这个类了
		// 我们自己去通过过socket跟view server通信，因此这里简单丢弃hierarchyviewer的实例。
		device.getImpl().getHierarchyViewer();

		_device = device;
	}

	@MonkeyRunnerExported(doc = "戳一个 控件", args = { "selector", "ch", "type" }, argDocs = {
			"用来查找要戳的控件的iQuery查询语句", "戳的手法" })
	public void touch(PyObject[] args, String[] kws)
			throws UnsupportedEncodingException, IOException,
			RecognitionException {
		ArgParser ap = createArgParser(args, kws, QueryableDevice.class,
				"touch");
		Preconditions.checkNotNull(ap);

		By selector = getSelector(ap, 0);
		ControlHierarchy ch = getControlHierarchy(ap, 1);
		String tmpType = ap.getString(2);
		TouchPressType type = TouchPressType.fromIdentifier(tmpType);
		Preconditions.checkNotNull(type, "Invalid touch type: " + tmpType);
		// TODO: try catch rethrow PyExc
		touch(selector, ch, type);
	}

	public void touch(By selector, ControlHierarchy ch, TouchPressType type)
			throws UnsupportedEncodingException, IOException,
			RecognitionException {
		Point p = getElementCenter(selector, ch);
		_device.getImpl().touch(p.x, p.y, type);
	}

	@MonkeyRunnerExported(doc = "连接到ViewServer", args = { "host", "port" }, argDocs = {
			"要链接的View server的地址", "要连接 的View server的端口号！" })
	public void connectViewServer(PyObject[] args, String[] kws)
			throws IOException {
		ArgParser ap = createArgParser(args, kws, QueryableDevice.class,
				"connectViewServer");
		int port = 4939;
		String host = "127.0.0.1";

		if (ap != null) {
			host = ap.getString(0);
			port = ap.getInt(1);
		}

		_viewServerHost = host;
		_viewServerPort = port;
	}

	@MonkeyRunnerExported(doc = "获取Activity的Id", args = { "activity" }, argDocs = { "Activity完整的名称！" })
	public String getActivityId(PyObject[] args, String[] kws)
			throws UnsupportedEncodingException, IOException {
		ArgParser ap = createArgParser(args, kws, QueryableDevice.class,
				"getActivityId");

		Preconditions.checkNotNull(ap);
		String activityName = ap.getString(0);
		String[] activities = execute("LIST");
		for (int i = 0; i < activities.length; ++i) {
			String[] tmp = activities[i].split(" ");
			if (tmp[1].startsWith(activityName) ) {
				return tmp[0];
			}
		}

		return "";
	}

	@MonkeyRunnerExported(doc = "获取指定Activity的界面布局。", args = { "aid" }, argDocs = { "要查看控件布局的Activity ID，可以通过getActivityId获取！" })
	public ControlHierarchy getLayout(PyObject[] args, String[] kws) throws UnsupportedEncodingException, IOException {
		ArgParser ap = createArgParser(args, kws, QueryableDevice.class,
				"getLayout");		

		Preconditions.checkNotNull(ap);
		String aid = ap.getString(0);
		String[] texts = execute("DUMP " + aid);
		ControlHierarchy ch = new ControlHierarchy(texts);
		return ch;
	}

	/**
	 * Get the selector object from the argument parser.
	 * 
	 * @param ap
	 *            argument parser to get it from.
	 * @param i
	 *            argument index.
	 * @return selector object.
	 */
	private By getSelector(ArgParser ap, int i) {
		return (By) ap.getPyObject(i).__tojava__(By.class);
	}

	private ControlHierarchy getControlHierarchy(ArgParser ap, int i) {
		return (ControlHierarchy) ap.getPyObject(i).__tojava__(ControlHierarchy.class);		
	}

	/**
	 * Get the coordinates of the element's center.
	 * 
	 * @param selector
	 *            the element selector
	 * @return the (x,y) coordinates of the center
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws RecognitionException
	 */
	private Point getElementCenter(By selector, ControlHierarchy ch)
			throws UnsupportedEncodingException, IOException,
			RecognitionException {
		List<ITreeNode> result = selector.query(ch.getAllNodes());
		if (result == null) {
			throw new PyException(Py.ValueError, String.format(
					"找不到iQuery语句指定的控件: %s", selector.getSelector()));
		}

		if (result.size() != 1) {
			throw new PyException(Py.ValueError, String.format(
					"iQuery查询语句找到多于一个控件: %s", selector.getSelector()));
		}

		ITreeNode node = result.get(0);
		Point p = getAbsoluteCenterOfView(node);
		return p;
	}

	public static Point getAbsolutePositionOfView(ITreeNode node) {
		int x = getIntProperty(node, "mLeft");
		int y = getIntProperty(node, "mTop");
		ITreeNode p = node.getParent();
		while (p != null) {
			x += getIntProperty(p, "mLeft") - getIntProperty(p, "mScrollX");
			y += getIntProperty(p, "mTop") - getIntProperty(p, "mScrollY");
			p = p.getParent();
		}
		return new Point(x, y);
	}

	/**
	 * Gets the absolute x/y center of the specified view node.
	 * 
	 * @param node
	 *            view node to find position of.
	 * @return absolute x/y center of the specified view node.
	 */
	public static Point getAbsoluteCenterOfView(ITreeNode node) {
		Point point = getAbsolutePositionOfView(node);
		return new Point(point.x + (getIntProperty(node, "getWidth()") / 2), point.y
				+ (getIntProperty(node, "getHeight()") / 2));
	}

	public static ArgParser createArgParser(PyObject[] args, String[] kws,
			Class<?> clz, String methodName) {
		Method m;

		try {
			m = clz.getMethod(methodName, PyObject[].class, String[].class);
		} catch (SecurityException e) {
			LOG.log(Level.SEVERE, "Got exception: ", e);
			return null;
		} catch (NoSuchMethodException e) {
			LOG.log(Level.SEVERE, "Got exception: ", e);
			return null;
		}

		MonkeyRunnerExported annotation = m
				.getAnnotation(MonkeyRunnerExported.class);
		return new ArgParser(methodName, args, kws, annotation.args());
	}

	private static int getIntProperty(ITreeNode node, String property) {
		return Integer.parseInt(node.getProperty(property).getValue());
	}

	private String[] execute(String command)
			throws UnsupportedEncodingException, IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(_viewServerHost, _viewServerPort));
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "utf-8"));
			out.write(command);
			out.newLine();
			out.flush();
			List<String> lines = new ArrayList<String>();
			String line = null;

			while ((line = in.readLine()) != null) {

				if (line.compareTo("DONE.") != 0) {
					lines.add(line);
				} else {
					break;
				}
			}

			String[] strs = new String[lines.size()];
			lines.toArray(strs);
			return strs;
		} finally {
			socket.close();
		}
	}
}