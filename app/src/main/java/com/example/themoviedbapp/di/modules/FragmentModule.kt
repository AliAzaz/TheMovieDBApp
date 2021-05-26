package com.example.themoviedbapp.di.modules

import com.example.themoviedbapp.ui.fragment.MovieDetailFragment
import com.example.themoviedbapp.ui.fragment.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Ali Azaz
 */
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindProductListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun bindProductDetailFragment(): MovieDetailFragment
}