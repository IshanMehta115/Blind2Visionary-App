package com.blind2visionary.andapp.book;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blind2visionary.andapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<BookItem> mData;

    public BookAdapter(ArrayList<BookItem> mData) { this.mData = mData; }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);
        BookViewHolder bvh = new BookViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BookAdapter.BookViewHolder holder, int position) {

        BookItem currentItem = mData.get(position);
        holder.bookName.setText(currentItem.getTitle());
        holder.bookAuthor.setText(currentItem.getAuthor());
        holder.bookDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = currentItem.getUrl();
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                String fileName = URLUtil.guessFileName(url, null, null);

                request.setTitle(fileName);

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                DownloadManager downloadManager = (DownloadManager) view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
                Toast.makeText(view.getContext(), "Downloading...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void filterList(ArrayList<BookItem> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder   {
        public TextView bookName, bookAuthor;
        public FloatingActionButton bookDownload;

        public BookViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.item_book_title);
            bookAuthor = itemView.findViewById(R.id.item_book_author);
            bookDownload = itemView.findViewById(R.id.bookDownload);
        }
    }
}