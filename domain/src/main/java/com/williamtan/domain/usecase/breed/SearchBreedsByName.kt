package com.williamtan.domain.usecase.breed

import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class SearchBreedsByName(
    private val catRepository: CatRepository
) {
    suspend operator fun invoke(
        animalType: AnimalType,
        name: String
    ): Flow<List<BreedEntity>> = when (animalType) {
        AnimalType.Cat -> catRepository.searchByName(name)

        else -> throw Exception("AnimalType $animalType not supported")
    }
}