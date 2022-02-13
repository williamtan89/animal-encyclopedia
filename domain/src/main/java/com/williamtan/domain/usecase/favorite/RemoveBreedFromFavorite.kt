package com.williamtan.domain.usecase.favorite

import com.williamtan.domain.repository.FavoriteRepository

class RemoveBreedFromFavorite(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(breedEntityId: String) =
        favoriteRepository.removeFromFavorite(breedEntityId)
}