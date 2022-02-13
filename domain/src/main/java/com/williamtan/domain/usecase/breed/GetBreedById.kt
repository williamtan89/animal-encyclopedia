package com.williamtan.domain.usecase.breed

import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.repository.CatRepository
import com.williamtan.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetBreedById(
    private val catRepository: CatRepository,
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(
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