package cn.hzbook.android.test.chapter1;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class TheSimpleExpandableListAdapter extends SimpleExpandableListAdapter {
	public TheSimpleExpandableListAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int groupLayout,
			String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo) {
		super(context, groupData, groupLayout, groupFrom, groupTo, childData,
				childLayout, childFrom, childTo);
	}

	@Override 
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) { 
	    View v = super.getGroupView(groupPosition, isExpanded, convertView, parent); 
	    ExpandableListView eLV = (ExpandableListView) parent; 
	    eLV.expandGroup(groupPosition); 
	    return v; 
	} 

}
