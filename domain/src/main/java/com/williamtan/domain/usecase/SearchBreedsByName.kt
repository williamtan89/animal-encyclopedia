package com.williamtan.domain.usecase

import com.williamtan.common.enumtype.AnimalType
import com.williamtan.common.entity.BreedEntity
import com.williamtan.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow

interface SearchBreedsByName {
    suspend operator fun invoke(animalType: AnimalType, name: String): Flow<List<BreedEntity>>
}

internal class SearchBreedsByNameImpl(
    private val catRepository: CatRepository
) : SearchBreedsByName {
    override suspend operator fun invoke(
        animalType: AnimalType,
        name: String
    ): Flow<List<BreedEntity>> = when (animalType) {
        AnimalType.Cat -> catRepository.searchByName(name)
        else -> throw Exception("AnimalType $animalType not supported")
    }
}