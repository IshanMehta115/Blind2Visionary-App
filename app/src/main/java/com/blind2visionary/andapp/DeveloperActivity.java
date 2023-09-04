package com.blind2visionary.andapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DeveloperActivity extends AppCompatActivity {
    FloatingActionButton link1, link2, link3, link4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        link1 = findViewById(R.id.profileLink1);
        link2 = findViewById(R.id.profileLink2);
        link3 = findViewById(R.id.profileLink3);
        link4 = findViewById(R.id.profileLink4);

        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.profileLink1);
                gotoLink(url, v);
            }
        });
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.profileLink2);
                gotoLink(url, v);
            }
        });
        link3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.profileLink3);
                gotoLink(url, v);
            }
        });
        link4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.profileLink4);
                gotoLink(url, v);
            }
        });
    }

    private void gotoLink(String url, View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        v.getContext().startActivity(i);
    }
}