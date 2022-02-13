package com.williamtan.domain.repository

import com.williamtan.common.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addToFavorite(breedEntity: BreedEntity)
    suspend fun removeFromFavorite(breedEntityId: String)
    suspend fun favoriteList(): Flow<List<BreedEntity>>
    suspend fun isBreedFavorite(breedEntityId: String): Flow<Boolean>
}