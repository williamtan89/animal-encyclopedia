package com.williamtan.dog.data.repository

import com.williamtan.common.entity.BreedEntity
import com.williamtan.dog.data.DogApi
import com.williamtan.dog.data.mapper.BreedMapper
import com.williamtan.domain.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class DogRepositoryImpl(
    private val dogApi: DogApi,
    private val breedMapper: BreedMapper
) : DogRepository {
    override suspend fun getDogBreedList(
        breedIdList: List<String>?,
        limit: Int,
        page: Int
    ): Flow<List<BreedEntity>> {
        return flow {
            emit(
                dogApi.searchByBreed(
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
                dogApi.searchByName(name).map(breedMapper::mapTo)
            )
        }
    }

    override suspend fun getDogBreedById(breedId: String): Flow<BreedEntity?> {
        return flow {
            emit(
                dogApi.getBreedById(breedId)?.let {
                    breedMapper.mapTo(it)
                }
            )
        }
    }
}