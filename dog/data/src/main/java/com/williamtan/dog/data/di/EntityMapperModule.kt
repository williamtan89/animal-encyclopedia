package com.williamtan.dog.data.di

import com.williamtan.dog.data.mapper.BreedMapper
import com.williamtan.dog.data.mapper.BreedMapperImpl
import com.williamtan.common.enumtype.AnimalType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EntityMapperModule {
    @Provides
    @Singleton
    fun provideBreedMapper(): BreedMapper = BreedMapperImpl(AnimalType.Dog)
}