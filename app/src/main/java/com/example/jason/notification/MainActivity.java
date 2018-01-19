package com.example.jason.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.PushListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "5f38f08929314ed5b3f0f4992b847582");
        BmobInstallationManager.getInstallationId();
        BmobInstallationManager.getInstance().getCurrentInstallation();
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {
                if (e == null) {
                    Log.i("bmob", bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                } else {
                    Log.i("bmob", e.getMessage());
                }
            }
        });

        findViewById(R.id.btn_send_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobPushManager bmobPushManager = new BmobPushManager();
                bmobPushManager.pushMessageAll("Client send notification", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("bmob", "Client 推送成功！");
                        } else {
                            Log.i("bmob", "Client 异常：" + e.getMessage());
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(NotificationBean notificationBean) {
//      定义一个PendingIntent,点击Intent可以启动一个新的Intent
        Log.i("bmob", "getEventData ：" + notificationBean.getAlert());
        Intent intent = new Intent(MainActivity.this, WelcomeAcitivity.class);
        PendingIntent pit = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
//                设置图片文字提示方式等等
        Notification.Builder builder = new Notification.Builder(MainActivity.this);
        builder.setContentTitle(notificationBean.getAlert())                        //标题
                .setContentText("Notification content")      //内容
                .setSubText("——内容下面的一小段文字")                    //内容下面的一小段文字
                .setTicker("收到信息后状态栏显示的文字信息~")             //收到信息后状态栏显示的文字信息
                .setWhen(System.currentTimeMillis())           //设置通知时间
                .setSmallIcon(R.mipmap.ic_launcher)            //设置小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                .setAutoCancel(true)                           //设置点击后取消Notification
                .setContentIntent(pit);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
