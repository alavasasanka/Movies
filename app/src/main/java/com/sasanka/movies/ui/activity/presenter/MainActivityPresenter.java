package com.sasanka.movies.ui.activity.presenter;

import android.widget.ImageView;

import com.sasanka.movies.data.api.ApiManager;
import com.sasanka.movies.data.model.SearchResult;
import com.sasanka.movies.image.ImageDownloaderManager;
import com.sasanka.movies.ui.activity.MainActivity;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * This class gets things done whenever requested by its associated activity by relying upon managers.
 * Acts as bridge between activity and managers.
 */

public class MainActivityPresenter {

    private ApiManager apiManager;
    private Disposable disposable;
    private MainActivity activity;
    private ImageDownloaderManager imageDownloaderManager;

    public MainActivityPresenter(MainActivity activity,
                                 ApiManager apiManager,
                                 ImageDownloaderManager imageDownloaderManager) {
        this.activity = activity;
        this.apiManager = apiManager;
        this.imageDownloaderManager = imageDownloaderManager;
    }

    public void onTextInput(String query) {
        if (disposable != null)
            disposable.dispose();
        disposable = apiManager.fetchSearchResults(activity, query)
                .subscribe(new Consumer<List<SearchResult>>() {
                    @Override
                    public void accept(List<SearchResult> searchResults) throws Exception {
                        activity.onSearchResultsFetched(searchResults, null);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        activity.onSearchResultsFetched(null, throwable);
                    }
                });
    }

    public void loadImage(String url, int errorDrawable, ImageView imageView) {
        imageDownloaderManager.loadImage(activity, url, errorDrawable, imageView);
    }

    public void onStop() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

}
