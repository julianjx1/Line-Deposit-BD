
package com.line_deposit.bd.model.push_notification.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationData {

    @SerializedName("PaymentType")
    @Expose
    private String paymentType;
    @SerializedName("TransactionProcess")
    @Expose
    private String transactionProcess;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTransactionProcess() {
        return transactionProcess;
    }

    public void setTransactionProcess(String transactionProcess) {
        this.transactionProcess = transactionProcess;
    }
}
