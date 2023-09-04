package com.blind2visionary.andapp.navigation.contact;

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

import com.blind2visionary.andapp.R;
import com.blind2visionary.andapp.databinding.FragmentContactBinding;

import java.util.List;
import java.util.Stack;

public class ContactFragment extends Fragment {

    private ContactViewModel contactViewModel;
    private FragmentContactBinding binding;
    private Button mobileButton, emailButton, websiteButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactViewModel =
                new ViewModelProvider(this).get(ContactViewModel.class);

        binding = FragmentContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mobileButton = binding.phoneContact;
        emailButton = binding.emailContact;
        websiteButton = binding.websiteContact;
        mobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String number = getString(R.string.contactNumber);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {" b2vindia@gmail.com"});
                startActivity(createEmailOnlyChooserIntent(intent, "Send via email"));
            }
        });
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