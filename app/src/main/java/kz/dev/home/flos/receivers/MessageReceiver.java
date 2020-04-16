package kz.dev.home.flos.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;

public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase("android.intent.action.ACTION_POWER_DISCONNECTED")) {
            Toasty.info(context, R.string.power_dsc,Toast.LENGTH_LONG, true).show();
        }
        if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase("android.intent.action.ACTION_POWER_CONNECTED")){
            Toasty.info(context, R.string.power_conn,Toast.LENGTH_LONG, true).show();
        }
        if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase("android.intent.action.BATTERY_LOW")){
            Toasty.error(context, R.string.battery_low,Toast.LENGTH_LONG, true).show();
        }
    }
}
