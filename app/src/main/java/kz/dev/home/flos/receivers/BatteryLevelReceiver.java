package kz.dev.home.flos.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import java.util.Objects;

public class BatteryLevelReceiver extends BroadcastReceiver {
    private static final String TAG = "TGbattery";


    int scale = -1;
    int level = -1;
    int voltage = -1;
    int temp = -1;

    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"battery Receiver was called now");
        String deviceUuid = "INVALID_IMEI";

        boolean batteryLow = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BATTERY_LOW);
        boolean batteryOK = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BATTERY_OKAY);
        boolean batteryPowerOn = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_POWER_CONNECTED);
        boolean batteryPowerOff = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_POWER_DISCONNECTED);
        boolean batteryChange = Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BATTERY_CHANGED);
        String intentAction = intent.getAction();

        // register SHUTDOWN event
        try {
            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            Log.d(TAG,intentAction+"   batteryChange="+batteryChange+"   flagLo="+batteryLow+"  batteryOK="+batteryOK+"  batteryPowerOn="+batteryPowerOn+"  batteryPowerOff="+batteryPowerOff+"\n  level="+level+"  temp="+temp+"  scale="+scale+"  voltage="+voltage);

        } catch (Exception e){
            Log.d(TAG, String.valueOf(e));// catch etc
        }
    }
}
