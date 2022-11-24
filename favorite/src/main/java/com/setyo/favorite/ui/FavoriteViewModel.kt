package com.setyo.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setyo.common.domain.model.MovieData
import com.setyo.common.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val movieUseCase: MovieUseCase): ViewModel() {

    private val _favorites = MutableSharedFlow<List<MovieData>>()
    val favorites = _favorites.asSharedFlow()

    fun getFavoritesMovies() {
        viewModelScope.launch {
            movieUseCase.getFavoriteMovie().collect {
                _favorites.emit(it)
            }
        }
    }


}