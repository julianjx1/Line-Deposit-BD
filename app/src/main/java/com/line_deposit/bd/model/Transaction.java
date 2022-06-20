package com.line_deposit.bd.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Transaction {

    public String mobileNumber;

    public String username;
    public String transactionId;
    public String transactionType;
    public TransactionProcess transactionProcess;
    public PaymentType paymentType;
    public Integer amount;

    public long date;

    public Transaction() {
    }



//    public String getMobileNumber() {
//        return mobileNumber;
//    }
//
//    public void setMobileNumber(String mobileNumber) {
//        this.mobileNumber = mobileNumber;
//    }
//
//    public String getTransactionId() {
//        return transactionId;
//    }
//
//    public void setTransactionId(String transactionId) {
//        this.transactionId = transactionId;
//    }
//
//    public String getTransactionType() {
//        return transactionType;
//    }
//
//    public void setTransactionType(String transactionType) {
//        this.transactionType = transactionType;
//    }
//
//    public TransactionProcess getTransactionProcess() {
//        return transactionProcess;
//    }
//
//    public void setTransactionProcess(TransactionProcess transactionProcess) {
//        this.transactionProcess = transactionProcess;
//    }
//
//    public PaymentType getPaymentType() {
//        return paymentType;
//    }
//
//    public void setPaymentType(PaymentType paymentType) {
//        this.paymentType = paymentType;
//    }
//
//    public Integer getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Integer amount) {
//        this.amount = amount;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getUsername() {
//        return username;
//    }

}

