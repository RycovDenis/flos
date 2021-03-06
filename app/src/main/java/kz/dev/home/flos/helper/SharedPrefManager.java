package kz.dev.home.flos.helper;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import kz.dev.home.flos.activitys.LoginActivity;
import kz.dev.home.flos.datamodels.User;


public class SharedPrefManager {



    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_UID, user.getUid());
        editor.putString(Constants.KEY_FNAME, user.getFname());
        editor.putString(Constants.KEY_LNAME, user.getLname());
        editor.putString(Constants.KEY_EMAIL, user.getEmail());
        editor.putString(Constants.KEY_ROLE_ID, user.getRoleID());
        editor.putString(Constants.KEY_ROLE_NAME, user.getRolename());
        editor.putString(Constants.KEY_UPHONE, user.getUphone());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.KEY_UID, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(Constants.KEY_UID, null),
                sharedPreferences.getString(Constants.KEY_FNAME, null),
                sharedPreferences.getString(Constants.KEY_LNAME, null),
                sharedPreferences.getString(Constants.KEY_EMAIL, null),
                sharedPreferences.getString(Constants.KEY_ROLE_ID, null),
                sharedPreferences.getString(Constants.KEY_ROLE_NAME, null),
                sharedPreferences.getString(Constants.KEY_UPHONE, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME_FCM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Constants.SHARED_PREF_NAME_FCM, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(Constants.TAG_TOKEN, null);
    }

}
