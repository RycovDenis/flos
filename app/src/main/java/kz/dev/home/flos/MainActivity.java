package kz.dev.home.flos;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import kz.dev.home.flos.activitys.LoginActivity;
import kz.dev.home.flos.activitys.ProfileActivity;
import kz.dev.home.flos.services.SharedPrefManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main:";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                    finish();
                    startActivity(new Intent(this, ProfileActivity.class));
                    return;
                }
    }

}

