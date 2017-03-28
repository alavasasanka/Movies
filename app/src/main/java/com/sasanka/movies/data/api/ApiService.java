package com.sasanka.movies.data.api;

import com.sasanka.movies.data.api.response.Genre;
import com.sasanka.movies.data.api.response.SearchResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This class contains all the end points.
 */

public interface ApiService {

    @GET("search/multi")
    Observable<SearchResponse> getSearchResults(@Query("query") String query);

    @GET("genre/movie/list")
    Observable<List<Genre>> getMovieGenres();

    @GET("genre/tv/list")
    Observable<List<Genre>> getTvGenres();

}
