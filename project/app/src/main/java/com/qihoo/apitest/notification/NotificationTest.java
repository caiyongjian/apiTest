package com.qihoo.apitest.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.qihoo.apitest.MainActivity;
import com.qihoo.apitest.R;

/**
 * Created by caiyongjian on 16-6-29.
 */
public class NotificationTest {

    Context mContext;
    public NotificationTest(Context context) {
        mContext = context;
    }

    public void doNotify() {
        Intent intent = new Intent(mContext, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(mContext, 10, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        String ticker = "测试~";
        String title = "测试~";
        String text = "测试";
        show(ticker, title, text, pi, true, false, 100);
    }

    public void show(String ticker, String title, String text, PendingIntent pi, boolean shock, boolean music, int id) {
//        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
//        builder.setSmallIcon(R.drawable.qiangpiao_notification_small_icon)
//                .setTicker(ticker)
//                .setContentTitle(title)
//                .setContentText(text)
//                .setContentIntent(pi)
//                .setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_LIGHTS);
//
//        if (!("vivo".equalsIgnoreCase(Build.BRAND) && "vivo S11t".equalsIgnoreCase(Build.MODEL))) {
//            Bitmap largeIcon = BitmapFactory.decodeResource(mContext.getResources(),
//                    R.drawable.notification_large_icon);
//            if (largeIcon != null)
//                builder.setLargeIcon(largeIcon);
//        }
//
//        if (shock) builder.setDefaults(Notification.DEFAULT_VIBRATE);
//
//        Notification notification = builder.build();
//        notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_SHOW_LIGHTS;
//        nm.cancel(id);
//        nm.notify(id, notification);
    }
}
