package com.line_deposit.bd.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.line_deposit.bd.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {
    private YouTubePlayerView youTubePlayerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video, container, false);

                youTubePlayerView = view.findViewById(R.id.youtube_player);

        requireActivity().getLifecycle().addObserver(youTubePlayerView);
//        youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
//            @Override
//            public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
//                if (getArguments() != null) {
//                    String videoId = getArguments().getString("referal_link");
//                    youTubePlayer.loadVideo(videoId, 0);
////                    YouTubePlayerUtils.loadOrCueVideo(
////                            youTubePlayer,
////                            getLifecycle(),
////                            videoId,
////                            0
////                    );
//                }
//            }
//        });

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if (getArguments() != null) {
                    String videoId = getArguments().getString("referal_link");
                    youTubePlayer.loadVideo(videoId, 0);
//                    YouTubePlayerUtils.loadOrCueVideo(
//                            youTubePlayer,
//                            getLifecycle(),
//                            videoId,
//                            0
//                    );
                }

            }
        });
        return  view;
    }
}