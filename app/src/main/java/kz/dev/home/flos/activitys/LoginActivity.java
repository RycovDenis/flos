package kz.dev.home.flos.activitys;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.JWT;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.R;
import kz.dev.home.flos.datamodels.URL_ch;
import kz.dev.home.flos.datamodels.User;
import kz.dev.home.flos.helper.SharedPrefManager;
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
                        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                            finish();
                            startActivity(new Intent(this, MainActivity.class));
                            return;
                        }
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
                User user = new User(
                        jwt.getClaim("uid").asString(),
                        jwt.getClaim("firstname").asString(),
                        jwt.getClaim("lastname").asString(),
                        jwt.getClaim("email").asString(),
                        jwt.getClaim("role_id").asString(),
                        jwt.getClaim("role_name").asString(),
                        jwt.getClaim("mphone").asString()
                );
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);

            }

        }
        UserLogin ul = new UserLogin();
        ul.execute();
    }

    public class UpdateApp extends AsyncTask<String,Void,Void>{
        private Context context;
        public void setContext(Context contextf){
            context = contextf;
        }

        @Override
        protected Void doInBackground(String... arg0) {
            try {
                URL url = new URL(arg0[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                String PATH = "/mnt/sdcard/Download/";
                File file = new File(PATH);
                file.mkdirs();
                File outputFile = new File(file, "update.apk");
                if(outputFile.exists()){
                    outputFile.delete();
                }
                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.close();
                is.close();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/Download/update.apk")), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                context.startActivity(intent);


            } catch (Exception e) {
                Log.e("UpdateAPP", "Update error! " + e.getMessage());
            }
            return null;
        }
    }


}