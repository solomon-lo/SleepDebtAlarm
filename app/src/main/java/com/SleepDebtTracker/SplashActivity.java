package com.SleepDebtTracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    SessionManager sessionManager;
    Intent intent;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager=new SessionManager(SplashActivity.this);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with location_marker timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Log.e("myTag","sessionManager.checkLogin(): "+sessionManager.checkLogin());

                if (sessionManager.checkLogin()) {
                    intent = new Intent(SplashActivity.this, LastNightActivity.class);
                    // Add new Flag to start new Activity

                    Log.e("myTag","sessionManager.isLastNight(): "+sessionManager.isLastNight());
                    if (sessionManager.isLastNight()){

                        intent = new Intent(SplashActivity.this, AlarmActivity.class);

                    }
                }
                else
                {
                    intent = new Intent(SplashActivity.this, SleepHoursActivity.class);

                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
