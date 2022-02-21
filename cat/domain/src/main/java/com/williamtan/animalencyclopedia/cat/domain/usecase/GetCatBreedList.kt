package com.williamtan.animalencyclopedia.cat.domain.usecase

import com.williamtan.animalencyclopedia.cat.domain.repository.CatRepository
import com.williamtan.common.entity.AnimalBreedEntity
import kotlinx.coroutines.flow.Flow

class GetCatBreedList(
    private val repository: CatRepository
) {
    suspend operator fun invoke(
        breedIdList: List<String>?,
        limit: Int,
        page: Int
    ): Flow<List<AnimalBreedEntity>> = repository.getBreedList(breedIdList, limit, page)
}