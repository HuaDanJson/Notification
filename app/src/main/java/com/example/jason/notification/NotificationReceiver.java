package com.example.jason.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.push.PushConstants;

/**
 * Created by jason on 2018/1/19.
 */

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            NotificationBean notificationBean = GsonConverter.fromJson(intent.getStringExtra("msg"), NotificationBean.class);
            EventBus.getDefault().post(notificationBean);
            Log.i("bmob", "NotificationReceiver receive Notification = "+notificationBean.getAlert());
        }
    }
}
