package elearning.app.java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import elearning.app.java.R;
public class Splash extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread t=new Thread(){
			public void run(){
				try {
					sleep(1000);
					Intent i=new Intent(Splash.this,Homepage_Activity.class);
					startActivity(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

}
