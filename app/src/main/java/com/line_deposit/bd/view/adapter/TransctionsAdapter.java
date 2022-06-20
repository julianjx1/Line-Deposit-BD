package com.line_deposit.bd.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.line_deposit.bd.R;
import com.line_deposit.bd.model.Transaction;
import com.line_deposit.bd.utilites.Constant;

import java.util.ArrayList;

public class TransctionsAdapter extends RecyclerView.Adapter<TransctionsAdapter.ViewHolder> {


    private ArrayList<Transaction> transactions;
    private Context context;

    public TransctionsAdapter(ArrayList<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

   public void updateList(ArrayList<Transaction> list){
        transactions.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TransctionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.transactions_item, parent, false);
        TransctionsAdapter.ViewHolder viewHolder = new TransctionsAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransctionsAdapter.ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.mobileNumber.setText(transaction.mobileNumber);
        holder.transactionId.setText(transaction.transactionId);
        holder.amount.setText(Integer.toString(transaction.amount));
        holder.transactionType.setText(transaction.transactionType);
        holder.date.setText(Constant.getDate(transaction.date));
        holder.paymentType.setText(transaction.paymentType.toString());
        holder.transactionProcess.setText(transaction.transactionProcess.toString());
        switch (transaction.transactionProcess){
            case Rejected:
                holder.transactionProcess.setTextColor(context.getColor(R.color.red));
                break;
            case Processing:
                holder.transactionProcess.setTextColor(context.getColor(R.color.theme_color));
                break;
            case Completed:
                holder.transactionProcess.setTextColor(context.getColor(R.color.green));
                break;
        }

        switch (transaction.paymentType){
            case Withdraw:
                holder.paymentType.setTextColor(context.getColor(R.color.red));
                break;
            case Deposit:
                holder.paymentType.setTextColor(context.getColor(R.color.green));
                break;
        }

    }


    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public  TextView mobileNumber, transactionId, amount, transactionType, paymentType, transactionProcess, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mobileNumber = itemView.findViewById(R.id.mobile_number);
            transactionId = itemView.findViewById(R.id.transaction_id);
            amount = itemView.findViewById(R.id.amount);
            transactionType = itemView.findViewById(R.id.transaction_type);
            paymentType = itemView.findViewById(R.id.payment_type);
            transactionProcess = itemView.findViewById(R.id.transaction_process);
            date = itemView.findViewById(R.id.date);
        }
    }
}
