package com.williamtan.animalencyclopedia.cat.domain.usecase

import com.williamtan.animalencyclopedia.cat.domain.repository.CatRepository

class GetCatBreedById(
    private val repository: CatRepository
) {
    suspend operator fun invoke(breedId: String) = repository.getBreedById(breedId)
}