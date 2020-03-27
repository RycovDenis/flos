package kz.dev.home.flos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import kz.dev.home.flos.AuthActivitys.SignupActivity;
import kz.dev.home.flos.ContentActivitys.HomeActivity;
import kz.dev.home.flos.SupportClases.WebReq;

import static kz.dev.home.flos.SupportClases.GlobalClass.API_SIGNUP;

@SuppressLint("Registered")
public class NewTicketActivity extends MainActivity {
    EditText nameEt,emailEt,passwordEt;
    Button signupBtn;
    TextView LoginNowTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setTitle("Registration");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        init();
        setContentView(R.layout.activity_signup);
        getViews();
    }

    @SuppressLint("CutPasteId")
    public void getViews() {
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.nameEt);
        passwordEt = findViewById(R.id.passwordEt);
        signupBtn = findViewById(R.id.SignupBtn);
        LoginNowTv = findViewById(R.id.LoginNowTv);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupValidation();
            }
        });
        LoginNowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void signupValidation() {
        name = nameEt.getText().toString();
        email = emailEt.getText().toString();
        email = sharedPreferences.getString("email","");
        password = passwordEt.getText().toString();

        //all inputs are validated now perform login request
        RequestParams params = new RequestParams();
//        params.add("email","email");
//        params.add("UserPhone","userPhone");
//        params.add("ticketTitle","ticketTitle");
//        params.add("ticketPriority","ticketPriority");
//        params.add("TicketDesc","TicketDesc");
        WebReq.post(context, API_SIGNUP, params, new NewTicketActivity.ResponseHandler());
    }

    private class ResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onStart() {
            super.onStart();
        }

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
                    sharedPrefEditor.putString("username",user.getString("username"));
                    sharedPrefEditor.putString("email",user.getString("email"));
                    sharedPrefEditor.apply();
                    sharedPrefEditor.commit();

                    //Move to home activity
                    intent = new Intent(context, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
