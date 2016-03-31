package com.lelivrescolaire.testtechnique.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lelivrescolaire.testtechnique.R;
import com.lelivrescolaire.testtechnique.bean.Book;
import com.lelivrescolaire.testtechnique.interfaces.BookAdapterListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 3/31/16.
 * Adapter for the list of books
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<Book> mBooks;
    private Context mContext;
    private BookAdapterListener mListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BooksAdapter(Context context, List<Book> books) {
        if (context instanceof BookAdapterListener) {
            mContext = context;
            mListener = (BookAdapterListener) context;
            mBooks = books;
        } else {
            throw new IllegalStateException("must implement BookAdapterListener");
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        /*
        set the view's size, margins, paddings and layout parameters
        TextView tv = (TextView)v.findViewById(R.id.my_text_view);
        */
        return new BookViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BookViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Book book = mBooks.get(position);

        holder.tvTitle.setText(book.getTitle());
        Picasso.with(mContext).load(book.getPathImage()).fit().into(holder.imvCover);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBookSelected(book.getId());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBooks.size();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imvCover;
        public TextView tvTitle;

        public BookViewHolder(View itemView) {
            super(itemView);
            imvCover = (ImageView) itemView.findViewById(R.id.lib_imv_cover);
            tvTitle = (TextView) itemView.findViewById(R.id.lib_tv_title);
        }
    }
}