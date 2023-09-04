package com.blind2visionary.andapp.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.blind2visionary.andapp.R;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ArticlesActivity extends AppCompatActivity {
    private FirebaseRecyclerOptions<ArticleItem> itemList;
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        createList();
        buildRecyclerView();
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

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerArticle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticleAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

    private void createList() {
        FirebaseRecyclerOptions<ArticleItem> options = new FirebaseRecyclerOptions.Builder<ArticleItem>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("articles"), ArticleItem.class)
                .build();
        itemList = options;
    }
}