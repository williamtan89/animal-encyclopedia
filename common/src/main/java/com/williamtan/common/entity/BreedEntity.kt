package com.williamtan.common.entity

import com.williamtan.common.enumtype.AnimalType

abstract class AnimalBreedEntity(
    open val id: String,
    open val name: String,
    open val imageUrl: String?,
    open val animalType: AnimalType,
    open val temperamentList: List<String>,
    open val wikipediaUrl: String?,
    open val energyLevel: Int,
    open val description: String,
    open val isFavorite: Boolean,
)