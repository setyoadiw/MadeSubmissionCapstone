package com.setyo.common.domain.model



data class ReviewData(
	val authorDetails: AuthorDetails? = null,
	val author: String? = null,
	val id: String? = null,
	val content: String? = null,
	val url: String? = null
)

data class AuthorDetails(
	val avatarPath: String? = null,
	val name: String? = null,
	val rating: Double? = null,
	val username: String? = null
)

