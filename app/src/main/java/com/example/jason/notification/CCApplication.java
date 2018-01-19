package com.example.jason.notification;

import android.app.Application;
import android.util.Log;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by jason on 2018/1/19.
 */

public class CCApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO 集成：1.4、初始化数据服务SDK、初始化设备信息并启动推送服务
        // 初始化BmobSDK
        Bmob.initialize(this, "66780a64cd33942356701f85caf06551");
        // 使用推送服务时的初始化操作
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
        // 启动推送服务
        BmobPush.startWork(this);
    }
}
