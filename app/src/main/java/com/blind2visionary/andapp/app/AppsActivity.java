package com.blind2visionary.andapp.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.blind2visionary.andapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppsActivity extends AppCompatActivity {
    private ArrayList<AppItem> appList;
    private RecyclerView recyclerView;
    private AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        createList();
        buildRecyclerView();

        EditText editText = findViewById(R.id.appSearchbar);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<AppItem> filteredList = new ArrayList<AppItem>();
        for(AppItem app: appList)  {
            String clean = text.replaceAll("\\p{Punct}", "");
            String name = app.getName().replaceAll("\\p{Punct}", "");
            if(name.toLowerCase().contains(clean.toLowerCase())) {
                filteredList.add(app);
            }
        }
        adapter.filterList(filteredList);
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerApp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppAdapter(appList);
        adapter.set_context(getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private void createList() {
        appList = new ArrayList<AppItem>();
        FirebaseDatabase.getInstance().getReference("apps").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appList.clear();

                for (DataSnapshot row: snapshot.getChildren()) {
                    AppItem app = row.getValue(AppItem.class);
                    assert app != null;
                    appList.add(new AppItem(app.getIcon(), app.getName(), app.getDesc(), app.getUrl()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}