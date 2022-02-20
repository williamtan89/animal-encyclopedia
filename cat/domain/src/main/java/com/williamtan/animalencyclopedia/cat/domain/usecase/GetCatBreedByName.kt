package com.williamtan.animalencyclopedia.cat.domain.usecase

import com.williamtan.animalencyclopedia.cat.domain.repository.CatRepository

class GetCatBreedByName(
    private val repository: CatRepository
) {
    suspend operator fun invoke(name: String) = repository.searchByName(name)
}