package com.williamtan.animalencyclopedia.breeddetail.mapper

import com.williamtan.animalencyclopedia.breeddetail.model.BreedDetail
import com.williamtan.animalencyclopedia.favorite.domain.model.FavoriteBreedEntity
import com.williamtan.common.entity.AnimalBreedEntity

class BreedDetailMapper {
    fun map(entity: AnimalBreedEntity, isFavorite: Boolean = false): BreedDetail = BreedDetail(
        id = entity.id,
        animalType = entity.animalType,
        name = entity.name,
        description = entity.description,
        temperamentList = entity.temperamentList,
        energyLevel = entity.energyLevel,
        imageUrl = entity.imageUrl,
        isFavorite = isFavorite,
        wikipediaUrl = entity.wikipediaUrl
    )

    fun map(entity: FavoriteBreedEntity): BreedDetail = BreedDetail(
        id = entity.id,
        animalType = entity.animalType,
        name = entity.name,
        description = entity.description,
        temperamentList = entity.temperamentList,
        energyLevel = entity.energyLevel,
        imageUrl = entity.imageUrl,
        isFavorite = true,
        wikipediaUrl = entity.wikipediaUrl
    )
}