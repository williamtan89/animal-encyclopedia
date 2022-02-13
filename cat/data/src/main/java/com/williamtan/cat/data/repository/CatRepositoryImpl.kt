package com.williamtan.cat.data.repository

import com.williamtan.cat.data.CatApi
import com.williamtan.cat.data.mapper.BreedMapper
import com.williamtan.common.entity.BreedEntity
import com.williamtan.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CatRepositoryImpl(
    private val catApi: CatApi,
    private val breedMapper: BreedMapper
) : CatRepository {
    override suspend fun getCatBreedList(
        breedIdList: List<String>?,
        limit: Int,
        page: Int
    ): Flow<List<BreedEntity>> {
        return flow {
            emit(
                catApi.searchByBreed(
                    breedIds = breedIdList?.joinToString(","),
                    limit = limit,
                    page = page
                ).map(breedMapper::mapTo)
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

    override suspend fun getCatBreedById(breedId: String): Flow<BreedEntity?> {
        return flow {
            emit(
                catApi.getBreedById(breedId)?.let {
                    breedMapper.mapTo(it)
                }
            )
        }
    }
}