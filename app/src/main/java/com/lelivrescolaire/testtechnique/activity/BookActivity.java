package com.lelivrescolaire.testtechnique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lelivrescolaire.testtechnique.R;
import com.lelivrescolaire.testtechnique.adapters.PagesAdapter;
import com.lelivrescolaire.testtechnique.bean.Page;
import com.lelivrescolaire.testtechnique.dao.PageDao;
import com.lelivrescolaire.testtechnique.interfaces.PageAdapterListener;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity implements PageAdapterListener {

    public final static String ARG_BOOK_ID = "ARG_BOOK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long idBook = extras.getLong(ARG_BOOK_ID);
            PageDao pageDao = new PageDao(this);
            ArrayList<Page> listPage = pageDao.getAllByBook(idBook);


            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.book_rcv_page);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            PagesAdapter adapter = new PagesAdapter(this, listPage);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onPageSelected(long idPage) {
        Intent intent = new Intent(this, PageActivity.class);
        intent.putExtra(PageActivity.ARG_PAGE_ID, idPage);
        startActivity(intent);
    }
}
