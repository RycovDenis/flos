package kz.dev.home.flos.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    private static final int TYPE_WIFI = 1;
    private static final int TYPE_MOBILE = 2;
    private static final int TYPE_NOT_CONNECTED = 0;

    public static int getTypeMobile() {
        return TYPE_MOBILE;
    }

    public static int getTypeWifi() {
        return TYPE_WIFI;
    }

    public static int getTypeNotConnected() {
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }
}
