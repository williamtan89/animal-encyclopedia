package com.williamtan.animalencyclopedia.cat.domain.breed.usecase

import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedById
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedById
import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
import com.williamtan.common.entity.AnimalBreedEntity
import com.williamtan.common.enumtype.AnimalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetBreedById(
    private val getCatBreedById: GetCatBreedById,
    private val getDogBreedById: GetDogBreedById,
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(
        animalType: AnimalType,
        breedId: String
    ): Flow<AnimalBreedEntity?> = when (animalType) {
        AnimalType.Cat -> {
            getCatBreedById(breedId)
                .combine(favoriteRepository.isBreedFavorite(breedId)) { breed, isFavorite ->
                    breed?.copy(
                        isFavorite = isFavorite
                    )
                }
        }
        AnimalType.Dog -> {
            getDogBreedById(breedId)
                .combine(favoriteRepository.isBreedFavorite(breedId)) { breed, isFavorite ->
                    breed?.copy(
                        isFavorite = isFavorite
                    )
                }
        }

        else -> throw Exception("AnimalType $animalType not supported")
    }
}