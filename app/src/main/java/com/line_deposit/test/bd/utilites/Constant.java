package com.line_deposit.test.bd.utilites;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.line_deposit.test.bd.api.Network;
import com.line_deposit.test.bd.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Constant {
    public static final String MyPREFERENCES = "LineDepositBD" ;
    private static SharedPreferences sharedpreferences;
    public  static User user;
    public  static final Network network = new Network();
    public static Map<String, User> userMap = new HashMap<>();
    public static Map<String, String> transactionProcessMap = new HashMap<>();

    public static String getDate(long date){
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(date));
    }
    public static long getCurrentTime(){
        Date date = Calendar.getInstance().getTime();
        return date.getTime();
    }
    public static void sharedPreferenceInit(Context context){
         sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedpreferences.getString("user", "");
        user = gson.fromJson(json, User.class);

    }
    public static void saveUserInformation(User user){
        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("user", json);
        prefsEditor.apply();
        Constant.user = user;
    }
    public static void removeUserInformation(){
        SharedPreferences.Editor prefsEditor = sharedpreferences.edit();
        prefsEditor.remove("user");
        prefsEditor.apply();
    }
}
