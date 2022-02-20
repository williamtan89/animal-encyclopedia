package com.williamtan.animalencyclopedia.cat.domain.di

import com.williamtan.animalencyclopedia.cat.domain.repository.CatRepository
import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedById
import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedByName
import com.williamtan.animalencyclopedia.cat.domain.usecase.GetCatBreedList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CatUseCaseModule {
    @Provides
    fun provideGetCatBreedList(
        repository: CatRepository
    ) = GetCatBreedList(repository)

    @Provides
    fun provideGetCatBreedByName(
        repository: CatRepository
    ) = GetCatBreedByName(repository)

    @Provides
    fun provideGetCatBreedById(
        repository: CatRepository
    ) = GetCatBreedById(repository)
}