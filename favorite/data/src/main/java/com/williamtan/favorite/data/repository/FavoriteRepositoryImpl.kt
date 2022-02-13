package com.williamtan.favorite.data.repository

import com.williamtan.common.entity.BreedEntity
import com.williamtan.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FavoriteRepositoryImpl : FavoriteRepository {
    // in-memory datasource
    private val favoriteList = MutableStateFlow<List<BreedEntity>>(emptyList())

    override suspend fun addToFavorite(breedEntity: BreedEntity) {
        favoriteList.update { it + breedEntity }
    }

    override suspend fun removeFromFavorite(breedEntityId: String) {
        favoriteList.update { it.filter { it.id != breedEntityId } }
    }

    override suspend fun favoriteList(): Flow<List<BreedEntity>> {
        return favoriteList
    }

    override suspend fun isBreedFavorite(breedEntityId: String): Flow<Boolean> {
        return favoriteList.map { it.find { it.id == breedEntityId } != null }
    }
}