package com.williamtan.usecase

import com.williamtan.common.enumtype.AnimalType
import com.williamtan.common.model.BreedEntity
import kotlinx.coroutines.flow.Flow

interface SearchBreedsByName {
    suspend fun operator(animalType: AnimalType, name: String): Flow<List<BreedEntity>>
}