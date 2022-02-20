package com.williamtan.favorite.data.repository

import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
import com.williamtan.common.entity.AnimalBreedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class FavoriteRepositoryImpl : FavoriteRepository {
    // in-memory datasource
    private val favoriteList = MutableStateFlow<List<AnimalBreedEntity>>(emptyList())

    override suspend fun addToFavorite(breedEntity: AnimalBreedEntity) {
        favoriteList.update { it + breedEntity }
    }

    override suspend fun removeFromFavorite(breedEntityId: String) {
        favoriteList.update { it.filter { it.id != breedEntityId } }
    }

    override suspend fun favoriteList(): Flow<List<AnimalBreedEntity>> {
        return favoriteList
    }

    override suspend fun isBreedFavorite(breedEntityId: String): Flow<Boolean> {
        return favoriteList.map { it.find { it.id == breedEntityId } != null }
    }
}