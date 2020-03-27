package kz.dev.home.flos.ContentActivitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import kz.dev.home.flos.AuthActivitys.LoginActivity;
import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.NewTicketActivity;
import kz.dev.home.flos.R;

@SuppressLint("Registered")
public class HomeActivity extends MainActivity {
    TextView nameTv;
    TextView emailTv;
    Button logoutbtn,profilebtn,newTiBtn;
    DrawerLayout mDrawer;
    Toolbar toolbar;
    NavigationView nvDrawer;

    ActionBarDrawerToggle drawerToggle;

    @SuppressLint({"NewApi", "CutPasteId"})
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        init();
        setContentView(R.layout.activity_home);
        getViews();
    }

    private void getViews() {

        logoutbtn = findViewById(R.id.logoutBtn);
        profilebtn = findViewById(R.id.profileBtn);
        newTiBtn = findViewById(R.id.newTiBtn);


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
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect back to login page
                intent = new Intent(context, ProfileActivity.class);
                //remove all previous stack activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        newTiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect back to login page
                intent = new Intent(context, NewTicketActivity.class);
                //remove all previous stack activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

}
