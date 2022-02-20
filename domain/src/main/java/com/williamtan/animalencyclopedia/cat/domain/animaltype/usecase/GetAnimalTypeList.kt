package com.williamtan.animalencyclopedia.cat.domain.animaltype.usecase

import com.williamtan.common.enumtype.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAnimalTypeList {
    suspend operator fun invoke(): Flow<List<AnimalType>> = flow {
        emit(
            listOf(AnimalType.Cat, AnimalType.Dog)
        )
    }
}