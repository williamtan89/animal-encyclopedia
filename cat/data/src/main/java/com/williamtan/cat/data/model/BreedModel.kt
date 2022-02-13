package com.williamtan.cat.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedModel(
    val id: String,
    val name: String,
    val image: BreedImageModel?,
    @Json(name = "reference_image_id") val referenceImageId: String?
)

@JsonClass(generateAdapter = true)
data class BreedImageModel(
    val url: String?
)