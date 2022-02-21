package com.williamtan.animalencyclopedia.favorite.domain.usecase

import com.williamtan.animalencyclopedia.favorite.domain.model.FavoriteBreedEntity
import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository

class AddBreedToFavorite(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(favoriteBreedEntity: FavoriteBreedEntity) =
        favoriteRepository.addToFavorite(favoriteBreedEntity)
}