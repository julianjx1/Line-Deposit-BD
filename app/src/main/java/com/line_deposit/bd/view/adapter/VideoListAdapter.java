package com.line_deposit.bd.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.line_deposit.bd.R;
import com.line_deposit.bd.model.VideoModel;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder>{

    private List<VideoModel> videoModelList;
    private Context context;

    private RecyclerviewOnClickListener onClickListener;
    public interface RecyclerviewOnClickListener{
        void showVideo(VideoModel videoModel);
    }

    public VideoListAdapter(Context context, List<VideoModel> videoModelList, RecyclerviewOnClickListener onClickListener){
        this.context = context;
        this.videoModelList = videoModelList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public VideoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.video_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListAdapter.ViewHolder holder, int position) {
        VideoModel videoModel = videoModelList.get(position);
        holder.textView.setText(videoModel.getTitle());
        Glide.with(context).load(videoModel.getImage()).into(holder.imageView);
        holder.relativeLayout.setOnClickListener(v -> {
            onClickListener.showVideo(videoModel);


        });
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;
        public LinearLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
