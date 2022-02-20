package com.williamtan.animalencyclopedia.cat.domain.animaltype.usecase

import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedList
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedList
import com.williamtan.common.entity.AnimalBreedEntity
import com.williamtan.common.enumtype.AnimalType
import kotlinx.coroutines.flow.Flow

class GetAnimalTypeWithPromotedBreeds(
    private val getCatBreedList: GetCatBreedList,
    private val getDogBreedList: GetDogBreedList
) {
    suspend operator fun invoke(
        animalType: AnimalType
    ): Flow<List<AnimalBreedEntity>> = when (animalType) {
        AnimalType.Cat -> getCatBreedList(null, 10, 0)
        AnimalType.Dog -> getDogBreedList(null, 10, 0)
        else -> throw Exception("AnimalType $animalType not supported")
    }
}