package com.line_deposit.test.bd.utilites;

import com.line_deposit.test.bd.api.Network;
import com.line_deposit.test.bd.model.User;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public  static User user;
    public  static final Network network = new Network();
    public static Map<String, User> userMap = new HashMap<>();
}
