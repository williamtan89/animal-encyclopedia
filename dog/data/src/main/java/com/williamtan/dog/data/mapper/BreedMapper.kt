package com.williamtan.dog.data.mapper

import com.williamtan.dog.data.model.BreedModel
import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType

interface BreedMapper {
    fun mapTo(model: BreedModel): BreedEntity
    fun mapTo(modelList: List<BreedModel>): List<BreedEntity>
}

internal class BreedMapperImpl(
    private val animalType: AnimalType
) : BreedMapper {
    override fun mapTo(model: BreedModel): BreedEntity {
        val imageUrl = model.image?.url ?: if (!model.referenceImageId.isNullOrBlank()) {
            "https://cdn2.thedogapi.com/images/${model.referenceImageId}.jpg"
        } else null

        return BreedEntity(
            id = model.id,
            name = model.name,
            imageUrl = imageUrl,
            animalType = animalType,
            temperamentList = model.temperament.split(",").map { it.trim() },
            wikipediaUrl = model.wikipediaUrl,
            energyLevel = model.energyLevel ?: 0,
            description = model.description ?: ""
        )
    }

    override fun mapTo(modelList: List<BreedModel>): List<BreedEntity> =
        modelList.map(::mapTo)
}