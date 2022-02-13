package com.williamtan.domain.usecase

import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.repository.CatRepository
import com.williamtan.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface GetBreedById {
    suspend operator fun invoke(animalType: AnimalType, breedId: String): Flow<BreedEntity?>
}

class GetBreedByIdImpl(
    private val catRepository: CatRepository,
    private val favoriteRepository: FavoriteRepository
) : GetBreedById {
    override suspend fun invoke(
        animalType: AnimalType,
        breedId: String
    ): Flow<BreedEntity?> = when (animalType) {
        AnimalType.Cat -> {
            catRepository.getCatBreedById(breedId)
                .combine(favoriteRepository.isBreedFavorite(breedId)) { breed, isFavorite ->
                    breed?.copy(
                        isFavorite = isFavorite
                    )
                }
        }
        else -> throw Exception("AnimalType $animalType not supported")
    }
}