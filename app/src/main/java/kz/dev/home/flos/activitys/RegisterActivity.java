package kz.dev.home.flos.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;
import kz.dev.home.flos.services.NetworkUtil;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Register:";
    private EditText editTextEmail, editTextPassword, editTextMobilePhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        int status = NetworkUtil.getConnectivityStatus(this);
        initViews();
        try {
            if (status != NetworkUtil.getTypeNotConnected()) {
                findViewById(R.id.regBtn).setOnClickListener(view -> sendSMSVerification());
            } else {
                Toasty.error(this, getString(R.string.connection_status_error), Toast.LENGTH_LONG, true).show();
                Log.d(TAG, getString(R.string.connection_status_error));
            }
        } catch (Exception e) {
            Log.d(TAG, String.valueOf(e));
        }
    }
    @SuppressLint("ResourceType")
    private void sendSMSVerification() {
        final String email = editTextEmail.getText().toString();
        final String uphone = editTextMobilePhone.getText().toString();
        final String password = editTextPassword.getText().toString();

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
        if (TextUtils.isEmpty(uphone)) {
            editTextMobilePhone.setError(findViewById(R.string.empty_phone_error));
            editTextMobilePhone.requestFocus();
            return;
        }
        @SuppressLint("StaticFieldLeak")
        class SMSVerification extends AsyncTask<Void, Void, String> {
            private ProgressBar progressBar;

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                finish();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("uphone", uphone);
                Intent intent = new Intent(RegisterActivity.this, SMSVerifyActivity.class);
//                Bundle bundle = new Bundle();
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("uphone", uphone);
                finish();
                startActivity(intent);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_CNFNU, params);
            }
        }

        SMSVerification smsv = new SMSVerification();
        smsv.execute();

    }
    private void initViews() {
        editTextEmail = findViewById(R.id.reg_email);
        editTextPassword = findViewById(R.id.reg_password);
        editTextMobilePhone = findViewById(R.id.reg_phone);
    }


}
