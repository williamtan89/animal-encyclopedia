package com.williamtan.animalencyclopedia.breed.model

import com.williamtan.common.enumtype.AnimalType

data class Breed(
    val id: String,
    val animalType: AnimalType,
    val name: String,
    val description: String,
    val temperamentList: List<String>,
    val energyLevel: Int,
    val imageUrl: String?,
    val wikipediaUrl: String?
)