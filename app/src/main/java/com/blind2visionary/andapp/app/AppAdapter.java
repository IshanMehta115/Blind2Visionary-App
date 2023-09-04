package com.blind2visionary.andapp.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.blind2visionary.andapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {
    private ArrayList<AppItem> appItemArrayList;
    private Context ct;

    public void set_context(Context ct){
        this.ct=ct;
    }

    @NonNull
    @NotNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item, parent, false);
        AppViewHolder avh = new AppViewHolder(v);
        return avh;
    }

    public AppAdapter(@NonNull ArrayList<AppItem> appList)   {
        appItemArrayList = appList;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AppViewHolder holder, int position) {
        AppItem model = appItemArrayList.get(position);
        if("".equals(model.getIcon())) {
            holder.appImage.setImageResource(R.drawable.ic_outline_image_24);
        }
        else    {
            Glide.with(ct).load(model.getIcon()).into(holder.appImage);
        }
        holder.appName.setText(model.getName());
        holder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder info = new AlertDialog.Builder(v.getContext());
                info.setTitle("About " + model.getName());
                info.setMessage(model.getDesc());
                info.create().show();
            }
        });
        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = model.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appItemArrayList.size();
    }

    public void filterList(ArrayList<AppItem> filteredList) {
        appItemArrayList = filteredList;
        notifyDataSetChanged();
    }

    public static class AppViewHolder extends RecyclerView.ViewHolder   {
        public ImageView appImage;
        public TextView appName;
        public Button downloadButton, detailsButton;

        public AppViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appImage = itemView.findViewById(R.id.appListIcon);
            appName = itemView.findViewById(R.id.appListName);
            downloadButton = itemView.findViewById(R.id.appListDownload);
            detailsButton = itemView.findViewById(R.id.appListDetails);
        }
    }
}
