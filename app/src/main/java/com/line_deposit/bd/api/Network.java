package com.line_deposit.bd.api;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.line_deposit.bd.model.Advertisement;
import com.line_deposit.bd.model.PaymentType;
import com.line_deposit.bd.model.Transaction;
import com.line_deposit.bd.model.TransactionProcess;
import com.line_deposit.bd.model.User;
import com.line_deposit.bd.model.UserType;
import com.line_deposit.bd.model.push_notification.request.FirebaseNotification;
import com.line_deposit.bd.model.push_notification.request.NotificationData;
import com.line_deposit.bd.model.push_notification.request.PushNotificationRequest;
import com.line_deposit.bd.model.push_notification.response.PushNotificationResponse;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.AdminStatusObserver;
import com.line_deposit.bd.view.Authentication.LoginObserver;
import com.line_deposit.bd.view.fragment.admin.AdvertisementObserver;
import com.line_deposit.bd.view.fragment.admin.TransactionLimitObserver;
import com.line_deposit.bd.view.fragment.admin.TransactionRequestObserver;
import com.line_deposit.bd.view.fragment.admin.affiliate.MembersObserver;
import com.line_deposit.bd.view.fragment.user.TransactionObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Network {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = database.getReference();

    public LoginObserver loginObserver;
    public TransactionObserver transactionObserver;
    public TransactionRequestObserver transactionRequestObserver;
    public MembersObserver membersObserver;
    public TransactionLimitObserver limitObserver;
    public AdminStatusObserver adminStatusObserver;
    public AdvertisementObserver advertisementObserver;
    private FirebaseApiInterface firebaseApiInterface = FirebaseApiClient.getClient().create(FirebaseApiInterface.class);
    private final String ADMIN = "admin";
    private String deviceToken = "";

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

    public void updateUserStatus(Boolean status){
        databaseReference.child("active")
                .child(Constant.user.username)
                .child("status")
                .setValue(status);
    }

    public void isAdminActive(){
        databaseReference.child("active")
                .child("admin")
                .child("status")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean status = snapshot.getValue(Boolean.class);
                        if (status != null && adminStatusObserver != null)
                        adminStatusObserver.isAdminActive(status);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void setAdvertisement(Advertisement advertisement){
        databaseReference.child("advertisement")
                .setValue(advertisement)
                .addOnSuccessListener(unused ->{
                    if (transactionObserver != null)
                    transactionObserver.onTransactionUpdate(true, "Information saved successfully");
                } );
    }

    public void getAdvertisement(){
        databaseReference.child("advertisement")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Advertisement advertisement = snapshot.getValue(Advertisement.class);
                        if (advertisementObserver != null)
                            advertisementObserver.getAdvertisement(advertisement);
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
    public void setTransactionLimit(PaymentType paymentType, int limit){
        databaseReference.child("limit")
                .child(paymentType.toString())
                .setValue(limit)
                .addOnSuccessListener(unused ->{
                    if(transactionObserver != null)
                    transactionObserver.onTransactionUpdate(true, "Information saved successfully");
                } );
    }
    public void getTransactionLimit(PaymentType paymentType){
        databaseReference.child("limit")
                .child(paymentType.toString())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if(limitObserver != null )
                          if(snapshot.exists())
                            limitObserver.getLimit(snapshot.getValue(Integer.class));
                          else
                              limitObserver.getLimit(0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void updateUserInformation(User user){
        databaseReference.child("users")
                .child(user.username)
                .setValue(user);
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


    public void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful())
                        return;
                    deviceToken = task.getResult();
                    User user = Constant.user;
                    if(user != null){
                        user.deviceToken = deviceToken;
                        updateUserInformation(user);
                    }
                });
    }

    public void login(String username, String password){
      //  databaseReference.child("users").child(user.getUsername()).setValue(user);

        databaseReference.child("users").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user == null || user.password.compareTo(password) != 0 ){
                    if(loginObserver != null)
                    loginObserver.authenticationStatus(false, "Incorrect username or password", user);
                }else{
                    Constant.saveUserInformation(user);
                    getToken();
                    if(loginObserver != null)
                    loginObserver.authenticationStatus(true, "Login successfully", user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loginObserver.authenticationStatus(false, "Please check your internet", null);
            }
        });
    }

    private void sendNotificationToAdmin(Transaction transaction){
        User user = Constant.userMap.get(ADMIN);
        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();

       if (user != null)
        pushNotificationRequest.setTo(user.deviceToken);
        FirebaseNotification notification = new FirebaseNotification();
        notification.setTitle(transaction.paymentType+" Request");
        notification.setBody(transaction.username + " has sent "+transaction.amount+" taka "+transaction.paymentType.toString().toLowerCase(Locale.ROOT)+" request");
        NotificationData data = new NotificationData();
        data.setPaymentType(transaction.paymentType.toString());
        data.setTransactionProcess(transaction.transactionProcess.toString());
        pushNotificationRequest.setNotification(notification);
        pushNotificationRequest.setData(data);

        Call<PushNotificationResponse> responseCall =  firebaseApiInterface.sendNotification(pushNotificationRequest);
        responseCall.enqueue(new Callback<PushNotificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<PushNotificationResponse> call, Response<PushNotificationResponse> response) {

            }

            @Override
            public void onFailure(@NonNull Call<PushNotificationResponse> call, Throwable t) {

            }
        });
    }
    private void sendNotificationToUser(Transaction transaction){
        User user = Constant.userMap.get(transaction.username);
        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();

       if (user != null)
        pushNotificationRequest.setTo(user.deviceToken);
        FirebaseNotification notification = new FirebaseNotification();
        notification.setTitle(transaction.paymentType+" Request");
        notification.setBody("Your "+transaction.amount+" taka request has been "+transaction.transactionProcess.toString().toLowerCase(Locale.ROOT));
        NotificationData data = new NotificationData();
        data.setPaymentType(transaction.paymentType.toString());
        data.setTransactionProcess(transaction.transactionProcess.toString());
        pushNotificationRequest.setNotification(notification);
        pushNotificationRequest.setData(data);

        Call<PushNotificationResponse> responseCall =  firebaseApiInterface.sendNotification(pushNotificationRequest);
        responseCall.enqueue(new Callback<PushNotificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<PushNotificationResponse> call, Response<PushNotificationResponse> response) {

            }

            @Override
            public void onFailure(@NonNull Call<PushNotificationResponse> call, Throwable t) {

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
                    sendNotificationToAdmin(transaction);
                })
                .addOnFailureListener(e -> transactionObserver.onTransactionUpdate(false, "Please check your internet connection"));
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
                        if(transactionRequestObserver != null)
                        transactionRequestObserver.transactionRequests(transactions);
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
                        if(transactionRequestObserver != null)
                        transactionRequestObserver.transactionRequests(transactions);
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
        User userInfo = Constant.userMap.get(transaction.username);
        assert userInfo != null;
        if(userInfo.userType == UserType.user) {
            databaseReference.child("transactions")
                    .child("userList")
                    .child(transaction.username)
                    .child(String.valueOf(transaction.date))
                    .setValue(transaction);

        }
        if(userInfo.userType == UserType.affiliate)
            databaseReference.child("affiliation_transactions")
                    .child(transaction.username)
                    .child(String.valueOf(transaction.date))
                    .setValue(transaction);

        if(transaction.transactionProcess == TransactionProcess.Completed) {
            if (transaction.paymentType == PaymentType.Deposit) {
                userInfo.balance += transaction.amount;
            } else {
                userInfo.balance -= transaction.amount;
            }
        }
        updateUserInformation(userInfo);
        Constant.userMap.put(userInfo.username, userInfo);
        String affiliateUser = Objects.requireNonNull(Constant.userMap.get(transaction.username)).affiliateUser;
        if(affiliateUser != null && !affiliateUser.isEmpty() && transaction.transactionProcess == TransactionProcess.Completed){
            User user = Constant.userMap.get(affiliateUser);
            assert user != null;
            if(transaction.transactionProcess == TransactionProcess.Completed) {

                if (transaction.paymentType == PaymentType.Deposit) {
                    user.balance += (transaction.amount * user.affiliatePercent)/ 100;
                } else {
                    user.balance -= (transaction.amount * user.affiliatePercent)/ 100;

                }
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
                    sendNotificationToUser(transaction);
                })
                .addOnFailureListener(e -> transactionObserver.onTransactionUpdate(false, "Please check your internet connection"));
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
                        transactionRequestObserver.transactionRequests(transactions);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public void showAffiliateTransaction(){
        //  databaseReference.child("users").child(user.getUsername()).setValue(user);

        databaseReference.child("affiliation_transactions")
                .child(Constant.user.username)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Transaction> transactions = new ArrayList<>();
                        for (DataSnapshot user : snapshot.getChildren()) {
                            transactions.add(user.getValue(Transaction.class));
                        }
                        if(transactionRequestObserver != null)
                        transactionRequestObserver.transactionRequests(transactions);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void showUserTransaction(){

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
                        if(transactionRequestObserver != null)
                        transactionRequestObserver.transactionRequests(transactions);
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
