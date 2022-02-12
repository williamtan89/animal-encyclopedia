package com.williamtan.domain.di

import com.williamtan.domain.repository.CatRepository
import com.williamtan.domain.usecase.GetRecentBreedsImpl
import com.williamtan.domain.usecase.SearchBreedsByNameImpl
import com.williamtan.usecase.GetRecentBreeds
import com.williamtan.usecase.SearchBreedsByName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetRecentBreeds(
        catRepository: CatRepository
    ): GetRecentBreeds = GetRecentBreedsImpl(catRepository)

    @Provides
    fun provideSearchBreedsByName(
        catRepository: CatRepository
    ): SearchBreedsByName = SearchBreedsByNameImpl(catRepository)
}