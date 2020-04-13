package kz.dev.home.flos.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;
import kz.dev.home.flos.services.NetworkUtil;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;

public class SMSVerifyActivity extends AppCompatActivity {
    private static final String TAG = "SMSVerify:";
    private Boolean status;
    private EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        int status = NetworkUtil.getConnectivityStatus(this);
        initViews();
        findViewById(R.id.btn_verify).setOnClickListener(view -> verifySMS());
    }

    private void initViews() {
        etCode = findViewById(R.id.et_code);
    }

    private void verifySMS() {
        final String email  = getIntent().getStringExtra("email");
        final String vcode =  etCode.getText().toString();

        @SuppressLint("StaticFieldLeak")
        class VerifySMS extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        Toasty.info(getApplicationContext(), obj.getString("message"),Toast.LENGTH_LONG, true).show();
                        status =  obj.getBoolean("status");
                        if (status){
                            registerUser();
                        }
                        finish();

                    } else {
                        Toasty.error(getApplicationContext(), R.string.email_input_error,Toast.LENGTH_LONG, true).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("username", email);
                params.put("vcode", vcode);
                return requestHandler.sendPostRequest(URLs.URL_VERIFYSMS, params);
            }
        }
        VerifySMS vs = new VerifySMS();
        vs.execute();
    }
    @SuppressLint("ResourceType")
    private void registerUser() {
        //first getting the values

        final String email = getIntent().getStringExtra("email");
        final String password = getIntent().getStringExtra("password");
        final String mobilephone = getIntent().getStringExtra("uphone");

        //validating inputs
        @SuppressLint("StaticFieldLeak")
        class CreateUser extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        Toasty.info(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG, true).show();
                        String uid = obj.getString("uid");
                        Intent intent = new Intent(SMSVerifyActivity.this, UIActivity.class);
                        intent.putExtra("uid", uid);
                        intent.putExtra("uphone", mobilephone);
                        finish();
                        startActivity(intent);
                    } else {
                        Toasty.error(getApplicationContext(), R.string.email_input_error, Toast.LENGTH_LONG, true).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("username", email);
                params.put("password", password);
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }
        }
        CreateUser cu = new CreateUser();
        cu.execute();
    }
}
