package com.blind2visionary.andapp.book;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<BookItem> mData;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private BookAdapter bookAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        initBooksData();
        buildRecyclerView();

        EditText editText = findViewById(R.id.bookSearchbar);
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
        ArrayList<BookItem> filteredList = new ArrayList<BookItem>();
        for(BookItem book: mData)  {
            String clean = text.replaceAll("\\p{Punct}", "");
            String name = book.getTitle().replaceAll("\\p{Punct}", "");
            String author = book.getAuthor().replaceAll("\\p{Punct}", "");
            if(name.toLowerCase().contains(clean.toLowerCase())) {
                filteredList.add(book);
            }
            else if(author.toLowerCase().contains(clean.toLowerCase())) {
                filteredList.add(book);
            }
        }
        bookAdapter.filterList(filteredList);
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerBook);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(mData);
        recyclerView.setAdapter(bookAdapter);
    }

    private void initBooksData() {
        mData = new ArrayList<BookItem>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("books");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mData.clear();

                for (DataSnapshot row: snapshot.getChildren()) {
                    BookItem book = row.getValue(BookItem.class);
                    assert book != null;
                    mData.add(new BookItem(book.getAuthor(), book.getTitle(), book.getUrl()));
                }
                //bookAdapter.getmDataAll() = new ArrayList<>(mData);
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}