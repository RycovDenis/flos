package kz.dev.home.flos.receivers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;
import kz.dev.home.flos.services.NetworkUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "Network:";

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtil.getConnectivityStatus(context);
        if(status == NetworkUtil.getTypeNotConnected()) {
            Toasty.error(context, R.string.connection_status_error,Toast.LENGTH_LONG, true).show();
        }
        else {
            Toasty.success(context, R.string.connection_status_ok,Toast.LENGTH_LONG, true).show();
        }
    }
}
