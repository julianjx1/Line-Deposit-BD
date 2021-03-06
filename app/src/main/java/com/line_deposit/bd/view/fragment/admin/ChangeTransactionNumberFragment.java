package com.line_deposit.bd.view.fragment.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.line_deposit.bd.R;
import com.line_deposit.bd.databinding.FragmentChangeTransactionNumberBinding;
import com.line_deposit.bd.utilites.Constant;
import com.line_deposit.bd.view.fragment.user.TransactionObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class ChangeTransactionNumberFragment extends Fragment implements TransactionObserver {

    FragmentChangeTransactionNumberBinding binding;
    String processName = "";
    String mobileNumber = "";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_transaction_number,container,false);

        updateProcessListView();

        binding.btnSave.setOnClickListener(view -> {
             mobileNumber = Objects.requireNonNull(binding.etMobileNumber.getText()).toString();
            if(binding.labelProcessName.getVisibility() == View.VISIBLE){
                 processName = Objects.requireNonNull(binding.etProcessName.getText()).toString();

                if(processName.isEmpty())
                {
                    Toast.makeText(requireContext(), "Process name must be given", Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                 processName = binding.transactionProcess.getText().toString();
            }
            if(mobileNumber.isEmpty())
            {
                Toast.makeText(requireContext(), "Mobile number must be given", Toast.LENGTH_SHORT).show();
                return;
            }
            Constant.network.addTransactionProcess(processName, mobileNumber);


        });
        return binding.getRoot();
    }

    private void updateProcessListView(){
        Set<String> processSet =  Constant.transactionProcessMap.keySet();
        List<String> processList = new ArrayList<>(processSet);
        processList.add("Add new transaction process");
        int processLength = processList.size();
        binding.labelProcessName.setVisibility((processLength == 1) ? View.VISIBLE : View.GONE);
        Constant.network.transactionObserver = this;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.drop_down_item,processList);
        binding.transactionProcess.setAdapter(adapter);
        binding.transactionProcess.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.labelProcessName.setVisibility((processLength == i+1) ? View.VISIBLE : View.GONE);
            binding.etMobileNumber.setText((processLength == 1) ? "" : Constant.transactionProcessMap.get(processList.get(i)));
        });
    }

    @Override
    public void onTransactionUpdate(Boolean success, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        if (success){
            Constant.transactionProcessMap.put(processName,mobileNumber);
            binding.etMobileNumber.setText("");
            binding.etProcessName.setText("");
            binding.transactionProcess.setText("");
            updateProcessListView();
        }
    }
}