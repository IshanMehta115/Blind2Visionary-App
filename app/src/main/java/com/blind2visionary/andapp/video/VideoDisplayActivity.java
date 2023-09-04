package com.blind2visionary.andapp.video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.blind2visionary.andapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class VideoDisplayActivity extends AppCompatActivity {

    String tag;
    private VideoAdapter adapter;
    private RecyclerView rview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);

        tag = getIntent().getStringExtra("tag");
        tag = tag.toUpperCase();

        rview = findViewById(R.id.recyclerVideo);

        System.out.println("-------------------------------------------------VIDEOS");
        System.out.println("-------------------------------------------------tag = "+tag);
        if(FirebaseDatabase.getInstance().getReference().child("videos").child(tag)==null){
            System.out.println("---------------------------------------------object null");
        }
        else{
            System.out.println("---------------------------------------------object not null");

        }
        FirebaseRecyclerOptions<VideoItem> options = new FirebaseRecyclerOptions.Builder<VideoItem>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("videos").child(tag), VideoItem.class)
                .build();
        rview.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VideoAdapter(options);
        adapter.set_context(getApplicationContext());
        rview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}