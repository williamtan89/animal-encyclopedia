package com.williamtan.domain.usecase.animaltype

import com.williamtan.common.entity.BreedEntity
import com.williamtan.common.enumtype.AnimalType
import com.williamtan.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class GetAnimalTypeWithPromotedBreeds(
    private val catRepository: CatRepository
) {
    suspend operator fun invoke(
        animalType: AnimalType,
        breedIdList: List<String>? = null
    ): Flow<List<BreedEntity>> = when (animalType) {
        AnimalType.Cat -> catRepository.getCatBreedList(
            breedIdList,
            10,
            0
        )
        else -> throw Exception("AnimalType $animalType not supported")
    }
}