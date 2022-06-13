package com.line_deposit.test.bd.model;

public class Transaction {

    private String mobileNumber;
    private String date;
    private String username;
    private String transactionId;
    private String transactionType;
    private TransactionProcess transactionProcess;
    private PaymentType paymentType;
    private Integer amount;

    public Transaction(String username, String mobileNumber, String transactionId, String transactionType, TransactionProcess transactionProcess, PaymentType paymentType, Integer amount,String date) {
        this.mobileNumber = mobileNumber;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionProcess = transactionProcess;
        this.paymentType = paymentType;
        this.amount = amount;
        this.username = username;
        this.date = date;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionProcess getTransactionProcess() {
        return transactionProcess;
    }

    public void setTransactionProcess(TransactionProcess transactionProcess) {
        this.transactionProcess = transactionProcess;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

}

