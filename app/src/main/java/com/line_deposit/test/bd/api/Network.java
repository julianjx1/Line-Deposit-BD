package com.line_deposit.test.bd.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.line_deposit.test.bd.model.PaymentType;
import com.line_deposit.test.bd.model.Transaction;
import com.line_deposit.test.bd.model.User;
import com.line_deposit.test.bd.utilites.Constant;
import com.line_deposit.test.bd.view.Authentication.LoginObserver;
import com.line_deposit.test.bd.view.fragment.admin.TransactionRequestObserver;
import com.line_deposit.test.bd.view.fragment.user.TransactionObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Network {
    private  FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  DatabaseReference databaseReference = database.getReference();

    public LoginObserver loginObserver;
    public TransactionObserver transactionObserver;
    public TransactionRequestObserver transactionRequestObserver;

    public void login(String username, String password){
      //  databaseReference.child("users").child(user.getUsername()).setValue(user);

        databaseReference.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user == null || user.password.compareTo(password) != 0 ){
                    loginObserver.authenticationStatus(false, "Incorrect username or password", user);
                }else{
                    Constant.user = user;
                    loginObserver.authenticationStatus(true, "Login successfully", user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loginObserver.authenticationStatus(false, "Please check your internet", null);
            }
        });
    }
    public void userTransaction(Transaction transaction){
      //  databaseReference.child("users").child(user.getUsername()).setValue(user);\
        transaction.username = Constant.user.username;
        databaseReference.child("transactions")
                .child(transaction.paymentType.toString())
                .child(Constant.user.username)
                .setValue(transaction)
                .addOnSuccessListener(unused -> {
                    transactionObserver.onTransactionUpdate(true, "Your "+transaction.paymentType+" request has been sent successfully");
                })
                .addOnFailureListener(e -> {
                    transactionObserver.onTransactionUpdate(false, "Please check your internet connection");
                });
    }

    public void userTransactionRequest(PaymentType paymentType){
        //  databaseReference.child("users").child(user.getUsername()).setValue(user);

        databaseReference.child("transactions")
                .child(paymentType.toString())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Transaction> transactions = new ArrayList<>();
                        for (DataSnapshot user : snapshot.getChildren()) {
                            transactions.add(user.getValue(Transaction.class));
                        }
                        transactionRequestObserver.transactionRequstions(transactions);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void removeTransactionRequest(Transaction transaction){
        //  databaseReference.child("users").child(user.getUsername()).setValue(user);\
      //  transaction.username = Constant.user.username;
        databaseReference.child("transactions")
                .child(transaction.paymentType.toString())
                .child(transaction.username)
                .child(transaction.paymentType.toString())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public void updateTransactionRequest(Transaction transaction){
        //  databaseReference.child("users").child(user.getUsername()).setValue(user);\
        removeTransactionRequest(transaction);
        databaseReference.child("transactions")
                .child("list")
                .child(transaction.username)
                .child(transaction.year)
                .child(transaction.month)
                .child(transaction.day)
                .setValue(transaction)
                .addOnSuccessListener(unused -> {
                    transactionObserver.onTransactionUpdate(true, "Transaction process has completed successfully");
                })
                .addOnFailureListener(e -> {
                    transactionObserver.onTransactionUpdate(false, "Please check your internet connection");
                });
    }
    public void userList(){
        //  databaseReference.child("users").child(user.getUsername()).setValue(user);

        databaseReference.child("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, User> userMap = new HashMap<>();
                        for (DataSnapshot user : snapshot.getChildren()) {
                            userMap.put(user.getKey(), user.getValue(User.class));
                        }
                        Constant.userMap = userMap;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }




}
