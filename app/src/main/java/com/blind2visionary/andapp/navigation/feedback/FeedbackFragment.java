package com.blind2visionary.andapp.navigation.feedback;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blind2visionary.andapp.BuildConfig;
import com.blind2visionary.andapp.databinding.FragmentFeedbackBinding;

import java.util.List;
import java.util.Stack;

public class FeedbackFragment extends Fragment {

    private FeedbackViewModel feedbackViewModel;
    private FragmentFeedbackBinding binding;
    private Button feedbackButton, shareButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedbackViewModel =
                new ViewModelProvider(this).get(FeedbackViewModel.class);

        binding = FragmentFeedbackBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        feedbackButton = binding.feedback;
        shareButton = binding.share;
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                String subject = "Blind to Visionary App: Feedback";

                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {" b2vindia@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,"Name of Sender:"+"\n Write feedBack here:");
                startActivity(createEmailOnlyChooserIntent(intent, "Send via email"));
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                String sub = "App Link is: " + "\nhttps://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID +"\n\nPlease Download";
                intent.putExtra(Intent.EXTRA_TEXT,sub);
                startActivity(Intent.createChooser(intent,"Share using"));
            }
        });
        return root;
    }

    public Intent createEmailOnlyChooserIntent(Intent source,
                                               CharSequence chooserTitle) {
        Stack<Intent> intents = new Stack<Intent>();
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                "info@domain.com", null));
        List<ResolveInfo> activities = getActivity().getPackageManager()
                .queryIntentActivities(i, 0);

        for(ResolveInfo ri : activities) {
            Intent target = new Intent(source);
            target.setPackage(ri.activityInfo.packageName);
            intents.add(target);
        }

        if(!intents.isEmpty()) {
            Intent chooserIntent = Intent.createChooser(intents.remove(0),
                    chooserTitle);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    intents.toArray(new Parcelable[intents.size()]));

            return chooserIntent;
        } else {
            return Intent.createChooser(source, chooserTitle);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}