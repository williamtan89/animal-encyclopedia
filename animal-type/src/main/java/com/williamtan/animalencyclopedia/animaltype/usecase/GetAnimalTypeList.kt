package com.williamtan.animalencyclopedia.animaltype.usecase

import com.williamtan.common.enumtype.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Returns allowed animal type to display in the app, in real world application this can be
 * based on a feature flag mechanism
 */
class GetAnimalTypeList {
    suspend operator fun invoke(): Flow<List<AnimalType>> = flow {
        emit(
            listOf(AnimalType.Cat, AnimalType.Dog)
        )
    }
}