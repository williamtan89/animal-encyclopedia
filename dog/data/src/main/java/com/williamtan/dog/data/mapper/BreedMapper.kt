package com.williamtan.dog.data.mapper

import com.williamtan.animalencyclopedia.dog.domain.model.DogBreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.dog.data.model.BreedModel

class BreedMapper {
    companion object {
        const val ALTERNATIVE_IMAGE_URL = "https://cdn2.thedogapi.com/images/%s.jpg"
    }

    fun mapTo(model: BreedModel): DogBreedEntity {
        val imageUrl = model.image?.url ?: if (!model.referenceImageId.isNullOrBlank()) {
            ALTERNATIVE_IMAGE_URL.format(model.referenceImageId)
        } else null

        return DogBreedEntity(
            id = model.id,
            name = model.name,
            imageUrl = imageUrl,
            animalType = AnimalType.Dog,
            temperamentList = model.temperament?.split(",")?.map { it.trim() } ?: emptyList(),
            wikipediaUrl = model.wikipediaUrl,
            energyLevel = model.energyLevel ?: 0,
            description = model.description ?: ""
        )
    }

    fun mapTo(modelList: List<BreedModel>): List<DogBreedEntity> = modelList.map(::mapTo)
}