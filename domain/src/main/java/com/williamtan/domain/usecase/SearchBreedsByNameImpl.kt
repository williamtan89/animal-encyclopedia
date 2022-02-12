package com.williamtan.domain.usecase

import com.williamtan.common.enumtype.AnimalType
import com.williamtan.common.model.BreedEntity
import com.williamtan.domain.repository.CatRepository
import com.williamtan.usecase.SearchBreedsByName
import kotlinx.coroutines.flow.Flow

class SearchBreedsByNameImpl(
    private val catRepository: CatRepository
) : SearchBreedsByName {
    override suspend fun operator(
        animalType: AnimalType,
        name: String
    ): Flow<List<BreedEntity>> = when (animalType) {
        AnimalType.Cat -> catRepository.searchByName(name)
        else -> throw Exception("AnimalType $animalType not supported")
    }
}