package com.blind2visionary.andapp.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.blind2visionary.andapp.R;


public class ViewVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        try{
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_video);

        String fileurl = getIntent().getStringExtra("fileurl");
        Uri uri = Uri.parse(fileurl);

        VideoView videoView= (VideoView)findViewById(R.id.video_player);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

    }

}