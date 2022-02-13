package com.williamtan.domain.usecase

import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow

interface GetBreedById {
    suspend operator fun invoke(animalType: AnimalType, breedId: String): Flow<BreedEntity?>
}

class GetBreedByIdImpl(
    private val catRepository: CatRepository
) : GetBreedById {
    override suspend fun invoke(
        animalType: AnimalType,
        breedId: String
    ): Flow<BreedEntity?> = when (animalType) {
        AnimalType.Cat -> catRepository.getCatBreedById(breedId)
        else -> throw Exception("AnimalType $animalType not supported")
    }
}