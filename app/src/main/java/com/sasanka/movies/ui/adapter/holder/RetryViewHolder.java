package com.sasanka.movies.ui.adapter.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sasanka.movies.R;
import com.sasanka.movies.data.model.SearchResult;

/**
 * View Holder for displaying error info with a retry option.
 */

public class RetryViewHolder extends AbstractViewHolder {

    public ImageView retry;
    public TextView retryInfo;
    public LinearLayout retryItemLayout;

    public RetryViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.retry_item, parent, false));
        retry = (ImageView) itemView.findViewById(R.id.retry);
        retryInfo = (TextView) itemView.findViewById(R.id.retry_info);
        retryItemLayout = (LinearLayout) itemView.findViewById(R.id.retry_item_layout);
    }

    @Override
    public void bind(SearchResult searchResult) {

    }
}
