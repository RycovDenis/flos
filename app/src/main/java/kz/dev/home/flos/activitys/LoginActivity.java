package kz.dev.home.flos.activitys;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.R;
import kz.dev.home.flos.services.NetworkUtil;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login:";

    EditText editTextUsername, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int status = NetworkUtil.getConnectivityStatus(this);
        editTextUsername = findViewById(R.id.nameEt);
        editTextPassword = findViewById(R.id.passwordEt);
        try {
        if(status != NetworkUtil.getTypeNotConnected()){
            //if user presses on login
            //calling the method login
            findViewById(R.id.loginBtn).setOnClickListener(view -> userLogin());
        }else{
            Toasty.error(this, getString(R.string.connection_status_error),Toast.LENGTH_LONG, true).show();
            Log.d(TAG,getString(R.string.connection_status_error));
        }
    }catch (Exception e) {
        Log.d(TAG, String.valueOf(e));
    }

    }

    @SuppressLint("ResourceType")
    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError(findViewById(R.string.empty_username_error));
            editTextUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError(findViewById(R.string.empty_password_error));
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine

        @SuppressLint("StaticFieldLeak")
        class UserLogin extends AsyncTask<Void, Void, String> {
            private ProgressBar progressBar;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toasty.info(getApplicationContext(), obj.getString("message"),Toast.LENGTH_LONG, true).show();
                        String token =  obj.getString("jwt");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("token", token);
                        //starting the profile activity
                        finish();
                        startActivity(intent);
                    } else {
                        Toasty.error(getApplicationContext(), R.string.email_input_error,Toast.LENGTH_LONG, true).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}