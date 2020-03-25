package kz.dev.home.flos.ContentActivitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import kz.dev.home.flos.AuthActivitys.LoginActivity;
import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.R;

public class HomeActivity extends MainActivity {
    TextView nameTv;
    TextView emailTv;
    Button logoutbtn;
    private Toolbar toolbar;
    private Menu menu;

    public HomeActivity() {
    }

    public boolean onCreateOptionsMenu(MenuItem item) {

        getMenuInflater().inflate(R.menu.toolbar_menu_items, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
        context = this;
        init();
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getViews();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void getViews() {
        nameTv = findViewById(R.id.nameTv);
        nameTv.setText(sharedPreferences.getString("username",""));
        emailTv = findViewById(R.id.emailTv);
        emailTv.setText(sharedPreferences.getString("email",""));
        logoutbtn = findViewById(R.id.logoutBtn);

        //make logout
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect back to login page
                logout();
                intent = new Intent(context, LoginActivity.class);
                //remove all previous stack activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

}
