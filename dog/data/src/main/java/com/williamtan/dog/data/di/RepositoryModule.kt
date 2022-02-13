package com.williamtan.dog.data.di

import com.williamtan.dog.data.DogApi
import com.williamtan.dog.data.mapper.BreedMapper
import com.williamtan.domain.repository.CatRepository
import com.williamtan.dog.data.repository.DogRepositoryImpl
import com.williamtan.domain.repository.DogRepository
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
    fun provideDogRepository(
        dogApi: DogApi,
        breedMapper: BreedMapper
    ): DogRepository = DogRepositoryImpl(
        dogApi,
        breedMapper
    )
}