package com.williamtan.animalencyclopedia.breeddetail.model

import com.williamtan.common.enumtype.AnimalType

data class BreedDetail(
    val id: String,
    val animalType: AnimalType,
    val name: String,
    val description: String,
    val temperamentList: List<String>,
    val energyLevel: Int,
    val imageUrl: String?,
    val isFavorite: Boolean,
    val wikipediaUrl: String?
)