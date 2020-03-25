package kz.dev.home.flos.ContentActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import kz.dev.home.flos.AuthActivitys.LoginActivity;
import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.R;

public class SplashActivity extends MainActivity {
    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Start application ");
        init();
        setContentView(R.layout.activity_splash);
        // 5 seconds pause on splash page
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isLoggedIn()){
                    //Redirect to home page
                    intent = new Intent(context,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //Redirect to Login Page
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },5000);


    }

    public void init() {
        context = this;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
    }
}