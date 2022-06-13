package com.line_deposit.test.bd.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.line_deposit.test.bd.R;
import com.line_deposit.test.bd.model.Transaction;

import java.util.List;

public class TransctionsAdapter extends RecyclerView.Adapter<TransctionsAdapter.ViewHolder> {


    private List<Transaction> transactions;
    private Context context;

    public TransctionsAdapter(List<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
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
        holder.mobileNumber.setText(transaction.getMobileNumber());
        holder.transactionId.setText(transaction.getTransactionId());
        holder.amount.setText(Integer.toString(transaction.getAmount()));
        holder.transactionType.setText(transaction.getTransactionType());
        holder.date.setText(transaction.getDate());
        holder.paymentType.setText(transaction.getPaymentType().toString());
        holder.transactionProcess.setText(transaction.getTransactionProcess().toString());
        switch (transaction.getTransactionProcess()){
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

        switch (transaction.getPaymentType()){
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
