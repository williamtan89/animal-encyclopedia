package com.williamtan.domain.usecase.favorite

import com.williamtan.common.entity.BreedEntity
import com.williamtan.domain.repository.FavoriteRepository

interface AddBreedToFavorite {
    suspend operator fun invoke(breedEntity: BreedEntity)
}

internal class AddBreedToFavoriteImpl(
    private val favoriteRepository: FavoriteRepository
) : AddBreedToFavorite {
    override suspend fun invoke(breedEntity: BreedEntity) =
        favoriteRepository.addToFavorite(breedEntity)
}