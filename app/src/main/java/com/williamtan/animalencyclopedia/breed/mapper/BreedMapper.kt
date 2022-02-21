package com.williamtan.animalencyclopedia.breed.mapper

import com.williamtan.animalencyclopedia.breed.model.Breed
import com.williamtan.animalencyclopedia.favorite.domain.model.FavoriteBreedEntity
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

    fun map(entity: FavoriteBreedEntity): Breed = Breed(
        id = entity.id,
        animalType = entity.animalType,
        name = entity.name,
        description = entity.description,
        temperamentList = entity.temperamentList,
        energyLevel = entity.energyLevel,
        imageUrl = entity.imageUrl,
        wikipediaUrl = entity.wikipediaUrl
    )

    fun mapFavoriteBreedEntityList(entityList: List<FavoriteBreedEntity>): List<Breed> = entityList.map(::map)
}