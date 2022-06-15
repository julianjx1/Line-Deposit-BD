package com.line_deposit.test.bd.view.fragment.user;

public interface TransactionObserver {
    void onTransactionUpdate(Boolean success, String message);
}
