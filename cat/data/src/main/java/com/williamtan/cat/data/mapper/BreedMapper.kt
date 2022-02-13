package com.williamtan.cat.data.mapper

import com.williamtan.cat.data.model.BreedModel
import com.williamtan.common.entity.BreedEntity

interface BreedMapper {
    fun mapTo(model: BreedModel): BreedEntity
    fun mapTo(modelList: List<BreedModel>): List<BreedEntity>
}

internal class BreedMapperImpl : BreedMapper {
    override fun mapTo(model: BreedModel): BreedEntity {
        val imageUrl = model.image?.url ?: if (!model.referenceImageId.isNullOrBlank()) {
            "https://cdn2.thecatapi.com/images/${model.referenceImageId}.jpg"
        } else null

        return BreedEntity(
            id = model.id,
            name = model.name,
            imageUrl = imageUrl
        )
    }

    override fun mapTo(modelList: List<BreedModel>): List<BreedEntity> =
        modelList.map(::mapTo)
}