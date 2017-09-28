package elearning.app.java;



import java.util.ArrayList;
import java.util.List;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import elearning.app.java.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings.TextSize;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class E_Learning_AppActivity extends Activity implements
		OnItemClickListener, View.OnClickListener {
	private ListView list1;
	private Button next,prv;
	String str_ans;
	String str_ques;
	Database_helper_class myDbHelper;
	private ArrayAdapter adapter;
	Cursor ourCursor;
	ArrayList<ques_details> queslist = new ArrayList<ques_details>();
	int count = 1;
	int startIndex = 0;
	int endIndex = 10;
	AdView addview;

	// ArrayList<ques_details> pojo_ques_list = new ArrayList<ques_details>();
	// @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lists);
		list1 = (ListView) findViewById(R.id.list1);
		next = (Button) findViewById(R.id.btnnext);
		prv=(Button) findViewById(R.id.btnprv);
		next.setOnClickListener(this);
		prv.setOnClickListener(this);
		try {
			myDbHelper = new Database_helper_class(this);
			myDbHelper.createDataBase();
			myDbHelper.openDataBase();

			SQLiteDatabase mydatabase = myDbHelper.getWritableDatabase();

			Cursor ourCursor = mydatabase.query(myDbHelper.TABLE_E_LEARNING,
					null, null, null, null, null, null);

			ourCursor = myDbHelper.getCursor();
			ourCursor.moveToFirst();
			if (ourCursor.getCount() > 0) {
				do {
					String str_ques = (ourCursor.getString(ourCursor
							.getColumnIndex(Database_helper_class.QUES_COLUMN)));
					// Log.v("in do Question", str_ques);

					str_ans = (ourCursor.getString(ourCursor
							.getColumnIndex(Database_helper_class.ANS_COLUMN)));
					// Log.v("in do Question", str_ans);
					ques_details q = new ques_details();
					q.setques(str_ques);
					queslist.add(q);

				} while (ourCursor.moveToNext() == true);

				list1.setAdapter(adapter);
				list1.setOnItemClickListener(this);
			}
		} catch (Exception e) {
			Log.e("Error", "ERRO MSG" + e.toString());
			e.printStackTrace();
		}

		try {

			myDbHelper.close();

		} catch (SQLException sqle) {
			throw sqle;

		}
		subListArray(startIndex, endIndex);
		list1.setAdapter(adapter);
		
		addview=(AdView) findViewById(R.id.addview);
		AdRequest request = new AdRequest();
			addview.loadAd(request);
		
	}

	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		TextView text = (TextView) view.findViewById(R.id.text);
		String qus = text.getText().toString();
		Bundle b = new Bundle();
		b.putString("DB_QUS", qus); // why used db_qus want to know?
		b.putLong("DB_ID", id);
		Intent i = new Intent(E_Learning_AppActivity.this, AnsActivity.class);
		i.putExtras(b);
		startActivity(i);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void subListArray(int start, int end) {
		try {

		// Log.v("queslist size", queslist.size() + "");
		int m = queslist.size();
		// Log.v("m", m + "");
		
		ArrayList[] resgrp = new ArrayList[m / 5];
		// Log.v("length", resgrp.length + "");
		int n = resgrp.length;
		// Log.v("n", n + "");
		int o = m / n;
		// Log.v("o", o + "");
		for (int i = 0; i < n; i++) {
			resgrp[i] = new ArrayList<String>();
			Log.v("initialised ", i + "");
		}
		ArrayList<String> TempList = new ArrayList<String>();
		for (int i = start; i <= end - 1; i++) {
			int q = 0 ;
			String temp = queslist.get(i).Ques;
			resgrp[o].add(q, temp);
			// resgrp[i].add(queslist.get(i));
			Log.v("final ", queslist.get(i).Ques + "");
			TempList = resgrp[o];
			q++;
			adapter = new ArrayAdapter<String>(E_Learning_AppActivity.this,
					R.layout.list_item, R.id.text, TempList);
			//adapter.notifyDataSetChanged();
			list1.setAdapter(adapter);
			
		}
		}
		catch (Exception e) {

			Toast.makeText(getApplicationContext(), "This is the last list",
					Toast.LENGTH_SHORT).show();
		}
	}

	// ArrayList<String> tempArray = new ArrayList<String>();
	// tempArray = resgrp[0];
	// }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnnext:
			
			if (count == 5) {
				//do nothing...................ending page 
			} else {
				count = count + 1;
				startIndex = startIndex + 10;
				endIndex = startIndex + 10;
				subListArray(startIndex, endIndex);
				Toast.makeText(getApplicationContext(), String.valueOf(count)+"/5",Toast.LENGTH_SHORT).show();

			}
			break;
		case R.id.btnprv:
			if(count==0){
				//do nothing..........................frist page
			}else{
			count--;
			startIndex = startIndex - 10;
			endIndex = startIndex + 10;
			subListArray(startIndex, endIndex);
			Toast.makeText(getApplicationContext(), String.valueOf(count)+"/5",Toast.LENGTH_SHORT).show();

			}
			break;

		}
	}

}
