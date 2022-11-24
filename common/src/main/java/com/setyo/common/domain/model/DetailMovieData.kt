package com.setyo.common.domain.model

data class DetailMovieData(
	val revenue: Int? = null,
	val genres: List<GenresItem?>? = null,
	val popularity: Double? = null,
	val budget: Int? = null,
	val spokenLanguages: List<SpokenLanguagesItem?>? = null,
	val productionCompanies: List<ProductionCompaniesItem?>? = null,
	val tagline: String? = null,
	val homepage: String? = null,
	val status: String? = null
)

data class GenresItem(
	val name: String? = null,
	val id: Int? = null
)

data class SpokenLanguagesItem(
	val name: String? = null,
	val iso6391: String? = null,
	val englishName: String? = null
)

data class ProductionCompaniesItem(
	val logoPath: String? = null,
	val name: String? = null,
	val id: Int? = null,
	val originCountry: String? = null
)



