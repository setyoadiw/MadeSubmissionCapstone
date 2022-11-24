package com.setyo.common.utils

import com.setyo.common.data.source.local.entity.FavoriteEntity
import com.setyo.common.data.source.local.entity.MovieEntity
import com.setyo.common.data.source.remote.response.DetailMovieResponse
import com.setyo.common.data.source.remote.response.ResultsPopularMovie
import com.setyo.common.data.source.remote.response.ResultsReview
import com.setyo.common.data.source.remote.response.ResultsSearchMovie
import com.setyo.common.domain.model.*

object DataMapper {

    fun mapMovieResponsesToEntities(input: List<ResultsPopularMovie>): List<MovieEntity> =
        input.map {
            MovieEntity(
                id = it.id!!,
                originalTitle = it.originalTitle ?: it.originalName!!,
                overview = it.overview!!,
                posterPath = it.posterPath!!,
                releaseDate = it.releaseDate ?: "",
                voteAverage = it.voteAverage!!
            )
        }

    fun mapFavoriteMovieDomainToEntities(input: MovieData): FavoriteEntity =
        FavoriteEntity(
            id = input.id,
            originalTitle = input.originalTitle,
            overview = input.overview,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage
        )



    fun mapMovieResponsesToDomain(input: List<ResultsPopularMovie>): List<MovieData> =
        input.map {
            MovieData(
                id = it.id!!,
                originalTitle = it.originalTitle ?: it.originalName!!,
                overview = it.overview!!,
                posterPath = it.posterPath!!,
                releaseDate = it.releaseDate ?: "",
                voteAverage = it.voteAverage!!
            )
        }

    fun mapSearchMovieResponsesToDomain(input: List<ResultsSearchMovie>): List<MovieData> =
        input.map {
            MovieData(
                id = it.id!!,
                originalTitle = it.originalTitle ?: "",
                overview = it.overview?: "",
                posterPath = it.posterPath ?: "",
                releaseDate = it.releaseDate ?: "",
                voteAverage = it.voteAverage ?: 0.0
            )
        }


    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<MovieData> =
        input.map {
            MovieData(
                id = it.id,
                originalTitle = it.originalTitle,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage
            )
        }

    fun mapFavoriteMovieEntitiesToDomain(input: List<FavoriteEntity>): List<MovieData> =
        input.map {
            MovieData(
                id = it.id,
                originalTitle = it.originalTitle,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage
            )
        }

    fun mapDetailResponseToDomain(response: DetailMovieResponse): DetailMovieData =
        DetailMovieData(
            revenue = response.revenue,
            genres = response.genres?.map { GenresItem(name = it?.name, id = it?.id) },
            popularity = response.popularity,
            budget = response.budget,
            spokenLanguages = response.spokenLanguages?.map {
                SpokenLanguagesItem(
                    name = it?.name,
                    iso6391 = it?.iso6391,
                    englishName = it?.englishName
                )
            },
            productionCompanies = response.productionCompanies?.map {
                ProductionCompaniesItem(
                    logoPath = it?.logoPath,
                    name = it?.name,
                    id = it?.id,
                    originCountry = it?.originCountry
                )
            },
            tagline = response.tagline,
            homepage = response.homepage,
            status = response.status
        )

    fun mapReviewResponseToDomain(response: List<ResultsReview>): List<ReviewData> =
        response.map {
            ReviewData(
                authorDetails = AuthorDetails(
                    avatarPath = it.authorDetails?.avatarPath,
                    name = it.authorDetails?.name,
                    rating = it.authorDetails?.rating,
                    username = it.authorDetails?.username
                ),
                author = it.author,
                id = it.id,
                content = it.content,
                url = it.url
            )
        }

}