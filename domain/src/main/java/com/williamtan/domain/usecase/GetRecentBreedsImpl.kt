package com.williamtan.domain.usecase

import com.williamtan.common.enumtype.AnimalType
import com.williamtan.common.model.BreedEntity
import com.williamtan.domain.repository.CatRepository
import com.williamtan.usecase.GetRecentBreeds
import kotlinx.coroutines.flow.Flow

class GetRecentBreedsImpl(
    private val catRepository: CatRepository
) : GetRecentBreeds {
    override suspend fun operator(
        animalType: AnimalType,
        breedIdList: List<String>?
    ): Flow<List<BreedEntity>> = when (animalType) {
        AnimalType.Cat -> catRepository.getCatBreedList(breedIdList)
        else -> throw Exception("AnimalType $animalType not supported")
    }
}