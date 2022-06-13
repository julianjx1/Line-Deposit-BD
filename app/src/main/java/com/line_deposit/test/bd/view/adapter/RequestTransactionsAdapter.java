package com.line_deposit.test.bd.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.line_deposit.test.bd.BR;
import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.databinding.RequestTransactionItemBinding;
import com.line_deposit.test.bd.model.Transaction;
import com.line_deposit.test.bd.model.User;

import java.util.List;
import java.util.Map;

public class RequestTransactionsAdapter extends RecyclerView.Adapter<RequestTransactionsAdapter.ViewHolder>{

    private List<Transaction> transactions;
    private Map<String, User> userMap;
    private Context context;

    public RequestTransactionsAdapter(List<Transaction> transactions, Map<String, User> userMap, Context context) {
        this.transactions = transactions;
        this.userMap = userMap;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestTransactionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RequestTransactionItemBinding binding = DataBindingUtil.inflate( LayoutInflater.from(parent.getContext()),R.layout.request_transaction_item,
                parent, false);


        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull RequestTransactionsAdapter.ViewHolder holder, int position) {

        Transaction transaction = transactions.get(position);
        User user = userMap.get(transaction.getUsername());
        holder.bind(transaction,user);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public RequestTransactionItemBinding transactionsItemBinding;

        public ViewHolder(RequestTransactionItemBinding transactionsItemBinding) {
            super(transactionsItemBinding.getRoot());
            this.transactionsItemBinding = transactionsItemBinding;
        }

        public void bind(Transaction transaction, User user) {
            transactionsItemBinding.setVariable(BR.transaction, transaction);
            transactionsItemBinding.setVariable(BR.user, user);
            transactionsItemBinding.executePendingBindings();
        }
    }




}
