package com.williamtan.domain.usecase.animaltype

import com.williamtan.common.enumtype.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAnimalTypeList {
    suspend operator fun invoke(): Flow<List<AnimalType>> = flow {
        emit(
            listOf(AnimalType.Cat)
        )
    }
}