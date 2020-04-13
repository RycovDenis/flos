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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.R;
import kz.dev.home.flos.services.NetworkUtil;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Register:";
    private SmsVerifyCatcher smsVerifyCatcher;
    EditText editTextEmail, editTextPassword,editTextMobilePhone, etCode;
    private Boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        int status = NetworkUtil.getConnectivityStatus(this);
        initViews();
        initSMSVerification();
        try {
            if(status != NetworkUtil.getTypeNotConnected()){
                //if user presses on login
                //calling the method login
//                findViewById(R.id.regBtn).setOnClickListener(view -> registerUser());
                findViewById(R.id.regBtn).setOnClickListener(view -> sendSMSVerification());
            }else{
                Toasty.error(this, getString(R.string.connection_status_error), Toast.LENGTH_LONG, true).show();
                Log.d(TAG,getString(R.string.connection_status_error));
            }
        }catch (Exception e) {
            Log.d(TAG, String.valueOf(e));
        }
    }

    private void sendSMSVerification() {
        final String email = editTextEmail.getText().toString();
        final String uphone = editTextMobilePhone.getText().toString();

        @SuppressLint("StaticFieldLeak")
        class SMSVerification extends AsyncTask<Void, Void, String> {
            private ProgressBar progressBar;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                verifySMS();
                finish();
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("uphone", uphone);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_CNFNU, params);
            }
        }

        SMSVerification smsv = new SMSVerification();
        smsv.execute();

    }

    private void initSMSVerification() {
        //init views
        final EditText etCode = (EditText) findViewById(R.id.et_code);
        final Button btnVerify = (Button) findViewById(R.id.btn_verify);

        //init SmsVerifyCatcher
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                etCode.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });

        //set phone number filter if needed
//        smsVerifyCatcher.setPhoneNumberFilter("777");
        smsVerifyCatcher.setFilter("[S][M][S][-][C][E][N][T][R][E]");

        //button for sending verification code manual
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSVerification();
            }
        });
    }

    private void initViews(){
        editTextEmail = findViewById(R.id.reg_email);
        editTextPassword = findViewById(R.id.reg_password);
        editTextMobilePhone = findViewById(R.id.reg_phone);
        etCode = findViewById(R.id.et_code);
    }
    @SuppressLint("ResourceType")
    private void registerUser() {
        //first getting the values
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String mobilephone = editTextMobilePhone.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError(findViewById(R.string.empty_username_error));
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError(findViewById(R.string.empty_password_error));
            editTextPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mobilephone)) {
            editTextMobilePhone.setError(findViewById(R.string.empty_phone_error));
            editTextMobilePhone.requestFocus();
            return;
        }
        @SuppressLint("StaticFieldLeak")
        class CreateUser extends AsyncTask<Void, Void, String> {
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
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        Toasty.info(getApplicationContext(), obj.getString("message"),Toast.LENGTH_LONG, true).show();
                        String uid =  obj.getString("user_id");
                        Intent intent = new Intent(RegisterActivity.this, UserInfoActivity.class);
                        intent.putExtra("uid", uid);
                        intent.putExtra("uphone", mobilephone);
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
    private void verifySMS() {
        final String email = editTextEmail.getText().toString();
        final String vcode =  etCode.getText().toString();

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
        CreateUser cu = new CreateUser();
        cu.execute();
    }

    /**
     * Parse verification code
     *
     * @param message sms message
     * @return only four numbers from massage string
     */
    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
