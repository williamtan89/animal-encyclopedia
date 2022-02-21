package com.williamtan.animalencyclopedia.favorite.domain.usecase

import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class IsFavoriteBreed(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(breedEntityId: String): Flow<Boolean> =
        favoriteRepository.isBreedFavorite(breedEntityId)
}