package com.line_deposit.bd.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.line_deposit.bd.BR;
import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.RequestTransactionItemBinding;
import com.line_deposit.bd.model.PaymentType;
import com.line_deposit.bd.model.Transaction;
import com.line_deposit.bd.model.User;
import com.line_deposit.bd.model.UserType;
import com.line_deposit.bd.utilites.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestTransactionsAdapter extends RecyclerView.Adapter<RequestTransactionsAdapter.ViewHolder>{

    private List<Transaction> transactions;
    private Map<String, User> userMap;
    private Context context;
    private boolean show = false;

    private RequestTransactionObserver requestTransactionObserver;


    public RequestTransactionsAdapter(List<Transaction> transactions, Map<String, User> userMap, Context context, RequestTransactionObserver requestTransactionObserver) {
        this.transactions = transactions;
        this.userMap = userMap;
        this.context = context;
        this.requestTransactionObserver = requestTransactionObserver;
    }

    public RequestTransactionsAdapter(List<Transaction> transactions, Map<String, User> userMap, Context context, RequestTransactionObserver requestTransactionObserve, boolean show) {
        this(transactions, userMap, context,requestTransactionObserve);
        this.show = show;
    }

    public void updateList(ArrayList<Transaction> list){
        transactions.addAll(list);
        notifyDataSetChanged();
    }

    public void updateAdapter(Transaction transaction){
        transactions.remove(transaction);
        notifyDataSetChanged();
    }

    public void changedInformation(Transaction transaction){
        int index = transactions.indexOf(transaction);
        transactions.set(index,transaction);
        notifyItemChanged(index);
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
        User user = userMap.get(transaction.username);
        holder.bind(transaction,user);
        if(show){
            holder.transactionsItemBinding.edit.setVisibility((Constant.user.userType == UserType.admin) ? View.VISIBLE : View.GONE);
            holder.transactionsItemBinding.edit.setOnClickListener(v -> {
                holder.transactionsItemBinding.edit.setVisibility(View.GONE);
                holder.transactionsItemBinding.accept.setVisibility(View.VISIBLE);
                holder.transactionsItemBinding.reject.setVisibility(View.VISIBLE);
                if(transaction.paymentType == PaymentType.Withdraw)
                holder.transactionsItemBinding.transactionId.setEnabled(true);

            });

            holder.transactionsItemBinding.accept.setVisibility(View.GONE);
            holder.transactionsItemBinding.reject.setVisibility(View.GONE);
            holder.transactionsItemBinding.transactionId.setEnabled(false);
        }else{
            holder.transactionsItemBinding.edit.setVisibility(View.GONE);
        }
        holder.transactionsItemBinding.paymentType.setTextColor((transaction.paymentType == PaymentType.Withdraw)? context.getResources().getColor(R.color.red) : context.getResources().getColor(R.color.green));
        holder.transactionsItemBinding.accept.setOnClickListener(view -> {
            if(transaction.paymentType == PaymentType.Withdraw){
                String transactionId = holder.transactionsItemBinding.transactionId.getText().toString();
                if(transactionId.isEmpty()){
                    Toast.makeText(context, "Transaction Id must be given", Toast.LENGTH_SHORT).show();
                    return;
                }
                transaction.transactionId = transactionId;
            }
         requestTransactionObserver.onAccept(transaction);
            if(show){
                holder.transactionsItemBinding.edit.setVisibility(View.VISIBLE);
                holder.transactionsItemBinding.accept.setVisibility(View.GONE);
                holder.transactionsItemBinding.reject.setVisibility(View.GONE);
            }
        });
        holder.transactionsItemBinding.reject.setOnClickListener(view -> {
            requestTransactionObserver.onReject(transaction);
            if(show){
                holder.transactionsItemBinding.edit.setVisibility((Constant.user.userType == UserType.admin) ? View.VISIBLE : View.GONE);
                holder.transactionsItemBinding.accept.setVisibility(View.GONE);
                holder.transactionsItemBinding.reject.setVisibility(View.GONE);
            }
        });
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
            transactionsItemBinding.setDate(Constant.getDate(transaction.date));
            transactionsItemBinding.executePendingBindings();
        }
    }




}
