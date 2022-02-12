package com.williamtan.common.entity

import com.williamtan.common.enumtype.AnimalType

data class AnimalTypeWithPromotedBreedsEntity(
    val animalType: AnimalType,
    val promotedBreeds: List<BreedEntity>
)