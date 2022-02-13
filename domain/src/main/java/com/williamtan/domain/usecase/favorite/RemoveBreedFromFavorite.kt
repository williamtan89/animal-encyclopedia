package com.williamtan.domain.usecase.favorite

import com.williamtan.domain.repository.FavoriteRepository

interface RemoveBreedFromFavorite {
    suspend operator fun invoke(breedEntityId: String)
}

internal class RemoveBreedFromFavoriteImpl(
    private val favoriteRepository: FavoriteRepository
) : RemoveBreedFromFavorite {
    override suspend fun invoke(breedEntityId: String) =
        favoriteRepository.removeFromFavorite(breedEntityId)
}