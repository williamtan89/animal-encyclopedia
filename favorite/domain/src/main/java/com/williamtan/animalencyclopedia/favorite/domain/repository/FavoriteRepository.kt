package com.williamtan.animalencyclopedia.favorite.domain.repository

import com.williamtan.animalencyclopedia.favorite.domain.model.FavoriteBreedEntity
import com.williamtan.common.entity.AnimalBreedEntity
import com.williamtan.common.enumtype.AnimalType
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addToFavorite(animalBreedEntity: FavoriteBreedEntity)
    suspend fun removeFromFavorite(breedEntityId: String)
    suspend fun favoriteList(): Flow<List<FavoriteBreedEntity>>
    suspend fun isBreedFavorite(breedEntityId: String): Flow<Boolean>
}