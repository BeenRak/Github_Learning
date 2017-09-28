package elearning.app.java;

import java.io.IOException;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import elearning.app.java.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AnsActivity extends Activity implements OnClickListener {
	TextView tvQus, tvAns;
	String qus;
	Button nxt, prv;
	protected Intent getIntent;
	int l;
	Database_helper_class myDbHelper = null;
	AdView addview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ans);
		tvQus = (TextView) findViewById(R.id.txtQus);
		tvAns = (TextView) findViewById(R.id.txtAns);
		nxt = (Button) findViewById(R.id.nxt);
		prv = (Button) findViewById(R.id.prv);
		myDbHelper = new Database_helper_class(this);
		Bundle b = getIntent().getExtras();
		qus = b.getString("DB_QUS").toString();

		l = (int) b.getLong("DB_ID");
		final int i = (l + 1);
		tvQus.setText("Question :" + " " + qus);
		//tvQus.setTextColor(Color.rgb(0, 0, 0));
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		try {
			String str_ans = "";
			myDbHelper.openDataBase();
			SQLiteDatabase sqlitedatabase = myDbHelper.getWritableDatabase();
			String[] columns = new String[] { Database_helper_class.ID_COLUMNS,
					Database_helper_class.QUES_COLUMN,
					Database_helper_class.ANS_COLUMN };

			Cursor cursor = sqlitedatabase.query(myDbHelper.TABLE_E_LEARNING,
					columns, Database_helper_class.QUES_COLUMN + "=\"" + qus+ "\"", null,
					null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();

			}
			str_ans = cursor.getString(2);
			tvAns.setText("Answer :" + " " + str_ans);
			tvAns.setTextColor(Color.rgb(0,0,0));
			} catch (SQLiteException sqle) {
			throw sqle;

		} 
		try {
			myDbHelper.close();
		} catch (SQLException sqle) {
			throw sqle;
		}
		nxt.setOnClickListener(this);
		prv.setOnClickListener(this);
		
		addview=(AdView) findViewById(R.id.addview);
		AdRequest request = new AdRequest();
			addview.loadAd(request);
		

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		myDbHelper.openDataBase();
		SQLiteDatabase sqlitedatabase = myDbHelper.getWritableDatabase();
		Cursor cursor = sqlitedatabase.rawQuery(
				"SELECT _id,questions,answers FROM ques_ans", null);
		String str_qus = "";
		String str_ans = "";
		switch (v.getId()) {

		case R.id.nxt:

			if (l < (cursor.getCount() - 1)) {
				l++;
			} else {
				Toast.makeText(getApplicationContext(), "No more question",
						Toast.LENGTH_SHORT).show();
			}
			if (cursor.getCount() > 0) {

				cursor.moveToPosition(l);
				str_qus = cursor.getString(cursor
						.getColumnIndex(Database_helper_class.QUES_COLUMN));
				str_ans = cursor.getString(cursor
						.getColumnIndex(Database_helper_class.ANS_COLUMN));

				tvQus.setText("Question :" + " " +str_qus);
				tvAns.setText("Answer :" + " " +str_ans);

			}
			break;
		case R.id.prv:
			try {
				l--;
				if (cursor.getCount() > 0) {
					cursor.moveToPosition(l);
					str_qus = cursor.getString(cursor
							.getColumnIndex(Database_helper_class.QUES_COLUMN));
					str_ans = cursor.getString(cursor
							.getColumnIndex(Database_helper_class.ANS_COLUMN));

					tvQus.setText("Question :" + " " +str_qus);
					tvAns.setText("Answer :" + " " +str_ans);
				}
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "No more question",
						Toast.LENGTH_SHORT).show();
				l = 0;
			
			}
			break;
		}
		myDbHelper.close();
	}
}
