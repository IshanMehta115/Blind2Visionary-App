package com.blind2visionary.andapp.article;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blind2visionary.andapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ArticleAdapter extends FirebaseRecyclerAdapter<ArticleItem, ArticleAdapter.ArticleViewHolder> {

    private final FirebaseRecyclerOptions<ArticleItem> articleList;

    public ArticleAdapter(@NonNull FirebaseRecyclerOptions<ArticleItem> options) {
        super(options);
        articleList =  options;
    }

    @Override
    protected void onBindViewHolder(@NonNull ArticleViewHolder holder, int position, @NonNull ArticleItem model) {
        holder.textView.setText(model.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("------------------------------------"+model.getUrl());
                Intent intent = new Intent(holder.textView.getContext(), ViewPdf.class);
                intent.putExtra("filename",model.getName());
                intent.putExtra("fileurl",model.getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.textView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item,parent,false);
        return new ArticleViewHolder(view);
    }
    public class ArticleViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.pdf_name);
        }
    }
}
