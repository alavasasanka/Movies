package com.sasanka.movies.ui.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import javax.inject.Inject;

/**
 * Implementation of search result view holder factory and abstracting out.
 */

public class ViewHolderFactoryImpl implements ViewHolderFactory {

    @Inject
    public ViewHolderFactoryImpl() {

    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        return new ViewHolder(parent);
    }
}
