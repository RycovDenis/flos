package kz.dev.home.flos.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import java.util.Objects;

public class BatteryLevelReceiver extends BroadcastReceiver {
    private static final String TAG = "TGbattery";


    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"battery Receiver was called now");

        boolean batteryLow = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BATTERY_LOW);
        boolean batteryOK = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BATTERY_OKAY);
        boolean batteryPowerOn = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_POWER_CONNECTED);
        boolean batteryPowerOff = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_POWER_DISCONNECTED);
        boolean batteryChange = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BATTERY_CHANGED);
        String intentAction = intent.getAction();

        // register SHUTDOWN event
        try {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            Log.d(TAG,intentAction+"   batteryChange="+batteryChange+"   flagLo="+batteryLow+"  batteryOK="+batteryOK+"  batteryPowerOn="+batteryPowerOn+"  batteryPowerOff="+batteryPowerOff+"\n  level="+ level +"  temp="+ temp +"  scale="+ scale +"  voltage="+ voltage);

        } catch (Exception e){
            Log.d(TAG, String.valueOf(e));// catch etc
        }
    }
}
