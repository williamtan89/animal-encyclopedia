package com.williamtan.animalencyclopedia.dog.domain.usecase

import com.williamtan.animalencyclopedia.dog.domain.repository.DogRepository

class GetDogBreedList(
    private val repository: DogRepository
) {
    suspend operator fun invoke(breedIdList: List<String>?, limit: Int, page: Int) =
        repository.getBreedList(breedIdList, limit, page)
}