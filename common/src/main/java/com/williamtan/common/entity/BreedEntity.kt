package com.williamtan.common.entity

import com.williamtan.common.enumtype.AnimalType

data class BreedEntity(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val animalType: AnimalType,
    val temperament: List<String>,
    val wikipediaUrl: String,
    val energyLevel: Int
)