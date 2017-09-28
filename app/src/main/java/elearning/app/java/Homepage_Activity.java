package elearning.app.java;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import elearning.app.java.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Homepage_Activity extends Activity {

	private Button info, quiz;
	AdView addview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		info = (Button) findViewById(R.id.info);
		quiz=(Button) findViewById(R.id.quiz);

		info.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Homepage_Activity.this,
						E_Learning_AppActivity.class);
				startActivity(intent);

			}

		});
		quiz.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Homepage_Activity.this,
						Quiz_Activity.class);
				startActivity(intent);

			}

		});
//		addview=(AdView) findViewById(R.id.addview);
//		AdRequest request = new AdRequest();
//			addview.loadAd(request);

	}
	

}