package com.blind2visionary.andapp.navigation.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blind2visionary.andapp.app.AppsActivity;
import com.blind2visionary.andapp.article.ArticlesActivity;
import com.blind2visionary.andapp.book.BooksActivity;
import com.blind2visionary.andapp.R;
import com.blind2visionary.andapp.video.VideosActivity;
import com.blind2visionary.andapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Button booksB, appsB, articlesB, videosB;
    private ImageView booksI, appsI, articlesI, videosI;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Buttons and icons

        booksB = root.findViewById(R.id.booksButton);
        booksI = root.findViewById(R.id.booksIcon);
        appsB = root.findViewById(R.id.appsButton);
        appsI = root.findViewById(R.id.appsIcon);
        articlesB = root.findViewById(R.id.articlesButton);
        articlesI = root.findViewById(R.id.articlesIcon);
        videosB = root.findViewById(R.id.videosButton);
        videosI = root.findViewById(R.id.videosIcon);

        booksB.setOnClickListener(this::openResource);
        booksI.setOnClickListener(this::openResource);
        appsB.setOnClickListener(this::openResource);
        appsI.setOnClickListener(this::openResource);
        articlesB.setOnClickListener(this::openResource);
        articlesI.setOnClickListener(this::openResource);
        videosB.setOnClickListener(this::openResource);
        videosI.setOnClickListener(this::openResource);

        return root;
    }

    public void openResource(View view)  {
        int id = view.getId();
        Intent intent;
        if(id==R.id.booksButton || id==R.id.booksIcon) {
            intent = new Intent(this.getContext(), BooksActivity.class);
//          intent.putExtra("key", value);
            startActivity(intent);
        }
        else if(id==R.id.appsButton || id==R.id.appsIcon) {
            intent = new Intent(this.getContext(), AppsActivity.class);
//                intent.putExtra("key", value);
            startActivity(intent);
        }
        else if(id==R.id.articlesButton || id==R.id.articlesIcon) {
            intent = new Intent(this.getContext(), ArticlesActivity.class);
//          intent.putExtra("key", value);
            startActivity(intent);
        }
        else if(id==R.id.videosButton || id==R.id.videosIcon) {
            intent = new Intent(this.getContext(), VideosActivity.class);
//          intent.putExtra("key", value);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}