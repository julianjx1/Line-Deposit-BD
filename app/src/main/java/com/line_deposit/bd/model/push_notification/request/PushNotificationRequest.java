
package com.line_deposit.bd.model.push_notification.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PushNotificationRequest {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("notification")
    @Expose
    private FirebaseNotification notification;
    @SerializedName("data")
    @Expose
    private NotificationData data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public FirebaseNotification getNotification() {
        return notification;
    }

    public void setNotification(FirebaseNotification notification) {
        this.notification = notification;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

}
