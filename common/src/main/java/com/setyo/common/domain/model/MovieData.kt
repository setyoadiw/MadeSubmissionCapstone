package com.setyo.common.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieData (
    var id: Int,
    var originalTitle: String,
    var overview: String,
    var posterPath: String,
    var releaseDate: String,
    var voteAverage: Double,
    var isFavorite: Boolean = false
): Parcelable