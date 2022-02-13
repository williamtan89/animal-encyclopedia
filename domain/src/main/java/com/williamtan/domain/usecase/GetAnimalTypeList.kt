package com.williamtan.domain.usecase

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