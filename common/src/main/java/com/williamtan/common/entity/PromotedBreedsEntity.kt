package com.williamtan.common.entity

import com.williamtan.common.enumtype.AnimalType

data class PromotedBreedsEntity(
    val animalType: AnimalType,
    val promotedBreeds: List<BreedEntity>
)