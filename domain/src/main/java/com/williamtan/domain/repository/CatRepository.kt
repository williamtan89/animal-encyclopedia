package com.williamtan.domain.repository

import com.williamtan.common.model.BreedEntity
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getCatBreedList(breedIdList: List<String>?): Flow<List<BreedEntity>>
    suspend fun searchByName(name: String): Flow<List<BreedEntity>>
}