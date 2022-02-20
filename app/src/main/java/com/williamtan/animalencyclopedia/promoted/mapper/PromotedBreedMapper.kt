package com.williamtan.animalencyclopedia.promoted.mapper

import com.williamtan.animalencyclopedia.promoted.model.PromotedBreed
import com.williamtan.common.entity.AnimalBreedEntity

class PromotedBreedMapper {
    fun map(entity: AnimalBreedEntity): PromotedBreed = PromotedBreed(
        id = entity.id,
        animalType = entity.animalType,
        name = entity.name,
        imageUrl = entity.imageUrl
    )

    fun map(entityList: List<AnimalBreedEntity>): List<PromotedBreed> = entityList.map(::map)
}