package com.sasanka.movies.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sasanka.movies.R;
import com.sasanka.movies.data.model.SearchResult;
import com.sasanka.movies.ui.activity.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

/**
 * Adapter for displaying movie, tv series info.
 */

public class MovieInfoAdapter {

    private MainActivity activity;
    private ImageView moviePoster;
    private TextView drag_drop_info, title, originalLanguage, genre, releasedDate, popularity, tmdbRating, votes, overview;
    private LinearLayout movieInfoLayout;

    @Inject
    public MovieInfoAdapter(MainActivity activity) {
        this.activity = activity;
        drag_drop_info = (TextView) activity.findViewById(R.id.drag_drop_info);
        moviePoster = (ImageView) activity.findViewById(R.id.movie_poster);
        title = (TextView) activity.findViewById(R.id.title);
        originalLanguage = (TextView) activity.findViewById(R.id.original_language);
        genre = (TextView) activity.findViewById(R.id.genre);
        releasedDate = (TextView) activity.findViewById(R.id.released_data);
        popularity = (TextView) activity.findViewById(R.id.popularity);
        tmdbRating = (TextView) activity.findViewById(R.id.tmdb_rating);
        votes = (TextView) activity.findViewById(R.id.votes);
        overview = (TextView) activity.findViewById(R.id.overview);
        movieInfoLayout = (LinearLayout) activity.findViewById(R.id.movie_info_layout);
    }

    public void display(SearchResult searchResult) {
        if (searchResult != null) {
            drag_drop_info.setVisibility(View.INVISIBLE);
            activity.loadImage(searchResult.getLargeImageUrl(), R.drawable.movie_db_blue_96, moviePoster);
            title.setText(searchResult.getTitle());
            originalLanguage.setText(searchResult.getOriginalLanguage());
            genre.setText(searchResult.getGenres());
            if (TextUtils.isEmpty(searchResult.getReleaseDate())) {
                releasedDate.setText("N/A");
            } else {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = simpleDateFormat.parse(searchResult.getReleaseDate());
                    simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                    releasedDate.setText(simpleDateFormat.format(date));
                } catch (ParseException e) {
                    releasedDate.setText("N/A");
                }
            }
            popularity.setText("" + searchResult.getPopularity());
            tmdbRating.setText("" + searchResult.getVoteAverage());
            votes.setText("" + searchResult.getVoteCount());
            if (!TextUtils.isEmpty(searchResult.getOverview()))
                overview.setText(searchResult.getOverview());
            else
                overview.setText("N/A");
            movieInfoLayout.setVisibility(View.VISIBLE);
            drag_drop_info.setVisibility(View.INVISIBLE);
        } else {
            drag_drop_info.setVisibility(View.VISIBLE);
            movieInfoLayout.setVisibility(View.INVISIBLE);
        }
    }

}
