package com.sasanka.movies.ui.activity.component;

import com.sasanka.movies.ui.activity.ActivityScope;
import com.sasanka.movies.ui.activity.MainActivity;
import com.sasanka.movies.ui.activity.module.MainActivityModule;

import dagger.Subcomponent;

/**
 * Specifies ways to access objects created by its modules.
 */

@ActivityScope
@Subcomponent (
        modules = {
                MainActivityModule.class
        }
)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
