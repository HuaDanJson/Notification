package com.example.jason.notification;

/**
 * Created by jason on 2018/1/19.
 */

public class NotificationBean {


    /**
     * alert : "First send Notification",

     */
    private String alert;

    public String getAlert() { return alert;}

    public void setAlert(String alert) { this.alert = alert;}

    @Override
    public String toString() {
        return "NotificationBean{" +
                "alert='" + alert + '\'' +
                '}';
    }
}
