package com.line_deposit.bd.api;

import com.line_deposit.bd.model.push_notification.request.PushNotificationRequest;
import com.line_deposit.bd.model.push_notification.response.PushNotificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FirebaseApiInterface {

    @Headers({"Content-Type: application/json", "Authorization: key=AAAAuq0k7X0:APA91bGtklMEsDtfwYlFRKUFTgn4REkDN7WubGlXRYWpmM0Fh2nxFGUeWK5CrihE5cIU_aXys4d3V5ZkjCy_BstA-6-exwqqUI_dYwnF7HFp3LneOYFe9hjvsXKsuBx9nwIOAxwSfpIe"})
    @POST("fcm/send")
    Call<PushNotificationResponse> sendNotification(@Body PushNotificationRequest pushNotificationRequest);
}

