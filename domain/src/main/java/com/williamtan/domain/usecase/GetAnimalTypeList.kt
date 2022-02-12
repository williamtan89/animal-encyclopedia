package com.williamtan.domain.usecase

import com.williamtan.common.enumtype.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetAnimalTypeList {
    suspend operator fun invoke(): Flow<List<AnimalType>>
}

internal class GetAnimalTypeListImpl : GetAnimalTypeList {
    override suspend operator fun invoke(): Flow<List<AnimalType>> {
        return flow {
            emit(
                listOf(AnimalType.Cat)
            )
        }
    }
}