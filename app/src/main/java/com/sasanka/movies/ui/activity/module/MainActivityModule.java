package com.sasanka.movies.ui.activity.module;

import android.support.v7.widget.LinearLayoutManager;

import com.sasanka.movies.data.api.ApiManager;
import com.sasanka.movies.image.ImageDownloaderManager;
import com.sasanka.movies.ui.activity.ActivityScope;
import com.sasanka.movies.ui.activity.MainActivity;
import com.sasanka.movies.ui.activity.presenter.MainActivityPresenter;
import com.sasanka.movies.ui.adapter.ListAdapter;
import com.sasanka.movies.ui.adapter.MovieInfoAdapter;
import com.sasanka.movies.ui.adapter.holder.RetryViewHolderFactoryImpl;
import com.sasanka.movies.ui.adapter.holder.ViewHolderFactory;
import com.sasanka.movies.ui.adapter.holder.ViewHolderFactoryImpl;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

/**
 * Contains definitions for creating objects whose scope exists as long as activity exists.
 */

@Module
public class MainActivityModule {

    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    MainActivityPresenter provideMainActivityPresenter(ApiManager apiManager,
                                                       ImageDownloaderManager imageDownloaderManager) {
        return new MainActivityPresenter(activity, apiManager, imageDownloaderManager);
    }

    @Provides
    @ActivityScope
    LinearLayoutManager provideLinearLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
    }

    @Provides
    @ActivityScope
    ListAdapter provideListAdapter(Map<Integer, ViewHolderFactory> viewHolderFactoryMap) {
        return new ListAdapter(activity, viewHolderFactoryMap);
    }

    @Provides
    @ActivityScope
    MovieInfoAdapter provideMovieInfoAdapter() {
        return new MovieInfoAdapter(activity);
    }

    @Provides
    @IntoMap
    @IntKey(ListAdapter.ITEM_TYPE_SEARCH_RESULT)
    ViewHolderFactory provideViewHolder() {
        return new ViewHolderFactoryImpl();
    }

    @Provides
    @IntoMap
    @IntKey(ListAdapter.ITEM_TYPE_RETRY)
    ViewHolderFactory provideRetryViewHolder() {
        return new RetryViewHolderFactoryImpl();
    }

}
