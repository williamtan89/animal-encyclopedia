package com.williamtan.animalencyclopedia.cat.domain.breed.usecase

import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedByName
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedByName
import com.williamtan.common.entity.AnimalBreedEntity
import com.williamtan.common.enumtype.AnimalType
import kotlinx.coroutines.flow.Flow

class SearchBreedsByName(
    private val getCatBreedByName: GetCatBreedByName,
    private val getDogBreedByName: GetDogBreedByName
) {
    suspend operator fun invoke(
        animalType: AnimalType,
        name: String
    ): Flow<List<AnimalBreedEntity>> = when (animalType) {
        AnimalType.Cat -> getCatBreedByName(name)
        AnimalType.Dog -> getDogBreedByName(name)

        else -> throw Exception("AnimalType $animalType not supported")
    }
}