package com.williamtan.animalencyclopedia.breeddetail.mapper

import com.williamtan.animalencyclopedia.breeddetail.model.BreedDetail
import com.williamtan.common.entity.AnimalBreedEntity

class BreedDetailMapper {
    fun map(entity: AnimalBreedEntity): BreedDetail = BreedDetail(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        temperamentList = entity.temperamentList,
        energyLevel = entity.energyLevel,
        imageUrl = entity.imageUrl,
        isFavorite = entity.isFavorite,
        wikipediaUrl = entity.wikipediaUrl
    )

    fun map(entityList: List<AnimalBreedEntity>): List<BreedDetail> = entityList.map(::map)
}