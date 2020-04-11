package kz.dev.home.flos;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.dev.home.flos.fragments.MessagesFragment;
import kz.dev.home.flos.fragments.NewTiFragment;
import kz.dev.home.flos.fragments.ProfileFragment;
import kz.dev.home.flos.fragments.SettingsFragment;
import kz.dev.home.flos.fragments.TasksFragment;
import kz.dev.home.flos.fragments.TicketsFragment;
import kz.dev.home.flos.fragments.ToolsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "Main:";
    private boolean viewIsAtHome;
    public String token;
    public String parsedValueUid,parsedValueFname,parsedValueLname,parsedValueEmail,parsedValueRoleID,parsedValueRoleName;
    public static final String Secret_KEY = "144541354333adswcxs2axas24xcas1x456as47d532c4w";
    private TextView nvName, nvRole;
    CircleImageView circleImageView;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jwtUserParse();
        changeTextNH();
        drawerMeVoid();
        imgMeVoid();
        fabMeVoid();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        displayView(id);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displayView(id);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtHome) { //if the current view is not the News fragment
            displayView(R.id.nav_tickets); //display the News fragment
        } else {
            moveTaskToBack(true);  //If view is in News fragment, exit application
        }
    }
    public void displayView(int viewId) {
        Bundle bundle = new Bundle();
        bundle.putString("UID", parsedValueUid);
        bundle.putString("fname", parsedValueFname);
        bundle.putString("lname", parsedValueLname);
        bundle.putString("email", parsedValueEmail);
        bundle.putString("role_id", parsedValueRoleID);
        bundle.putString("role_name", parsedValueRoleName);
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_tickets:
                fragment = new TicketsFragment();
                fragment.setArguments(bundle);
                title  = getString(R.string.nav_menu_tickets);
                viewIsAtHome = true;
                break;
            case R.id.nav_messages:
                fragment = new MessagesFragment();
                fragment.setArguments(bundle);
                title = getString(R.string.nav_menu_message);
                viewIsAtHome = false;
                break;
            case R.id.nav_tasks:
                fragment = new TasksFragment();
                fragment.setArguments(bundle);
                title = getString(R.string.nav_menu_tasks);
                viewIsAtHome = false;
                break;
            case R.id.nav_tools:
                fragment = new ToolsFragment();
                fragment.setArguments(bundle);
                title = getString(R.string.nav_menu_tools);
                viewIsAtHome = false;
                break;
            case R.id.action_settings:
                fragment = new SettingsFragment();
                fragment.setArguments(bundle);
                title = getString(R.string.settings);
                viewIsAtHome = false;
                break;
            case R.id.action_profile:
                fragment = new ProfileFragment();
                fragment.setArguments(bundle);
                title = getString(R.string.profile);
                viewIsAtHome = false;
                break;
            case R.id.fab:
                fragment = new NewTiFragment();
                fragment.setArguments(bundle);
                title = getString(R.string.string_new_ti);
                viewIsAtHome = false;
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
    @SuppressLint("SetTextI18n")
    private void changeTextNH(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = (TextView) headerView.findViewById(R.id.navUsername);
        nvName = (TextView) headerView.findViewById(R.id.nh_name);
        nvRole = (TextView) headerView.findViewById(R.id.nh_role);
        nvName.setText(parsedValueFname + " "+parsedValueLname);
        nvRole.setText(parsedValueRoleName);
//        navUsername.setText("Your Text Here");
    }
    private void jwtUserParse(){
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        assert token != null;
        JWT jwt = new JWT(token);
        Claim uid = jwt.getClaim("uid");
        Claim firstname = jwt.getClaim("firstname");
        Claim lastname = jwt.getClaim("lastname");
        Claim email = jwt.getClaim("email");
        Claim role_id = jwt.getClaim("role_id");
        Claim role_name = jwt.getClaim("role_name");
        parsedValueUid = uid.asString();
        parsedValueFname = firstname.asString();
        parsedValueLname = lastname.asString();
        parsedValueEmail = email.asString();
        parsedValueRoleID = role_id.asString();
        parsedValueRoleName = role_name.asString();
    }
    private void drawerMeVoid(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //Log.i(TAG, "onDrawerSlide");
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
//                Log.i(TAG, "onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
//                Log.i(TAG, "onDrawerClosed");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
//                Log.i(TAG, "onDrawerStateChanged");
            }
        });
    }
    private void fabMeVoid(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(R.id.fab);
            }
        });
    }
    private void imgMeVoid(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        circleImageView = (CircleImageView) headerView.findViewById(R.id.action_profile);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(R.id.action_profile);
            }
        });
    }
}

