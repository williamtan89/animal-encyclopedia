package com.williamtan.animalencyclopedia.breeddetail.model

data class BreedDetail(
    val id: String,
    val name: String,
    val description: String,
    val temperamentList: List<String>,
    val energyLevel: Int,
    val imageUrl: String?,
    val isFavorite: Boolean,
    val wikipediaUrl: String?
)