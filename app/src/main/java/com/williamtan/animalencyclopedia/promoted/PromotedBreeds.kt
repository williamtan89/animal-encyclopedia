package com.williamtan.animalencyclopedia.promoted

import com.williamtan.animalencyclopedia.adapter.PromotedBreedsAdapter.PromotedBreedsItemState
import com.williamtan.common.enumtype.AnimalType

data class PromotedBreeds(
    val animalType: AnimalType,
    val promotedBreedsItemState: PromotedBreedsItemState
)