package kz.dev.home.flos.activitys;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.R;
import kz.dev.home.flos.datamodels.MyJwt;
import kz.dev.home.flos.datamodels.URL_ch;
import kz.dev.home.flos.services.NetworkUtil;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login:";
    private EditText editTextUsername;
    private EditText editTextPassword;
    private String username;
    private String password;


    //account name identifier.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int status = NetworkUtil.getConnectivityStatus(this);

                editTextUsername = findViewById(R.id.nameEt);
                editTextPassword = findViewById(R.id.passwordEt);
                try {
                    if(status != NetworkUtil.getTypeNotConnected()){
                        findViewById(R.id.loginBtn).setOnClickListener(view -> userLogin());
                        Button btnOk = findViewById(R.id.newUserBtn);
                        View.OnClickListener oclBtnOk = v -> {
                            Intent i1 = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(i1);

                        };
                        btnOk.setOnClickListener(oclBtnOk);
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

            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();

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
                        jwtUserParse(token);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        Toasty.error(getApplicationContext(), R.string.email_input_error,Toast.LENGTH_LONG, true).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private void jwtUserParse(String token){
                JWT jwt = new JWT(token);
                MyJwt myJWT = new MyJwt();
                Claim uid = jwt.getClaim("uid");
                Claim firstname = jwt.getClaim("firstname");
                Claim lastname = jwt.getClaim("lastname");
                Claim email = jwt.getClaim("email");
                Claim role_id = jwt.getClaim("role_id");
                Claim u_phone = jwt.getClaim("mphone");
                Claim role_name = jwt.getClaim("role_name");
                myJWT.setParsedValueUid( uid.asString()) ;
                myJWT.setParsedValueFname (firstname.asString());
                myJWT.setParsedValueLname (lastname.asString());
                myJWT.setParsedValueEmail ( email.asString());
                myJWT.setParsedValueUphone ( u_phone.asString());
                myJWT.setParsedValueRoleID ( role_id.asString());
                myJWT.setParsedValueRoleName ( role_name.asString());
            }


            @Override
            protected String doInBackground(Void... voids) {
                URL_ch urls = new URL_ch();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                //returing the response
                if(isSuccessPingInThread()){
                    urls.setHOST(URLs.HOST_PRIMAR);
                }else{
                    urls.setHOST(URLs.HOST_SECOND);
                }
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);

            }

            private boolean isSuccessPingInThread() {
                return true;
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }


}