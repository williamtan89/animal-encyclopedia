package com.williamtan.favorite.data.repository

import com.williamtan.animalencyclopedia.favorite.domain.model.FavoriteBreedEntity
import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
import com.williamtan.favorite.data.model.FavoriteBreedModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class FavoriteRepositoryImpl : FavoriteRepository {
    // in-memory datasource
    private val favoriteList = MutableStateFlow<List<FavoriteBreedModel>>(emptyList())

    override suspend fun addToFavorite(entity: FavoriteBreedEntity) {
        favoriteList.update {
            it + FavoriteBreedModel(
                id = entity.id,
                name = entity.name,
                imageUrl = entity.imageUrl,
                animalType = entity.animalType,
                temperamentList = entity.temperamentList,
                wikipediaUrl = entity.wikipediaUrl,
                energyLevel = entity.energyLevel,
                description = entity.description
            )
        }
    }

    override suspend fun removeFromFavorite(breedEntityId: String) {
        favoriteList.update { it.filter { it.id != breedEntityId } }
    }

    override suspend fun favoriteList(): Flow<List<FavoriteBreedEntity>> {
        return favoriteList.map {
            it.map {
                FavoriteBreedEntity(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    animalType = it.animalType,
                    temperamentList = it.temperamentList,
                    wikipediaUrl = it.wikipediaUrl,
                    energyLevel = it.energyLevel,
                    description = it.description
                )
            }
        }
    }

    override suspend fun isBreedFavorite(breedEntityId: String): Flow<Boolean> {
        return favoriteList.map { it.find { it.id == breedEntityId } != null }
    }
}