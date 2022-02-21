package com.williamtan.animalencyclopedia.dog.domain.model

import com.williamtan.common.entity.AnimalBreedEntity
import com.williamtan.common.enumtype.AnimalType

data class DogBreedEntity(
    override val id: String,
    override val name: String,
    override val imageUrl: String?,
    override val animalType: AnimalType,
    override val temperamentList: List<String>,
    override val wikipediaUrl: String?,
    override val energyLevel: Int,
    override val description: String,
    override var isFavorite: Boolean = false
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