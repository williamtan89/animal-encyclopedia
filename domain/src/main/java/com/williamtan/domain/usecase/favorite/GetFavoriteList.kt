package com.williamtan.domain.usecase.favorite

import com.williamtan.common.entity.BreedEntity
import com.williamtan.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

interface GetFavoriteList {
    suspend operator fun invoke(): Flow<List<BreedEntity>>
}

internal class GetFavoriteListImpl(
    private val favoriteRepository: FavoriteRepository
) : GetFavoriteList {
    override suspend fun invoke(): Flow<List<BreedEntity>> {
        return favoriteRepository.favoriteList()
    }
}