package com.lelivrescolaire.testtechnique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lelivrescolaire.testtechnique.R;
import com.lelivrescolaire.testtechnique.adapters.BooksAdapter;
import com.lelivrescolaire.testtechnique.bean.Book;
import com.lelivrescolaire.testtechnique.dao.BookDao;
import com.lelivrescolaire.testtechnique.interfaces.BookAdapterListener;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity implements BookAdapterListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lib_rcv_book);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            BookDao bookDao = new BookDao(this);
            ArrayList<Book> listBook = bookDao.getAll();
            BooksAdapter adapter = new BooksAdapter(this, listBook);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onBookSelected(long idBook) {
        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra(BookActivity.ARG_BOOK_ID, idBook);
        startActivity(intent);
    }
}
