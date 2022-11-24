package com.setyo.made.ui.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setyo.common.data.Resource
import com.setyo.common.domain.model.MovieData
import com.setyo.common.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TopRatedViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movies = MutableSharedFlow<Resource<List<MovieData>>>()
    val movies = _movies.asSharedFlow()

    fun getTopMovies() {
        viewModelScope.launch {
            movieUseCase.getTopMovie(true).collect {
                _movies.emit(it)
            }
        }
    }

}