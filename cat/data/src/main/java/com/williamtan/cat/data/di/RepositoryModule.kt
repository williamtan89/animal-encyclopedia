package com.williamtan.cat.data.di

import com.williamtan.cat.data.CatApi
import com.williamtan.cat.data.mapper.BreedMapper
import com.williamtan.domain.repository.CatRepository
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
        breedMapper: BreedMapper
    ): CatRepository = CatRepositoryImpl(
        catApi,
        breedMapper
    )
}