package elearning.app.java;

import java.io.IOException;
import elearning.app.java.R;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz_Activity extends Activity {
	/** Called when the activity is first created. */
	private RadioButton radioButton;
	private TextView quizQuestion;
	private int rowIndex = 1;
	private int score = 0;
	private int questNo = 0;
	private boolean checked = false;
	private boolean flag = true;
	int quizid = 0;
	int l = 1;
	private RadioGroup radioGroup;
	Button btnNext, btnSave,score1;
	AdView addview;

	String[] corrAns = new String[10];

	final Database_helper_class db = new Database_helper_class(this);
	Cursor c1;
	Cursor c2;
	Cursor c3;
	int counter = 1;
	String label;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		String options[] = new String[39];
		score1=(Button)findViewById(R.id.score1);
		// get reference to radio group in layout
		// RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rd1);
		// layout params to use when adding each radio button
		LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
				RadioGroup.LayoutParams.WRAP_CONTENT,
				RadioGroup.LayoutParams.WRAP_CONTENT);

	try {
			db.createDataBase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c3 = db.getCorrAns(); 
		
		for (int i = 0; i <= 9; i++) {
			corrAns[i] = c3.getString(0);
			c3.moveToNext();

		}
		radioGroup = (RadioGroup) findViewById(R.id.rd1);
	
		// radioGroup.setOnCheckedChangeListener(new
		// RadioGroup.OnCheckedChangeListener()
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// // TODO Auto-generated method stub
						String text = null;
						String qus = null;
						for (int i = 0; i < 4; i++) {
							RadioButton btn = (RadioButton) radioGroup
									.getChildAt(i);
							
								if (btn.isPressed()&& btn.isChecked() 
										&& questNo < 10) {
									//Log.v("corrAns[questNo]", corrAns[questNo]);

									text = btn.getText().toString();
									qus = corrAns[questNo];
									Log.v("EQUAL STRING", "" + qus + ":" + text);
									if (text.equalsIgnoreCase(qus) && flag == true) {
										score++;
										flag = false;
										checked = true;
										
										Toast.makeText(Quiz_Activity.this, "Correct", Toast.LENGTH_SHORT).show();
										break;
									} else if  (text!=qus ) {
										//score--;
										//flag = true;
										//checked = false;
										Toast.makeText(Quiz_Activity.this, "Incorrect", Toast.LENGTH_SHORT).show();
									}
								
								}
						}
		}
	});
		
		quizQuestion = (TextView) findViewById(R.id.qus1);
		displayQuestion();
		/* Displays the next options and sets listener on next button */
		btnNext = (Button) findViewById(R.id.next1);

		btnNext.setEnabled(false);
		btnNext.setOnClickListener(btnNext_Listener);
		/* Saves the selected values in the database on the save button */
		btnSave = (Button) findViewById(R.id.save);
		btnSave.setOnClickListener(btnSave_Listener);

		score1.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				Toast.makeText(getApplicationContext(),
						String.valueOf(score) + "/10" ,
						Toast.LENGTH_SHORT).show();
			}
		});
		btnSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_btn));
		btnNext.setBackgroundColor(Color.GRAY);
		addview=(AdView) findViewById(R.id.addview);
		AdRequest request = new AdRequest();
			addview.loadAd(request);
	}
	
	/* Called when next button is clicked */
	private View.OnClickListener btnNext_Listener = new View.OnClickListener() {
		public void onClick(View v) {
			btnNext.setEnabled(false);
			btnSave.setEnabled(true);
			btnSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_btn));
			btnNext.setBackgroundColor(Color.GRAY);
			flag = true;
			checked = false;
			questNo++;

			if (questNo < 10) {
				c1.moveToNext();
				displayQuestion();
			}
			}
	};
	/* Called when save button is clicked */
	private View.OnClickListener btnSave_Listener = new View.OnClickListener() {

		public void onClick(View v) {
			btnNext.setEnabled(true);
			btnSave.setEnabled(false);
			btnNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_btn));
			btnSave.setBackgroundColor(Color.GRAY);
		}
	};
	private void displayQuestion() {
		c1 = db.getcorrect_Content(rowIndex++);
		quizQuestion.setText(c1.getString(0));
		radioGroup.removeAllViews();
		for (int i = 0; i <= 3; i++) {
			c2 = db.getAns(l++);
			radioButton = new RadioButton(this);
			radioButton.setText(c2.getString(0));
			radioButton.setId(i);
			c2.moveToPosition(l);
			radioGroup.addView(radioButton);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
