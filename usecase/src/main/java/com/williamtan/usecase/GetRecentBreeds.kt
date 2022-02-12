package com.williamtan.usecase

import com.williamtan.common.enumtype.AnimalType
import com.williamtan.common.model.BreedEntity
import kotlinx.coroutines.flow.Flow

interface GetRecentBreeds {
    suspend fun operator(
        animalType: AnimalType,
        breedIdList: List<String>?
    ): Flow<List<BreedEntity>>
}