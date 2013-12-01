package cn.hzbook.android.test.chapter1;

import java.util.*;

import cn.hzbook.android.test.chapter1.R;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
// import android.widget.ExpandableListView.OnChildClickListener;
import android.view.View;

public class MainActivity extends ExpandableListActivity {
	private static final String NAME = "NAME";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static String[] _groupNames = new String[] { "Ben Elton",
			"Bill Bryson", "John Foxx", "P.G.Wodehouse", "Richard Dawkins",
			"Terry Pratchett", "Yorick Wilks" };

	private static String[][] _childItems = new String[][] {
			{ "High Society", "Stark" },
			{ "Down Under", "Notes from a Big Country" },
			{ "Astronaut - The Quiet Man" },
			{ "Psmith in the City", "The Inimitable Jeeves" },
			{ "The Selfish Gene" }, { "Moving Pictures", "Strata" },
			{ "Machine Translation" } };

	private static List<String> _groupNameList = new ArrayList<String>();
	private static List<List<String>> _childNameList = new ArrayList<List<String>>();

	static {
		for (int i = 0; i < _groupNames.length; ++i) {
			_groupNameList.add(_groupNames[i]);

			ArrayList<String> childList = new ArrayList<String>();
			_childNameList.add(childList);
			for (int j = 0; j < _childItems[i].length; ++j) {
				childList.add(_childItems[i][j]);
			}
		}
	}

	public static List<List<String>> getBooks() {
		return _childNameList;
	}

	public static List<String> getAuthors() {
		return _groupNameList;
	}

	private boolean _isDeleting = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
		List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

		for (int i = 0; i < _groupNameList.size(); ++i) {
			Map<String, String> curGroupMap = new HashMap<String, String>();
			groupData.add(curGroupMap);
			curGroupMap.put(NAME, _groupNameList.get(i));
			curGroupMap.put(DESCRIPTION, _groupNameList.get(i));

			List<Map<String, String>> children = new ArrayList<Map<String, String>>();
			for (int j = 0; j < _childNameList.get(i).size(); ++j) {
				Map<String, String> curChildMap = new HashMap<String, String>();
				children.add(curChildMap);
				curChildMap.put(NAME, _childNameList.get(i).get(j));
				curChildMap.put(DESCRIPTION, _childNameList.get(i).get(j));
			}

			childData.add(children);
		}

		setListAdapter(new TheSimpleExpandableListAdapter(this,
				groupData, android.R.layout.simple_expandable_list_item_1,
				new String[] { NAME, DESCRIPTION }, new int[] {
						android.R.id.text1, android.R.id.text2 }, childData,
				android.R.layout.simple_expandable_list_item_2, new String[] {
						NAME, DESCRIPTION }, new int[] { android.R.id.text1,
						android.R.id.text2 }));

		Button button = (Button) findViewById(R.id.btnDelete);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onDelete(v);
			}
		});

		button = (Button) findViewById(R.id.btnAdd);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onAdd(v);
			}
		});

		expandAllGroups();		
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		if (_isDeleting) {
			_childNameList.get(groupPosition).remove(childPosition);
			_isDeleting = false;
			startActivity(new Intent(this, MainActivity.class));
		} else {
			Intent i = new Intent(this, BookDetails.class);
			i.setAction(Intent.ACTION_VIEW);
			i.putExtra("author", _groupNameList.get(groupPosition));
			i.putExtra("title",
					_childNameList.get(groupPosition).get(childPosition));
			i.putExtra("indics", new int[] { groupPosition, childPosition });
			i.putExtra("copyright", "copyright c");

			startActivity(i);
		}

		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}

	protected void expandAllGroups() {
		ExpandableListView lv = getExpandableListView();

		for (int i = 0; i < _groupNames.length; ++i) {
			lv.expandGroup(i);
		}
	}

	protected void onDelete(View v) {
		_isDeleting = true;
	}

	protected void onAdd(View v) {
		Intent i = new Intent(this, BookDetails.class);
		i.setAction(Intent.ACTION_INSERT);

		startActivity(i);
	}
}