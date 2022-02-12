package com.williamtan.cat.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedModel(
    val id: String,
    val name: String,
    val image: BreedImageModel?
)

@JsonClass(generateAdapter = true)
data class BreedImageModel(
    val url: String?
)