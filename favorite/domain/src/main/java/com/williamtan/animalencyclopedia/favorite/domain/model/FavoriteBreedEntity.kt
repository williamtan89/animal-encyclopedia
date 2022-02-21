package com.williamtan.animalencyclopedia.favorite.domain.model

import com.williamtan.common.enumtype.AnimalType

data class FavoriteBreedEntity(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val animalType: AnimalType,
    val temperamentList: List<String>,
    val wikipediaUrl: String?,
    val energyLevel: Int,
    val description: String
)