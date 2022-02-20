package com.williamtan.animalencyclopedia.dog.domain.usecase

import com.williamtan.animalencyclopedia.dog.domain.repository.DogRepository

class GetDogBreedByName(
    private val repository: DogRepository
) {
    suspend operator fun invoke(name: String) = repository.searchByName(name)
}