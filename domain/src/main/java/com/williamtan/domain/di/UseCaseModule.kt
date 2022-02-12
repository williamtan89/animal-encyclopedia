package com.williamtan.domain.di

import com.williamtan.domain.repository.CatRepository
import com.williamtan.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetRecentBreeds(
        catRepository: CatRepository
    ): GetAnimalTypeWithRecentBreeds = GetAnimalTypeWithRecentBreedsImpl(catRepository)

    @Provides
    @Singleton
    fun provideSearchBreedsByName(
        catRepository: CatRepository
    ): SearchBreedsByName = SearchBreedsByNameImpl(catRepository)

    @Provides
    @Singleton
    fun provideGetAnimalTypeList(): GetAnimalTypeList = GetAnimalTypeListImpl()
}