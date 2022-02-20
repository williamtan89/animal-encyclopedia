package com.williamtan.cat.data.repository

import com.williamtan.animalencyclopedia.cat.domain.model.CatBreedEntity
import com.williamtan.animalencyclopedia.cat.domain.repository.CatRepository
import com.williamtan.cat.data.CatApi
import com.williamtan.cat.data.mapper.CatBreedMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CatRepositoryImpl(
    private val catApi: CatApi,
    private val catBreedMapper: CatBreedMapper
) : CatRepository {
    override suspend fun getBreedList(
        breedIdList: List<String>?,
        limit: Int,
        page: Int
    ): Flow<List<CatBreedEntity>> {
        return flow {
            emit(
                catApi.searchByBreed(
                    breedIds = breedIdList?.joinToString(","),
                    limit = limit,
                    page = page
                ).map(catBreedMapper::mapTo)
            )
        }
    }

    override suspend fun searchByName(name: String): Flow<List<CatBreedEntity>> {
        return flow {
            emit(
                catApi.searchByName(name).map(catBreedMapper::mapTo)
            )
        }
    }

    override suspend fun getBreedById(breedId: String): Flow<CatBreedEntity?> {
        return flow {
            emit(
                catApi.getBreedById(breedId)?.let {
                    catBreedMapper.mapTo(it)
                }
            )
        }
    }
}