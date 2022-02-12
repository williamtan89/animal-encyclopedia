package com.williamtan.cat.data.repository

import com.williamtan.cat.data.CatApi
import com.williamtan.cat.data.mapper.BreedMapper
import com.williamtan.common.model.BreedEntity
import com.williamtan.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CatRepositoryImpl(
    private val catApi: CatApi,
    private val breedMapper: BreedMapper
) : CatRepository {
    override suspend fun getCatBreedList(breedIdList: List<String>?): Flow<List<BreedEntity>> {
        return flow {
            emit(
                catApi.searchByBreed(breedIdList?.joinToString(",")).map(breedMapper::mapTo)
            )
        }
    }

    override suspend fun searchByName(name: String): Flow<List<BreedEntity>> {
        return flow {
            emit(
                catApi.searchByName(name).map(breedMapper::mapTo)
            )
        }
    }
}