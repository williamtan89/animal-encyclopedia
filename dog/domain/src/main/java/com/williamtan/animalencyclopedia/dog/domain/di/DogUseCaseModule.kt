package com.williamtan.animalencyclopedia.dog.domain.di

import com.williamtan.animalencyclopedia.dog.domain.repository.DogRepository
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedById
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedByName
import com.williamtan.animalencyclopedia.dog.domain.usecase.GetDogBreedList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DogUseCaseModule {
    @Provides
    fun provideGetDogBreedList(
        repository: DogRepository
    ) = GetDogBreedList(repository)

    @Provides
    fun provideGetDogBreedByName(
        repository: DogRepository
    ) = GetDogBreedByName(repository)

    @Provides
    fun provideGetDogBreedById(
        repository: DogRepository
    ) = GetDogBreedById(repository)
}