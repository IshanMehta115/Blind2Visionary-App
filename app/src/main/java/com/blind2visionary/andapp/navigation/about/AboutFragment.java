package com.blind2visionary.andapp.navigation.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blind2visionary.andapp.R;
import com.blind2visionary.andapp.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;
    private FragmentAboutBinding binding;
    private Button websiteButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);

        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        websiteButton = binding.websiteAbout;
        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.websiteUrl);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                v.getContext().startActivity(i);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}