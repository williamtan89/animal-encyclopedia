package com.williamtan.domain.usecase.favorite

import com.williamtan.common.entity.BreedEntity
import com.williamtan.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteList(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(): Flow<List<BreedEntity>> = favoriteRepository.favoriteList()
}