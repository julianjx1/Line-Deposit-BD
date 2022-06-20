package com.line_deposit.bd.view.fragment.admin;

import com.line_deposit.bd.model.Transaction;

import java.util.ArrayList;

public interface TransactionRequestObserver {
     void transactionRequests(ArrayList<Transaction> transactions);
}
