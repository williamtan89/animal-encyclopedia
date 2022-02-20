package com.williamtan.cat.data.di

import com.williamtan.cat.data.CatApi
import com.williamtan.cat.data.mapper.CatBreedMapper
import com.williamtan.animalencyclopedia.cat.domain.repository.CatRepository
import com.williamtan.cat.data.repository.CatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCatRepository(
        catApi: CatApi,
        catBreedMapper: CatBreedMapper
    ): CatRepository = CatRepositoryImpl(
        catApi,
        catBreedMapper
    )
}