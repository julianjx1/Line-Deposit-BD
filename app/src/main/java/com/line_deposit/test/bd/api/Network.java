package com.line_deposit.test.bd.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.line_deposit.test.bd.model.User;

public class Network {
    private  FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  DatabaseReference databaseReference = database.getReference();

    public User login(User user){
        databaseReference.child("users").child(user.getUsername()).setValue(user);
        return  user;
    }
}
