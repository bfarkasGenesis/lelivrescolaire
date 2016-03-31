package com.lelivrescolaire.testtechnique.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lelivrescolaire.testtechnique.R;
import com.lelivrescolaire.testtechnique.bean.Page;
import com.lelivrescolaire.testtechnique.interfaces.PageAdapterListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 3/31/16.
 * Adapter for the list of pages
 */
public class PagesAdapter extends RecyclerView.Adapter<PagesAdapter.PageViewHolder> {

    private List<Page> mPages;
    private Context mContext;
    private PageAdapterListener mListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PagesAdapter(Context context, List<Page> pages) {
        if (context instanceof PageAdapterListener) {
            mContext = context;
            mListener = (PageAdapterListener) context;
            mPages = pages;
        } else {
            throw new IllegalStateException("must implement PageAdapterListener");
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        /*
        set the view's size, margins, paddings and layout parameters
        TextView tv = (TextView)v.findViewById(R.id.my_text_view);
        */
        return new PageViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PageViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Page page = mPages.get(position);

        holder.tvTitle.setText(page.getTitle());
        Picasso.with(mContext).load(page.getPathImage()).fit().into(holder.imvCover);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPageSelected(page.getId());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPages.size();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imvCover;
        public TextView tvTitle;

        public PageViewHolder(View itemView) {
            super(itemView);
            imvCover = (ImageView) itemView.findViewById(R.id.lib_imv_cover);
            tvTitle = (TextView) itemView.findViewById(R.id.lib_tv_title);
        }
    }
}