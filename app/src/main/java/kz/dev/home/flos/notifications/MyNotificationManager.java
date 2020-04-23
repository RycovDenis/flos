package kz.dev.home.flos.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.R;


/**
 * Created by Ravi on 31/03/15.
 */

public class MyNotificationManager {
    private Context mCtx;

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;

    }

    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;
    public String channelId;




    //the method will show a big notification with an image
    //parameters are title for message title, message for message text, url of the big image and an intent that will open
    //when you will tap on the notification
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showBigNotification(String title, String message, String url, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        0,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                );
        channelId = mCtx.getString(R.string.default_notification_channel_id);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx,channelId);
        Notification notification;
        notification = mBuilder
                .setTicker(title)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setContentText(message)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    mCtx.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        assert notificationManager != null;
        notificationManager.notify(0, notification);
    }

    //the method will show a small notification
    //parameters are title for message title, message for message text and an intent that will open
    //when you will tap on the notification
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showSmallNotification(String title, String message, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                );

        channelId = mCtx.getString(R.string.default_notification_channel_id);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx,channelId);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setContentText(message)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    mCtx.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        assert notificationManager != null;
        notificationManager.notify(ID_SMALL_NOTIFICATION, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendNotification(String title, String message, Intent intent) {
//        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        0 /* Request code */,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                );
        channelId = mCtx.getString(R.string.default_notification_channel_id);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mCtx, channelId)
                        .setTicker(title)
                        .setWhen(0)
                        .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(false)
                        .setSound(defaultSoundUri)
                        .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    mCtx.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    //The method will return Bitmap from an image URL
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
