package com.line_deposit.test.bd.utilites;

import android.icu.text.SimpleDateFormat;

import com.line_deposit.test.bd.api.Network;
import com.line_deposit.test.bd.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Constant {

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
}
