package com.williamtan.animalencyclopedia.cat.domain.model

import com.williamtan.common.entity.AnimalBreedEntity
import com.williamtan.common.enumtype.AnimalType

data class CatBreedEntity(
    override val id: String,
    override val name: String,
    override val imageUrl: String?,
    override val animalType: AnimalType,
    override val temperamentList: List<String>,
    override val wikipediaUrl: String?,
    override val energyLevel: Int,
    override val description: String,
    override val isFavorite: Boolean = false
) : AnimalBreedEntity(
    id,
    name,
    imageUrl,
    animalType,
    temperamentList,
    wikipediaUrl,
    energyLevel,
    description,
    isFavorite
)