package com.williamtan.animalencyclopedia.dog.domain.repository

import com.williamtan.animalencyclopedia.dog.domain.model.DogBreedEntity
import kotlinx.coroutines.flow.Flow

interface DogRepository {
    suspend fun getBreedList(
        breedIdList: List<String>?,
        limit: Int,
        page: Int
    ): Flow<List<DogBreedEntity>>

    suspend fun searchByName(name: String): Flow<List<DogBreedEntity>>
    suspend fun getBreedById(breedId: String): Flow<DogBreedEntity?>
}