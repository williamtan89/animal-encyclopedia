package com.williamtan.animalencyclopedia.breed.mapper

import com.williamtan.animalencyclopedia.breed.model.Breed
import com.williamtan.common.entity.AnimalBreedEntity

class BreedMapper {
    fun map(entity: AnimalBreedEntity): Breed = Breed(
        id = entity.id,
        animalType = entity.animalType,
        name = entity.name,
        description = entity.description,
        temperamentList = entity.temperamentList,
        energyLevel = entity.energyLevel,
        imageUrl = entity.imageUrl,
        wikipediaUrl = entity.wikipediaUrl
    )

    fun map(entityList: List<AnimalBreedEntity>): List<Breed> = entityList.map(::map)
}