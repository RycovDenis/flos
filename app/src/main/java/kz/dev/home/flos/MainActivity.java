package kz.dev.home.flos;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.MultiDex;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.dev.home.flos.datamodels.User;
import kz.dev.home.flos.fragments.MessagesFragment;
import kz.dev.home.flos.fragments.NewTaskFragment;
import kz.dev.home.flos.fragments.NewTiFragment;
import kz.dev.home.flos.fragments.ProfileFragment;
import kz.dev.home.flos.fragments.SettingsFragment;
import kz.dev.home.flos.fragments.TasksFragment;
import kz.dev.home.flos.fragments.TicketsFragment;
import kz.dev.home.flos.fragments.ToolsFragment;
import kz.dev.home.flos.helper.SharedPrefManager;
import kz.dev.home.flos.services.URLs;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "Main:";
    private static final String STARTUP_TRACE_NAME = " Logging TraceMetric" ;
    private static final String REQUESTS_COUNTER_NAME = "_as";
    private boolean viewIsAtHome;
    private CircleImageView circleImageView;
    private FloatingActionButton fab_main, fabNew_ticket, fabNew_task;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView textview_newti, textview_newtask;
    Boolean isOpen = false;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        changeTextNH();
        drawerMeVoid();
        imgMeVoid();
        fabMeVoid();
        notfMy();
    }

    private void notfMy() {
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
//        }else {
            // [START retrieve_current_token]
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(Objects.requireNonNull(task.getResult()).getToken());
                        String token = SharedPrefManager.getInstance(this).getDeviceToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                    });
            // [END retrieve_current_token]
//        }
        MultiDex.install(this);
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
        if (item.getItemId() == android.R.id.home) {
            finish();
        }else if(item.getItemId()==R.id.saveTocken){
            sendTokenToServer();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        displayView(id);
        if (item.getItemId() == R.id.menuLogout) {
            finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        User user = SharedPrefManager.getInstance(this).getUser();

        bundle.putString("uid", user.getUid());
        bundle.putString("fname", user.getFname());
        bundle.putString("lname", user.getLname());
        bundle.putString("email", user.getEmail());
        bundle.putString("uphone", user.getUphone());
        bundle.putString("roleid", user.getRoleID());
        bundle.putString("rolename", user.getRolename());
        Fragment fragment;
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
            case R.id.fabNewTicket:
                fragment = new NewTiFragment();
                fragment.setArguments(bundle);
                title = getString(R.string.string_new_ti);
                viewIsAtHome = false;
                break;
            case R.id.fabNewTask:
                fragment = new NewTaskFragment();
                fragment.setArguments(bundle);
                title = getString(R.string.string_new_task);
                viewIsAtHome = false;
                break;
            default:
                fragment = new TicketsFragment();
                fragment.setArguments(bundle);
                title  = getString(R.string.nav_menu_tickets);
                viewIsAtHome = true;
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
    @SuppressLint("SetTextI18n")
    private void changeTextNH(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView nvName = headerView.findViewById(R.id.nh_name);
        TextView nvRole = headerView.findViewById(R.id.nh_role);
        User user = SharedPrefManager.getInstance(this).getUser();

        nvName.setText(user.getFname() + " "+ user.getLname());
        nvRole.setText(user.getRolename());
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
        fab_main = findViewById(R.id.fabShow);
        fabNew_task = findViewById(R.id.fabNewTask);
        fabNew_ticket = findViewById(R.id.fabNewTicket);
        textview_newtask = findViewById(R.id.textview_ntask);
        textview_newti = findViewById(R.id.textview_nt);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    textview_newti.setVisibility(View.INVISIBLE);
                    textview_newtask.setVisibility(View.INVISIBLE);
                    fabNew_task.startAnimation(fab_close);
                    fabNew_ticket.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fabNew_task.setClickable(false);
                    fabNew_ticket.setClickable(false);
                    isOpen = false;
                } else {
                    textview_newti.setVisibility(View.VISIBLE);
                    textview_newtask.setVisibility(View.VISIBLE);
                    fabNew_task.startAnimation(fab_open);
                    fabNew_ticket.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fabNew_task.setClickable(true);
                    fabNew_ticket.setClickable(true);
                    isOpen = true;
                }

            }
        });

        fabNew_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(R.id.fabNewTask);
                textview_newti.setVisibility(View.INVISIBLE);
                textview_newtask.setVisibility(View.INVISIBLE);
                fabNew_task.startAnimation(fab_close);
                fabNew_ticket.startAnimation(fab_close);
                fab_main.startAnimation(fab_anticlock);
                isOpen = false;
            }
        });
//

//        FloatingActionButton fab = findViewById(R.id.fabNewTicket);
        fabNew_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(R.id.fabNewTicket);
                textview_newti.setVisibility(View.INVISIBLE);
                textview_newtask.setVisibility(View.INVISIBLE);
                fabNew_task.startAnimation(fab_close);
                fabNew_ticket.startAnimation(fab_close);
                fab_main.startAnimation(fab_anticlock);
                isOpen = false;
            }
        });
    }
    private void imgMeVoid(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        circleImageView = headerView.findViewById(R.id.action_profile);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(R.id.action_profile);
            }
        });
    }
    //storing token to mysql server
    private void sendTokenToServer() {

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        User user = SharedPrefManager.getInstance(this).getUser();
        final String email = user.getEmail();
        final String uid = user.getUid();

        if (token == null) {
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_STORE_TOKEN,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", uid);
                params.put("email", email);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}

