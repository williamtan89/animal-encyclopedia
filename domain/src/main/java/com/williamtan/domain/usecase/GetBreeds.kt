package com.williamtan.domain.usecase

import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow

interface GetBreeds {
    suspend operator fun invoke(animalType: AnimalType, page: Int): Flow<List<BreedEntity>>
}

class GetBreedsImpl(
    private val catRepository: CatRepository
) : GetBreeds {
    override suspend fun invoke(
        animalType: AnimalType,
        page: Int
    ): Flow<List<BreedEntity>> = when (animalType) {
        AnimalType.Cat -> catRepository.getCatBreedList(
            null,
            10,
            page
        )
        else -> throw Exception("AnimalType $animalType not supported")
    }

}