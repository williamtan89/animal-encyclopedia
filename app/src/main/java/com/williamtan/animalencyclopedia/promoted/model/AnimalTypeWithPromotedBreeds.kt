package com.williamtan.animalencyclopedia.promoted.model

import com.williamtan.animalencyclopedia.promoted.adapter.AnimalTypeWithPromotedBreedsAdapter
import com.williamtan.common.enumtype.AnimalType

data class AnimalTypeWithPromotedBreeds(
    val animalType: AnimalType,
    val promotedBreedsItemState: AnimalTypeWithPromotedBreedsAdapter.PromotedBreedsItemState
)