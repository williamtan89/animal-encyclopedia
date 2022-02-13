package com.williamtan.domain.usecase.favorite

import com.williamtan.common.entity.BreedEntity
import com.williamtan.domain.repository.FavoriteRepository

class AddBreedToFavorite(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(breedEntity: BreedEntity) = favoriteRepository.addToFavorite(breedEntity)
}