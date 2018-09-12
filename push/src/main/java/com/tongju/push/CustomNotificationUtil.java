package com.tongju.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.alibaba.sdk.android.push.notification.CPushMessage;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by zhangyinlei on 2018/4/18 19:17
 */
public class CustomNotificationUtil {

    private static final int PUSH_NOTIFICATION_ID = (0x001);
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";

    /**
     * 接受到对应消息后，消息的弹出处理
     */
    public static void buildNotification(Context context, CPushMessage message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notification = new Notification.Builder(context)
                    .setChannelId(PUSH_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(message.getTitle())
                    .setContentText(message.getContent())
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setChannelId(PUSH_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(message.getTitle())
                    .setContentText(message.getContent())
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            notification = notificationBuilder.build();
        }
        notification.contentIntent = buildClickContent(context, message);
        notification.deleteIntent = buildDeleteContent(context, message);
        notificationManager.notify(message.hashCode(), notification);
    }

    public static PendingIntent buildClickContent(Context context, CPushMessage message) {
        Intent clickIntent = new Intent();
        clickIntent.setAction("com.push.click.action");
        //添加其他数据
        clickIntent.putExtra("message key", message);//将message放入intent中，方便通知自建通知的点击事件
        return PendingIntent.getService(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent buildDeleteContent(Context context, CPushMessage message) {
        Intent deleteIntent = new Intent();
        deleteIntent.setAction("com.push.click.delete.action");
        //添加其他数据
        deleteIntent.putExtra("message key", message);//将message放入intent中，方便通知自建通知的点击事件
        return PendingIntent.getService(context, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
