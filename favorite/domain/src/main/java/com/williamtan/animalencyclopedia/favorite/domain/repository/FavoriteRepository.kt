package com.williamtan.animalencyclopedia.favorite.domain.repository

import com.williamtan.common.entity.AnimalBreedEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addToFavorite(breedEntity: AnimalBreedEntity)
    suspend fun removeFromFavorite(breedEntityId: String)
    suspend fun favoriteList(): Flow<List<AnimalBreedEntity>>
    suspend fun isBreedFavorite(breedEntityId: String): Flow<Boolean>
}