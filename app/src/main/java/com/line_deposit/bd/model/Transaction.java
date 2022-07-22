package com.line_deposit.bd.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Transaction {

    public String mobileNumber = "";

    public String username;
    public String transactionId;
    public String transactionType;
    public TransactionProcess transactionProcess;
    public String transactionNumber = "";
    public PaymentType paymentType;
    public Integer amount;

    public long date;

    public Transaction() {
    }


}

