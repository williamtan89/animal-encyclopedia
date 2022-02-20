package com.williamtan.animalencyclopedia.cat.domain.usecase

import com.williamtan.animalencyclopedia.cat.domain.repository.CatRepository

class GetCatBreedList(
    private val repository: CatRepository
) {
    suspend operator fun invoke(breedIdList: List<String>?, limit: Int, page: Int) =
        repository.getBreedList(breedIdList, limit, page)
}