package elearning.app.java;
import java.util.ArrayList;
import java.util.List;
import elearning.app.java.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<ques_details> {
	ArrayList<ques_details> items;

	LayoutInflater mInflater;

	Activity a;

	int layoutResourceId;
	
	boolean checked = false;

	public MyAdapter(Activity activity, int layoutResourceId,
			ArrayList<ques_details> items) {

		super(activity, layoutResourceId, items);

		this.layoutResourceId = layoutResourceId;

		this.items = items;

		this.a = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row;
		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			row = new View(a);
			mInflater = a.getLayoutInflater();
			row = mInflater.inflate(layoutResourceId, null, true);
			holder.ques = (TextView) row.findViewById(R.id.text);
			
			row.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
			row = convertView;
			holder = (ViewHolder) row.getTag();
			holder.ques.setText(items.get(position).getques());
		}
		holder.ques.setText(items.get(position).getques());
		
		
		row.setTag(holder);
		return row;
	}

	public class ViewHolder {
		TextView ques;
		
	}
}