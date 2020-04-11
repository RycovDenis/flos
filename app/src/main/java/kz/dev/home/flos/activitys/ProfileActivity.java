package kz.dev.home.flos.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kz.dev.home.flos.R;
import kz.dev.home.flos.datamodels.User;
import kz.dev.home.flos.services.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "Profile:";

    TextView textViewId, textViewUsername, textViewEmail, textViewGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


//        textViewId = (TextView) findViewById(R.id.textViewId);
//        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
//        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
//        textViewGender = (TextView) findViewById(R.id.textViewGender);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
//        textViewId.setText(String.valueOf(user.getId()));
//        textViewUsername.setText(user.getUsername());
//        textViewEmail.setText(user.getEmail());
//        textViewGender.setText(user.getGender());

        //when the user presses logout button
        //calling the logout method
//        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                SharedPrefManager.getInstance(getApplicationContext()).logout();
//            }
//        });
    }
}