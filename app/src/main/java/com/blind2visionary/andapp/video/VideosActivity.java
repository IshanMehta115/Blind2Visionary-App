package com.blind2visionary.andapp.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blind2visionary.andapp.R;

public class VideosActivity extends AppCompatActivity {

    private Button tag1,tag2,tag3,tag4,tag5;
    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        tag3 = findViewById(R.id.tag3);
        tag4 = findViewById(R.id.tag4);
        tag5 = findViewById(R.id.tag5);

        tag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag=tag1.getText().toString();
                next();
            }
        });

        tag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag=tag2.getText().toString();
                next();
            }
        });

        tag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag=tag3.getText().toString();
                next();
            }
        });

        tag4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag=tag4.getText().toString();
                next();
            }
        });

        tag5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag=tag5.getText().toString();
                next();
            }
        });
    }

    private void next() {
        Intent intent = new Intent(getBaseContext(), VideoDisplayActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
    }
}