package com.line_deposit.bd.view.fragment.user;

public interface TransactionObserver {
    void onTransactionUpdate(Boolean success, String message);
}
