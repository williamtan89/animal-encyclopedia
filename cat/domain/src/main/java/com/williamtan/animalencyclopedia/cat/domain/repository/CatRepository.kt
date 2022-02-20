package com.williamtan.animalencyclopedia.cat.domain.repository

import com.williamtan.animalencyclopedia.cat.domain.model.CatBreedEntity
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getBreedList(
        breedIdList: List<String>?,
        limit: Int,
        page: Int
    ): Flow<List<CatBreedEntity>>

    suspend fun searchByName(name: String): Flow<List<CatBreedEntity>>
    suspend fun getBreedById(breedId: String): Flow<CatBreedEntity?>
}