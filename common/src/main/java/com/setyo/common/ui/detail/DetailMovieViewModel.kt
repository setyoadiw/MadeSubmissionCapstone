package com.setyo.common.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.setyo.common.data.Resource
import com.setyo.common.domain.model.DetailMovieData
import com.setyo.common.domain.model.MovieData
import com.setyo.common.domain.model.ReviewData
import com.setyo.common.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite =_isFavorite.asStateFlow()

    private val _detailMovie = MutableSharedFlow<Resource<DetailMovieData>>()
    val detailMovie = _detailMovie.asSharedFlow()

    private val _reviews = MutableSharedFlow<Resource<List<ReviewData>>>()
    val reviews = _reviews.asSharedFlow()

    fun getDetailMovies(id:String) {
        viewModelScope.launch {
            movieUseCase.getDetailMovie(id).collect {
                _detailMovie.emit(it)
            }
        }
    }


    fun getReviews(id:String) {
        viewModelScope.launch {
            movieUseCase.getReview(id).collect {
                _reviews.emit(it)
            }
        }
    }

    fun isFavoriteMovie(id:Int) {
        viewModelScope.launch {
            movieUseCase.getFavoriteMovieById(id).collect {
                _isFavorite.emit(it.isNotEmpty())
            }
        }
    }

    fun setFavorite(movieData: MovieData, newState: Boolean){
        viewModelScope.launch {
            movieUseCase.setMovieFavorite(movieData, newState)
        }

    }
}