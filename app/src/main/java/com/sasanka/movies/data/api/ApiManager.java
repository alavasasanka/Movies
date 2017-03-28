package com.sasanka.movies.data.api;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.sasanka.movies.data.api.response.Genre;
import com.sasanka.movies.data.api.response.Result;
import com.sasanka.movies.data.api.response.SearchResponse;
import com.sasanka.movies.data.model.SearchResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

/**
 * This class handles all actions corresponding to api calls.
 * This acts as a wrapper around a rest service which interacts with a http client
 * to get corresponding results which are exposed in this class.
 */

public class ApiManager {

    private ApiService apiService;
    OkHttpClient client;

    public ApiManager(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<List<SearchResult>> fetchSearchResults(final Context context, String query) {
        return apiService.getSearchResults(query)
                .map(new Function<SearchResponse, List<SearchResult>>() {
                    @Override
                    public List<SearchResult> apply(SearchResponse searchResponse) throws Exception {
                        List<SearchResult> searchResults = new ArrayList<SearchResult>();
                        List<Result> responseResults = searchResponse.getResults();
                        JSONObject genresJson = readFromAssets(context, "genres.json");
                        for (Result responseResult : responseResults) {
                            if (responseResult.getMediaType().equals("movie")
                                    || responseResult.getMediaType().equals("tv")) {
                                SearchResult searchResult = new SearchResult();
                                searchResult.setId(responseResult.getId());
                                searchResult.setOriginalTitle(responseResult.getOriginalTitle());
                                searchResult.setTitle(responseResult.getTitle());
                                if (responseResult.getGenreIds() == null || responseResult.getGenreIds().isEmpty()) {
                                    searchResult.setGenres("N/A");
                                } else {
                                    try {
                                        String genres = "";
                                        String key = responseResult.getMediaType().equals("tv") ? "tv_genres" : "movie_genres";
                                        JSONObject jsonObject = (JSONObject) genresJson.get(key);
                                        for (Integer genreId : responseResult.getGenreIds()) {
                                            genres += (jsonObject.get(String.valueOf(genreId)) + ", ");
                                        }
                                        genres = genres.substring(0, genres.length() - 2);
                                        searchResult.setGenres(genres);
                                    } catch (Exception e) {
                                        searchResult.setGenres("N/A");
                                    }
                                }
                                if (responseResult.getMediaType().equals("tv")) {
                                    searchResult.setOriginalTitle(responseResult.getOriginalName());
                                    searchResult.setTitle(responseResult.getName());
                                    searchResult.setMediaType("TV Series");
                                } else {
                                    searchResult.setMediaType("Movie");
                                }
                                searchResult.setOverview(responseResult.getOverview());
                                searchResult.setSmallImageUrl("https://image.tmdb.org/t/p/w185"
                                        + responseResult.getPosterPath());
                                searchResult.setLargeImageUrl("https://image.tmdb.org/t/p/w342"
                                        + responseResult.getPosterPath());
                                try {
                                    Locale locale = new Locale(responseResult.getOriginalLanguage());
                                    searchResult.setOriginalLanguage(locale.getDisplayLanguage());
                                } catch (Exception e) {
                                    searchResult.setOriginalLanguage(responseResult.getOriginalLanguage());
                                }
                                if (TextUtils.isEmpty(responseResult.getReleaseDate())
                                        || responseResult.getReleaseDate().length() < 4) {
                                    searchResult.setReleaseYear("");
                                } else {
                                    searchResult.setReleaseYear(responseResult.getReleaseDate().substring(0, 4));
                                }
                                searchResult.setReleaseDate(responseResult.getReleaseDate());
                                searchResult.setPopularity(responseResult.getPopularity());
                                searchResult.setVoteAverage(responseResult.getVoteAverage());
                                searchResult.setVoteCount(responseResult.getVoteCount());
                                searchResults.add(searchResult);
                            }
                        }
                        return searchResults;
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        throwable.printStackTrace();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void load(Context context, String url, int errorDrawable, ImageView target) {
        Picasso.with(context).load(url).error(errorDrawable).into(target);
    }

    public Observable<List<Map<Integer, String>>> fetchGenres() {
        return Observable.zip(apiService.getMovieGenres(), apiService.getTvGenres(),
                new BiFunction<List<Genre>, List<Genre>, List<Map<Integer, String>>>() {
                    @Override
                    public List<Map<Integer, String>> apply(List<Genre> movieGenres, List<Genre> tvGenres) throws Exception {
                        List<Map<Integer, String>> mapList = new ArrayList<>();
                        HashMap<Integer, String> movieGenreMap = new HashMap<>();
                        for (Genre genre : movieGenres) {
                            movieGenreMap.put(genre.getId(), genre.getName());
                        }
                        mapList.add(movieGenreMap);
                        HashMap<Integer, String> tvGenreMap = new HashMap<>();
                        for (Genre genre : movieGenres) {
                            tvGenreMap.put(genre.getId(), genre.getName());
                        }
                        mapList.add(tvGenreMap);
                        return mapList;
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public JSONObject readFromAssets(Context context, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonString = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
