package com.vuong.ebookapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;

public class Splash extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new CountDownTimer(5000,1000) {        	
        	

			@Override
			public void onFinish() {

				Intent intent = new Intent(getBaseContext(), MainActivity.class);

				startActivity(intent);

				finish();
				
			}
			@Override
			public void onTick(long millisUntilFinished) {
								
			}
		}.start();
        
    }
}