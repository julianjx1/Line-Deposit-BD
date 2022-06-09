package com.line_deposit.bd.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.line_deposit.bd.R;
import com.line_deposit.bd.model.VideoModel;
import com.line_deposit.bd.view.adapter.VideoListAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        MaterialButton withdrawButton = view.findViewById(R.id.btn_withdraw);
        MaterialButton depositButton = view.findViewById(R.id.btn_deposit);
        MaterialButton showTransactionButton = view.findViewById(R.id.btn_show_transactions);




        withdrawButton.setOnClickListener(v -> loadFragment(new WithdrawFragment()));
        depositButton.setOnClickListener(v -> loadFragment(new DepositFragment()));
        showTransactionButton.setOnClickListener(v -> loadFragment(new ShowTransactionsFragment()));


//        RecyclerView recyclerView = view.findViewById(R.id.embedded_list);
//        List<VideoModel> videoModelList = new ArrayList<>();
//        videoModelList.add(new VideoModel("কুয়েতে বিভিন্ন মার্কেট থেকে সরিয়ে ফেলা হচ্ছে ভারতীয় পণ্য","https://i.ytimg.com/vi/LYwvMCQHPOY/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLAj3bW6ODqvAazFkkL1pokgjLu5JA", "LYwvMCQHPOY"));
//        videoModelList.add(new VideoModel("এটি একটি স্বজনতোষী বাজেট","https://i.ytimg.com/vi/bt1jGtrqufM/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLD2IK_8LjczOsaFTWjng_OwRXNQQQ", "bt1jGtrqufM"));
//        videoModelList.add(new VideoModel("অর্ধেক চেহারাতেও অনবদ্য শাহরুখ ","https://i.ytimg.com/vi/Ogj_R_GiFuk/hqdefault.jpg?sqp=-oaymwEcCOADEI4CSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBis3r7G0oeO7h59dtd_SGOWe_syw", "Ogj_R_GiFuk"));
//        videoModelList.add(new VideoModel("কুয়েতে বিভিন্ন মার্কেট থেকে সরিয়ে ফেলা হচ্ছে ভারতীয় পণ্য","https://i.ytimg.com/vi/LYwvMCQHPOY/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLAj3bW6ODqvAazFkkL1pokgjLu5JA", "LYwvMCQHPOY"));
//        videoModelList.add(new VideoModel("এটি একটি স্বজনতোষী বাজেট","https://i.ytimg.com/vi/bt1jGtrqufM/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLD2IK_8LjczOsaFTWjng_OwRXNQQQ", "bt1jGtrqufM"));
//        videoModelList.add(new VideoModel("অর্ধেক চেহারাতেও অনবদ্য শাহরুখ ","https://i.ytimg.com/vi/Ogj_R_GiFuk/hqdefault.jpg?sqp=-oaymwEcCOADEI4CSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBis3r7G0oeO7h59dtd_SGOWe_syw", "Ogj_R_GiFuk"));
//        videoModelList.add(new VideoModel("কুয়েতে বিভিন্ন মার্কেট থেকে সরিয়ে ফেলা হচ্ছে ভারতীয় পণ্য","https://i.ytimg.com/vi/LYwvMCQHPOY/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLAj3bW6ODqvAazFkkL1pokgjLu5JA", "LYwvMCQHPOY"));
//        videoModelList.add(new VideoModel("এটি একটি স্বজনতোষী বাজেট","https://i.ytimg.com/vi/bt1jGtrqufM/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLD2IK_8LjczOsaFTWjng_OwRXNQQQ", "bt1jGtrqufM"));
//        videoModelList.add(new VideoModel("অর্ধেক চেহারাতেও অনবদ্য শাহরুখ ","https://i.ytimg.com/vi/Ogj_R_GiFuk/hqdefault.jpg?sqp=-oaymwEcCOADEI4CSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBis3r7G0oeO7h59dtd_SGOWe_syw", "Ogj_R_GiFuk"));
//        videoModelList.add(new VideoModel("কুয়েতে বিভিন্ন মার্কেট থেকে সরিয়ে ফেলা হচ্ছে ভারতীয় পণ্য","https://i.ytimg.com/vi/LYwvMCQHPOY/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLAj3bW6ODqvAazFkkL1pokgjLu5JA", "LYwvMCQHPOY"));
//        videoModelList.add(new VideoModel("এটি একটি স্বজনতোষী বাজেট","https://i.ytimg.com/vi/bt1jGtrqufM/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLD2IK_8LjczOsaFTWjng_OwRXNQQQ", "bt1jGtrqufM"));
//        videoModelList.add(new VideoModel("অর্ধেক চেহারাতেও অনবদ্য শাহরুখ ","https://i.ytimg.com/vi/Ogj_R_GiFuk/hqdefault.jpg?sqp=-oaymwEcCOADEI4CSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBis3r7G0oeO7h59dtd_SGOWe_syw", "Ogj_R_GiFuk"));

//        VideoListAdapter adapter = new VideoListAdapter(requireContext(), videoModelList, new VideoListAdapter.RecyclerviewOnClickListener() {
//            @Override
//            public void showVideo(VideoModel videoModel) {
//                Bundle bundle = new Bundle();
//                bundle.putString("referal_link",videoModel.getVideoReference());
//               // Navigation.findNavController(view).navigate(R.id.to_navigation_video,bundle);
//                VideoFragment videoFragment = new VideoFragment();
//                videoFragment.setArguments(bundle);
//                loadFragment(videoFragment);
//
//
//
//            }
//        });
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        recyclerView.setAdapter(adapter);
        return  view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment).addToBackStack("back");
        fragmentTransaction.commit();
    }
}