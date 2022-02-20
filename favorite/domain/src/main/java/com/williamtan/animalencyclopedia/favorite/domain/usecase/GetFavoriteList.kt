package com.williamtan.animalencyclopedia.favorite.domain.usecase

import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
import com.williamtan.common.entity.AnimalBreedEntity
import kotlinx.coroutines.flow.Flow

class GetFavoriteList(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(): Flow<List<AnimalBreedEntity>> = favoriteRepository.favoriteList()
}