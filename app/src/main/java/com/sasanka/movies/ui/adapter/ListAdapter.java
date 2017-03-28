package com.sasanka.movies.ui.adapter;

import android.content.ClipData;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.sasanka.movies.R;
import com.sasanka.movies.data.model.SearchResult;
import com.sasanka.movies.ui.activity.MainActivity;
import com.sasanka.movies.ui.adapter.holder.RetryViewHolder;
import com.sasanka.movies.ui.adapter.holder.ViewHolder;
import com.sasanka.movies.ui.adapter.holder.ViewHolderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adapter for displaying search results.
 */

public class ListAdapter extends RecyclerView.Adapter {

    public static final int ITEM_TYPE_SEARCH_RESULT = 0;
    public static final int ITEM_TYPE_RETRY = 1;

    private MainActivity activity;
    private Map<Integer, ViewHolderFactory> viewHolderFactoryMap;
    private List<SearchResult> searchResults = new ArrayList<>();
    private String retryItemInfo = "";

    public ListAdapter(MainActivity activity,
                       Map<Integer, ViewHolderFactory> viewHolderFactoryMap) {
        this.activity = activity;
        this.viewHolderFactoryMap = viewHolderFactoryMap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_SEARCH_RESULT) {
            final ViewHolder viewHolder = (ViewHolder) viewHolderFactoryMap.get(viewType).createViewHolder(parent);
            viewHolder.innerConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onListItemClicked(viewHolder.getAdapterPosition());
                }
            });
            viewHolder.innerConstraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ClipData data = ClipData.newPlainText("adapter_position", viewHolder.getAdapterPosition() + "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(new View(activity));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        view.startDragAndDrop(data, shadowBuilder, viewHolder.constraintLayout, 0);
                    } else {
                        view.startDrag(data, shadowBuilder, viewHolder.constraintLayout, 0);
                    }
                    return true;
                }
            });
            return viewHolder;
        } else {
            RetryViewHolder retryViewHolder = (RetryViewHolder) viewHolderFactoryMap.get(viewType).createViewHolder(parent);
            retryViewHolder.retryItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (retryItemInfo.equals(activity.getString(R.string.retry_info)))
                        activity.onRetryItemClicked();
                }
            });
            return retryViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_SEARCH_RESULT) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.bind(searchResults.get(position));
            activity.loadImage(getItemAt(viewHolder.getAdapterPosition()).getSmallImageUrl(),
                    R.drawable.movie_db_blue_72, viewHolder.image);
        } else {
            RetryViewHolder retryViewHolder = (RetryViewHolder) holder;
            retryViewHolder.retryInfo.setText(retryItemInfo);
            if (retryItemInfo.equals(activity.getString(R.string.retry_info))) {
                retryViewHolder.retry.setVisibility(View.VISIBLE);
            } else {
                retryViewHolder.retry.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (TextUtils.isEmpty(retryItemInfo))
            return searchResults.size();
        else
            return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 0) {
            return ITEM_TYPE_SEARCH_RESULT;
        } else {
            if (TextUtils.isEmpty(retryItemInfo))
                return ITEM_TYPE_SEARCH_RESULT;
            else
                return ITEM_TYPE_RETRY;
        }
    }

    public SearchResult getItemAt(int position) {
        if (position != 0) {
            return searchResults.get(position);
        } else {
            if (TextUtils.isEmpty(retryItemInfo))
                return searchResults.get(0);
            else
                return null;
        }
    }

    public void updateList(List<SearchResult> searchResults) {
        if (searchResults == null) {
            showRetryItem();
        } else if (searchResults.isEmpty()) {
            showEmptyItem();
        } else {
            this.searchResults.clear();
            this.searchResults.addAll(searchResults);
            retryItemInfo = "";
        }
        notifyDataSetChanged();
    }

    private void showRetryItem() {
        searchResults.clear();
        retryItemInfo = activity.getString(R.string.retry_info);
    }

    private void showEmptyItem() {
        searchResults.clear();
        retryItemInfo = activity.getString(R.string.empty_list_info);
    }
}
