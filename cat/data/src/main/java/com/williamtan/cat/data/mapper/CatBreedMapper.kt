package com.williamtan.cat.data.mapper

import com.williamtan.animalencyclopedia.cat.domain.model.CatBreedEntity
import com.williamtan.cat.data.model.BreedModel
import com.williamtan.common.enumtype.AnimalType

class CatBreedMapper {
    companion object {
        const val ALTERNATIVE_IMAGE_URL = "https://cdn2.thecatapi.com/images/%s.jpg"
    }

    fun mapTo(model: BreedModel): CatBreedEntity {
        val imageUrl = model.image?.url ?: if (!model.referenceImageId.isNullOrBlank()) {
            ALTERNATIVE_IMAGE_URL.format(model.referenceImageId)
        } else null

        return CatBreedEntity(
            id = model.id,
            name = model.name,
            imageUrl = imageUrl,
            animalType = AnimalType.Cat,
            temperamentList = model.temperament?.split(",")?.map { it.trim() } ?: emptyList(),
            wikipediaUrl = model.wikipediaUrl,
            energyLevel = model.energyLevel ?: 0,
            description = model.description ?: ""
        )
    }

    fun mapTo(modelList: List<BreedModel>): List<CatBreedEntity> = modelList.map(::mapTo)
}