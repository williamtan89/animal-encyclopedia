package com.williamtan.animalencyclopedia.promoted.model

import com.williamtan.common.enumtype.AnimalType

data class PromotedBreed(
    val id: String,
    val animalType: AnimalType,
    val name: String,
    val imageUrl: String?
)