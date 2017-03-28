package com.sasanka.movies.ui.activity;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sasanka.movies.MyApplication;
import com.sasanka.movies.R;
import com.sasanka.movies.data.model.SearchResult;
import com.sasanka.movies.ui.activity.module.MainActivityModule;
import com.sasanka.movies.ui.activity.presenter.MainActivityPresenter;
import com.sasanka.movies.ui.adapter.ListAdapter;
import com.sasanka.movies.ui.adapter.MovieInfoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements
        View.OnDragListener, TextWatcher, View.OnClickListener {

    @Inject
    MainActivityPresenter presenter;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    ListAdapter listAdapter;
    @Inject
    MovieInfoAdapter movieInfoAdapter;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText search;
    private ConstraintLayout dropContainerLayout;
    private ImageView clear;
    private View movieInfoView;

    private PublishSubject<String> subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        clear = (ImageView) findViewById(R.id.clear);
        dropContainerLayout = (ConstraintLayout) findViewById(R.id.drop_container_layout);
        movieInfoView = findViewById(R.id.movie_info_view);
        search = (EditText) findViewById(R.id.search_query);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapter);
        listAdapter.updateList(new ArrayList<SearchResult>());
        showLoading(false);

        search.addTextChangedListener(this);
        dropContainerLayout.setOnDragListener(this);
        movieInfoView.setOnDragListener(this);
        clear.setOnClickListener(this);

        subject = PublishSubject.create();
        subject.debounce(100, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String query) throws Exception {
                        if (TextUtils.isEmpty(query)) {
                            clear.setVisibility(View.INVISIBLE);
                            listAdapter.updateList(new ArrayList<SearchResult>());
                            showLoading(false);
                            return;
                        }
                        clear.setVisibility(View.VISIBLE);
                        showLoading(true);
                        presenter.onTextInput(query);
                    }
                });
    }

    private void init() {
        MyApplication
                .get(this)
                .getAppComponent()
                .getActivityComponent(new MainActivityModule(this))
                .inject(this);
    }

    public void onListItemClicked(int position) {
        movieInfoAdapter.display(listAdapter.getItemAt(position));
    }

    public void onRetryItemClicked() {
        showLoading(true);
        presenter.onTextInput(search.getText().toString());
    }

    public void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void loadImage(String url, int errorDrawable, ImageView imageView) {
        presenter.loadImage(url, errorDrawable, imageView);
    }

    private int yDisplacement, initialTopMargin, initialBottomMargin;

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        View sourceView = (View) dragEvent.getLocalState();
        ViewGroup.MarginLayoutParams lParams = (ViewGroup.MarginLayoutParams) sourceView.getLayoutParams();
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                if (view.getId() == R.id.drop_container_layout) {
                    initialTopMargin = lParams.topMargin;
                    initialBottomMargin = lParams.bottomMargin;
                    yDisplacement = (int) dragEvent.getY() - lParams.topMargin;
                }
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                if (view.getId() == R.id.drop_container_layout || view.getId() == R.id.movie_info_view) {
                    if (((int) dragEvent.getY() - yDisplacement) >= 0) {
                        lParams.topMargin = (int) dragEvent.getY() - yDisplacement;
                    } else {
                        lParams.bottomMargin = -((int) dragEvent.getY() - yDisplacement);
                    }
                    sourceView.setLayoutParams(lParams);
                }
                break;
            case DragEvent.ACTION_DROP:
                if (view.getId() == R.id.movie_info_view) {
                    ClipData.Item item = dragEvent.getClipData().getItemAt(0);
                    movieInfoAdapter.display(listAdapter.getItemAt(Integer.valueOf(item.getText().toString())));
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                if (view.getId() == R.id.drop_container_layout) {
                    sourceView = (View) dragEvent.getLocalState();
                    lParams = (ViewGroup.MarginLayoutParams) sourceView.getLayoutParams();
                    lParams.topMargin = initialTopMargin;
                    lParams.bottomMargin = initialBottomMargin;
                    sourceView.setLayoutParams(lParams);
                    sourceView.setVisibility(View.VISIBLE);
                }
                break;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        subject.onNext(s.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void onSearchResultsFetched(List<SearchResult> searchResults, Throwable throwable) {
        if (TextUtils.isEmpty(search.getText())) {
            listAdapter.updateList(new ArrayList<SearchResult>());
            showLoading(false);
            return;
        }
        if (throwable == null) {
            listAdapter.updateList(searchResults);
        } else {
            listAdapter.updateList(null);
        }
        showLoading(false);
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        search.setText("");
    }
}
