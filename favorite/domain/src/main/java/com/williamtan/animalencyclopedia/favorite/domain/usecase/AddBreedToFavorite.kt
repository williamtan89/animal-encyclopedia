package com.williamtan.animalencyclopedia.favorite.domain.usecase

import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
import com.williamtan.common.entity.AnimalBreedEntity

class AddBreedToFavorite(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(breedEntity: AnimalBreedEntity) =
        favoriteRepository.addToFavorite(breedEntity)
}