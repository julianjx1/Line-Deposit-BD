package com.line_deposit.test.bd.view.adapter;

import com.line_deposit.test.bd.model.Transaction;

public interface RequestTransactionObserver {
    void onAccept(Transaction transaction);
    void onReject(Transaction transaction);
}
