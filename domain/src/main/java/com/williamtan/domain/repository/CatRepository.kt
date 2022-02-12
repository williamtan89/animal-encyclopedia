package com.williamtan.domain.repository

import com.williamtan.common.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getCatBreedList(
        breedIdList: List<String>?,
        limit: Int,
        page: Int
    ): Flow<List<BreedEntity>>

    suspend fun searchByName(name: String): Flow<List<BreedEntity>>
}