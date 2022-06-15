package com.line_deposit.test.bd.view.fragment.admin;

import com.line_deposit.test.bd.model.Transaction;

import java.util.ArrayList;

public interface TransactionRequestObserver {
    public void transactionRequstions(ArrayList<Transaction> transactions);
}
