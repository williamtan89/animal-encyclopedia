package com.williamtan.animalencyclopedia.dog.domain.usecase

import com.williamtan.animalencyclopedia.dog.domain.repository.DogRepository
import com.williamtan.common.entity.AnimalBreedEntity
import kotlinx.coroutines.flow.Flow

class GetDogBreedList(
    private val repository: DogRepository
) {
    suspend operator fun invoke(
        breedIdList: List<String>?,
        limit: Int,
        page: Int
    ): Flow<List<AnimalBreedEntity>> = repository.getBreedList(breedIdList, limit, page)
}