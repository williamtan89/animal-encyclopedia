package com.williamtan.cat.data.mapper

import com.williamtan.common.model.BreedEntity
import com.williamtan.cat.data.model.BreedModel

interface BreedMapper {
    fun mapTo(model: BreedModel): BreedEntity
    fun mapTo(modelList: List<BreedModel>): List<BreedEntity>
}

internal class BreedMapperImpl : BreedMapper {
    override fun mapTo(model: BreedModel): BreedEntity = BreedEntity(
        id = model.id,
        name = model.name
    )

    override fun mapTo(modelList: List<BreedModel>): List<BreedEntity> = modelList.map(::mapTo)
}