package com.sasanka.movies.ui.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import javax.inject.Inject;

/**
 * Implementation of retry view holder factory and abstracting out.
 */

public class RetryViewHolderFactoryImpl implements ViewHolderFactory {

    @Inject
    public RetryViewHolderFactoryImpl() {

    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
        return new RetryViewHolder(parent);
    }
}
