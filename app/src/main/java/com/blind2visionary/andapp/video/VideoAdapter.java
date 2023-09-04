package com.blind2visionary.andapp.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.blind2visionary.andapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class VideoAdapter extends FirebaseRecyclerAdapter<VideoItem, VideoAdapter.VideoViewHolder> {

    private Context ct;
    public void set_context(Context ct){
        this.ct=ct;
    }
    public VideoAdapter(@NonNull FirebaseRecyclerOptions<VideoItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position, @NonNull VideoItem model_video) {
        holder.textView.setText(model_video.getTitle());
        Glide.with(ct)
                .load(model_video.getPic())
                .into(holder.imageView);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.textView.getContext(), ViewVideo.class);
                intent.putExtra("filename",model_video.getTitle());
                intent.putExtra("fileurl",model_video.getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.textView.getContext().startActivity(intent);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), ViewVideo.class);
                intent.putExtra("filename",model_video.getTitle());
                intent.putExtra("fileurl",model_video.getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.textView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent,false);
        return new VideoViewHolder(view);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        @SuppressLint("ClickableViewAccessibility")
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.video_name);
            imageView = itemView.findViewById(R.id.video_thumbnail);

            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event)   {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            textView.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}