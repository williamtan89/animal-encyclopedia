package com.williamtan.animalencyclopedia.dog.domain.usecase

import com.williamtan.animalencyclopedia.dog.domain.repository.DogRepository

class GetDogBreedById(
    private val repository: DogRepository
) {
    suspend operator fun invoke(breedId: String) = repository.getBreedById(breedId)
}