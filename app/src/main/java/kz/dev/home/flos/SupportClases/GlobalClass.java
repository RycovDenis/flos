package kz.dev.home.flos.SupportClases;

import android.app.Application;

public class GlobalClass extends Application {
    public static final String BASE_HOST = "http://16srb5pk.ddns.net/";
//    public static final String BASE_HOST = "http://192.168.0.100/";
    public static final String API_ROOT = "restdroid/";
    public static final String API_SIGNUP = "signup";
    public static final String API_SIGNIN = "signin";
    public static final String BASE_URL =BASE_HOST + API_ROOT ;
//    public static final String BASE_URL = "http:/localhost:8888/androidApp/api.php";

    private static GlobalClass singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public static GlobalClass getInstance() {
        return singleton;
    }
}
