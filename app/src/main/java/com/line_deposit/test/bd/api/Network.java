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
import com.line_deposit.test.bd.model.TransactionProcess;
import com.line_deposit.test.bd.model.User;
import com.line_deposit.test.bd.utilites.Constant;
import com.line_deposit.test.bd.view.Authentication.LoginObserver;
import com.line_deposit.test.bd.view.fragment.admin.TransactionRequestObserver;
import com.line_deposit.test.bd.view.fragment.admin.affiliate.MembersObserver;
import com.line_deposit.test.bd.view.fragment.user.TransactionObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Network {
    private  FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  DatabaseReference databaseReference = database.getReference();

    public LoginObserver loginObserver;
    public TransactionObserver transactionObserver;
    public TransactionRequestObserver transactionRequestObserver;
    public MembersObserver membersObserver;

    public void userList(){
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
    public void affiliateMembersList(User affiliate, String purpose){
        databaseReference.child("affiliate")
                .child(affiliate.username)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, String> users = new HashMap<>();
                        for (DataSnapshot user : snapshot.getChildren()) {
                            users.put(user.getKey(),user.getValue(String.class));
                        }
                        membersObserver.membersList(users, purpose);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void addUser(User user){
        databaseReference.child("users")
                .child(user.username)
                .setValue(user)
                .addOnSuccessListener(unused ->{
                    userList();
                    transactionObserver.onTransactionUpdate(true, "Information saved successfully");
                } );
    }
    public void addAffiliateMember(User user, User affiliate){
        databaseReference.child("affiliate")
                .child(affiliate.username)
                .child(String.valueOf(Constant.getCurrentTime()))
                .setValue(user.username);
    }
    public void removeAffiliateMember(String affiliate, String time){
        databaseReference.child("affiliate")
                .child(affiliate)
                .child(time)
                .removeValue();
    }

    public void login(String username, String password){
      //  databaseReference.child("users").child(user.getUsername()).setValue(user);

        databaseReference.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user == null || user.password.compareTo(password) != 0 ){
                    loginObserver.authenticationStatus(false, "Incorrect username or password", user);
                }else{

                    Constant.saveUserInformation(user);
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




    public void userTransactionRequest(PaymentType paymentType, String username){
        //  databaseReference.child("users").child(user.getUsername()).setValue(user);

        databaseReference.child("transactions")
                .child(paymentType.toString())
                .child(username)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Transaction> transactions = new ArrayList<>();
                        if (snapshot.hasChildren())
                        transactions.add(snapshot.getValue(Transaction.class));
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
                .removeValue();

    }


    public void updateTransactionRequest(Transaction transaction){
        //  databaseReference.child("users").child(user.getUsername()).setValue(user);\
        removeTransactionRequest(transaction);
        databaseReference.child("transactions")
                .child("userList")
                .child(transaction.username)
                .child(String.valueOf(transaction.date))
                .setValue(transaction);
        String affiliateUser = Constant.userMap.get(transaction.username).affiliateUser;
        if(affiliateUser != null && !affiliateUser.isEmpty() && transaction.transactionProcess == TransactionProcess.Completed){
            User user = Constant.userMap.get(affiliateUser);
            if(transaction.paymentType == PaymentType.Deposit){
                assert user != null;
                user.balance += transaction.amount * 0.1;
            }else{
                assert user != null;
                user.balance -= transaction.amount * 0.1;
                if (user.balance < 0)
                    user.balance = 0;
            }
            databaseReference.child("users")
                    .child(user.username)
                    .setValue(user);
            Constant.userMap.put(user.username, user);
            databaseReference.child("affiliation_transactions")
                    .child(affiliateUser)
                    .child(String.valueOf(transaction.date))
                    .setValue(transaction);
        }

        databaseReference.child("transactions")
                .child("adminList")
                .child(String.valueOf(transaction.date))
                .setValue(transaction)
                .addOnSuccessListener(unused -> {
                    transactionObserver.onTransactionUpdate(true, "Transaction process has completed successfully");
                })
                .addOnFailureListener(e -> {
                    transactionObserver.onTransactionUpdate(false, "Please check your internet connection");
                });
    }



    public void showAdminTransaction(){
        //  databaseReference.child("users").child(user.getUsername()).setValue(user);

        databaseReference.child("transactions")
                .child("adminList")
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

    public void showUserTransaction(){
        //  databaseReference.child("users").child(user.getUsername()).setValue(user);

        databaseReference.child("transactions")
                .child("userList")
                .child(Constant.user.username)
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

    public void addTransactionProcess(String processName, String mobileNumber){
        databaseReference.child("transactions_process")
                .child(processName)
                .setValue(mobileNumber)
                .addOnSuccessListener(unused ->  transactionObserver.onTransactionUpdate(true, "Information has saved successfully"))
                .addOnFailureListener(e -> transactionObserver.onTransactionUpdate(false, "Please check your internet connection"));
    }

    public void getTransactionProcessList(){
        databaseReference.child("transactions_process")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       Map<String,String> transactionMap = new HashMap<>();
                       for(DataSnapshot process: snapshot.getChildren()){
                           transactionMap.put(process.getKey(), process.getValue(String.class));
                       }
                       Constant.transactionProcessMap = transactionMap;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}
