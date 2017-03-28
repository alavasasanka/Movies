package com.sasanka.movies.ui.adapter.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sasanka.movies.R;
import com.sasanka.movies.data.model.SearchResult;
import com.squareup.picasso.Picasso;

/**
 * View Holder for displaying search result of a movie or a tv series.
 */

public class ViewHolder extends AbstractViewHolder {

    public TextView title, mediaType, year;
    public ConstraintLayout constraintLayout, innerConstraintLayout;
    public CardView cardView;
    public ImageView image;

    public ViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
        title = (TextView) itemView.findViewById(R.id.title);
        mediaType = (TextView) itemView.findViewById(R.id.media_type);
        year = (TextView) itemView.findViewById(R.id.year);
        image = (ImageView) itemView.findViewById(R.id.image);
        constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.list_item_parent);
        innerConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.inner_constraint_layout);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
    }

    @Override
    public void bind(SearchResult searchResult) {
        title.setText(searchResult.getTitle());
        mediaType.setText(searchResult.getMediaType());
        year.setText(searchResult.getReleaseYear());
        Picasso.with(image.getContext())
                .load(searchResult.getSmallImageUrl())
                .error(R.drawable.movie_db_blue_72)
                .into(image);
    }
}
