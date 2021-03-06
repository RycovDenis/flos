package kz.dev.home.flos.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;
import kz.dev.home.flos.services.NetworkUtil;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;

@SuppressLint("Registered")
public class UIActivity extends AppCompatActivity {
    private static final String TAG = "Register:";
    private EditText editTextFName;
    private EditText editTextMName;
    private EditText editTextLName;
    private EditText editTextINN;
    private EditText editTextTabNum;
    private EditText editTextCP;
    private EditText editTextRegion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setinfo);
        int status = NetworkUtil.getConnectivityStatus(this);
        initViews();
        try {
            if(status != NetworkUtil.getTypeNotConnected()){
                //if user presses on login
                //calling the method login
                findViewById(R.id.btnCreateUInfo).setOnClickListener(view -> setUserInfo());
            }else{
                Toasty.error(this, getString(R.string.connection_status_error), Toast.LENGTH_LONG, true).show();
                Log.d(TAG,getString(R.string.connection_status_error));
            }
        }catch (Exception e) {
            Log.d(TAG, String.valueOf(e));
        }
    }
    private void initViews(){
        editTextFName = findViewById(R.id.sui_fname);
        editTextMName = findViewById(R.id.sui_mname);
        editTextLName = findViewById(R.id.sui_lname);
        editTextINN = findViewById(R.id.sui_inn);
        editTextTabNum = findViewById(R.id.sui_tabnum);
        editTextCP = findViewById(R.id.sui_cp);
        editTextRegion = findViewById(R.id.sui_region);
    }
    @SuppressLint("ResourceType")
    private void setUserInfo() {
        //first getting the values
        final String uid = getIntent().getStringExtra("uid");
        final String fname = editTextFName.getText().toString();
        final String mname = editTextMName.getText().toString();
        final String lname = editTextLName.getText().toString();
        final String u_inn = editTextINN.getText().toString();
        final String u_tab_num = editTextTabNum.getText().toString();
        final String u_cp = editTextCP.getText().toString();
        final String u_region = editTextRegion.getText().toString();
        final String u_phone  = getIntent().getStringExtra("uphone");

        //validating inputs
        if (TextUtils.isEmpty(fname)) {
            editTextFName.setError(findViewById(R.string.empty_username_error));
            editTextFName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mname)) {
            editTextMName.setError(findViewById(R.string.empty_username_error));
            editTextMName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lname)) {
            editTextLName.setError(findViewById(R.string.empty_username_error));
            editTextLName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(u_inn)) {
            editTextINN.setError(findViewById(R.string.empty_username_error));
            editTextINN.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(u_tab_num)) {
            editTextTabNum.setError(findViewById(R.string.empty_username_error));
            editTextTabNum.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(u_cp)) {
            editTextCP.setError(findViewById(R.string.empty_password_error));
            editTextCP.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(u_region)) {
            editTextRegion.setError(findViewById(R.string.empty_phone_error));
            editTextRegion.requestFocus();
            return;
        }
        @SuppressLint("StaticFieldLeak")
        class UserInfo extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        Toasty.info(getApplicationContext(), obj.getString("message"),Toast.LENGTH_LONG, true).show();
                            Intent intent = new Intent(UIActivity.this, LoginActivity.class);
                            finish();
                            startActivity(intent);

                    } else {
                        Toasty.error(getApplicationContext(), R.string.email_input_error,Toast.LENGTH_LONG, true).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("fname", fname);
                params.put("mname", mname);
                params.put("lname", lname);
                params.put("uinn", u_inn);
                params.put("utabnum", u_tab_num);
                params.put("ucp", u_cp);
                params.put("uregion", u_region);
                params.put("uphone",u_phone);
                return requestHandler.sendPutRequest(URLs.URL_SETUINFO, params);
            }
        }
        UserInfo ui = new UserInfo();
        ui.execute();
    }
}
