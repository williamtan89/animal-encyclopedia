package com.williamtan.dog.data.repository

import com.williamtan.animalencyclopedia.dog.domain.model.DogBreedEntity
import com.williamtan.animalencyclopedia.dog.domain.repository.DogRepository
import com.williamtan.dog.data.DogApi
import com.williamtan.dog.data.mapper.BreedMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class DogRepositoryImpl(
    private val dogApi: DogApi,
    private val breedMapper: BreedMapper
) : DogRepository {
    override suspend fun getBreedList(
        breedIdList: List<String>?,
        limit: Int,
        page: Int
    ): Flow<List<DogBreedEntity>> {
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

    override suspend fun searchByName(name: String): Flow<List<DogBreedEntity>> {
        return flow {
            emit(
                dogApi.searchByName(name).map(breedMapper::mapTo)
            )
        }
    }

    override suspend fun getBreedById(breedId: String): Flow<DogBreedEntity?> {
        return flow {
            emit(
                dogApi.getBreedById(breedId)?.let {
                    breedMapper.mapTo(it)
                }
            )
        }
    }
}