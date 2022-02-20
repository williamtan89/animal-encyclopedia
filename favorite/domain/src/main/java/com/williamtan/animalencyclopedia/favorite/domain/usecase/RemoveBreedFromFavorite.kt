package com.williamtan.animalencyclopedia.favorite.domain.usecase

import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository

class RemoveBreedFromFavorite(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(breedEntityId: String) =
        favoriteRepository.removeFromFavorite(breedEntityId)
}