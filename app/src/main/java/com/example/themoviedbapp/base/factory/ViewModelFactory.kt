package com.example.themoviedbapp.base.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedbapp.base.repository.GeneralRepository
import com.example.themoviedbapp.base.viewmodel.MovieViewModel
import com.example.themoviedbapp.base.viewmodel.usecases.MovieSearchUseCase
import com.example.themoviedbapp.base.viewmodel.usecases.MovieListUseCase
import com.example.themoviedbapp.base.viewmodel.usecases.MovieUseCase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author AliAzazAlam on 5/16/2021.
 */
@Singleton
@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val repository: GeneralRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(
                MovieListUseCase(repository),
                MovieSearchUseCase(repository),
                MovieUseCase(repository)
            ) as T
            else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
        }
    }

}