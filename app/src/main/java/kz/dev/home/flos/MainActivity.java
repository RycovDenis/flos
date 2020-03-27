package kz.dev.home.flos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.ResponseHandlerInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kz.dev.home.flos.AuthActivitys.LoginActivity;
import kz.dev.home.flos.SupportClases.WebReq;

import static kz.dev.home.flos.SupportClases.GlobalClass.API_SIGNIN;

public class MainActivity extends AppCompatActivity {
    public Context context;
    public Intent intent;
    protected SharedPreferences sharedPreferences;
    public String SHARED_PREF_NAME ="user_pref";
    public SharedPreferences.Editor sharedPrefEditor;
    protected String name,email,password;

    protected boolean isLoggedIn(){
        return sharedPreferences.getBoolean("login",false);
    }

    protected void logout(){
        WebReq.post(context, API_SIGNIN, params, new MainActivity.ResponseHandler());
        sharedPrefEditor.putBoolean("login",false);
        sharedPrefEditor.apply();
        sharedPrefEditor.commit();
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @SuppressLint("CommitPrefEdits")
    public void init() {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class ResponseHandler implements ResponseHandlerInterface {
    }
}