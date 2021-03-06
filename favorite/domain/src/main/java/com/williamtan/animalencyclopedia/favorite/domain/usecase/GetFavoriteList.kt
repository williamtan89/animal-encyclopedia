package com.williamtan.animalencyclopedia.favorite.domain.usecase

import com.williamtan.animalencyclopedia.favorite.domain.model.FavoriteBreedEntity
import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteList(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(): Flow<List<FavoriteBreedEntity>> =
        favoriteRepository.favoriteList()
}