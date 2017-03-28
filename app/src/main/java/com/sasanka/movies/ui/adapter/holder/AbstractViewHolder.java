package com.sasanka.movies.ui.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sasanka.movies.data.model.SearchResult;

/**
 * Abstract class for view holder
 */

public abstract class AbstractViewHolder extends RecyclerView.ViewHolder {

    public AbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(SearchResult searchResult);
}
