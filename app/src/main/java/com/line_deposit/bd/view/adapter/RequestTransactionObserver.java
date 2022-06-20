package com.line_deposit.bd.view.adapter;

import com.line_deposit.bd.model.Transaction;

public interface RequestTransactionObserver {
    void onAccept(Transaction transaction);
    void onReject(Transaction transaction);
}
