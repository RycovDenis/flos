package kz.dev.home.flos.AuthActivitys;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import kz.dev.home.flos.ContentActivitys.HomeActivity;
import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.R;
import kz.dev.home.flos.SupportClases.WebReq;

import static kz.dev.home.flos.SupportClases.GlobalClass.API_SIGNIN;

public class LoginActivity extends MainActivity {
    TextInputEditText emailEt,passwordEt;
    Button loginBtn;
    TextView signupNowTv;
    String email,password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_login);
        init();
        getViews();
    }

    private void getViews() {
        emailEt = findViewById(R.id.nameEt);
        signupNowTv = findViewById(R.id.signupNowTv);
        passwordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                loginValidation();
            }
        });
        signupNowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loginValidation() {
        progressBar = findViewById(R.id.progressBar);
        email = Objects.requireNonNull(emailEt.getText()).toString();
        password = Objects.requireNonNull(passwordEt.getText()).toString();

        if (email.length()==0){
//            usernameLayout.setHelperText(getString(R.string.email_input_error));
            Toast.makeText(context,getString(R.string.email_input_error),Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isEmailValid(email)){
//            usernameLayout.setHelperText(getString(R.string.email_input_error));
            Toast.makeText(context,getString(R.string.email_input_error),Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length()<5){
//            passwordLayout.setHelperText(getString(R.string.password_input_error));
            Toast.makeText(context,getString(R.string.password_input_error),Toast.LENGTH_SHORT).show();
            return;
        }

        //all inputs are validated now perform login request
        RequestParams params = new RequestParams();
//        params.add("type","login");
        params.add("email",email);
        params.add("password",password);

        WebReq.post(context, API_SIGNIN, params, new LoginActivity.ResponseHandler());
    }

    @SuppressLint("CommitPrefEdits")
    public void init() {
        context =this;
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
    }

    private class ResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onStart() {
            super.onStart();
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            Log.d("response ",response.toString()+" ");
            try {
                if (response.getBoolean("error")){
                    // failed to login
                    Toast.makeText(context,response.getString("message"),Toast.LENGTH_SHORT).show();
                }else{
                    // successfully logged in
                    JSONObject user = response.getJSONObject("user");
                    //save login values
                    sharedPrefEditor.putBoolean("login",true);
                    sharedPrefEditor.putString("id",user.getString("id"));
                    sharedPrefEditor.putString("role",user.getString("role_name"));
                    sharedPrefEditor.putString("status",user.getString("status"));
                    sharedPrefEditor.putString("user_id",user.getString("user_id"));
                    sharedPrefEditor.putString("email",user.getString("email"));
                    sharedPrefEditor.putString("f_name",user.getString("f_name"));
                    sharedPrefEditor.putString("l_name",user.getString("l_name"));
                    sharedPrefEditor.putString("m_name",user.getString("m_name"));
                    sharedPrefEditor.putString("inn",user.getString("inn"));
                    sharedPrefEditor.putString("mobile_phone",user.getString("mobile_phone"));
                    sharedPrefEditor.putString("ip",user.getString("INET_NTOA(ip)"));
                    sharedPrefEditor.putString("tab_num",user.getString("tab_num"));
                    sharedPrefEditor.putString("company_post",user.getString("company_post"));
                    sharedPrefEditor.putString("region",user.getString("region"));
                    sharedPrefEditor.putString("date_password",user.getString("date_password"));
                    sharedPrefEditor.apply();
                    sharedPrefEditor.commit();

                    //Move to home activity
                    intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    }
}