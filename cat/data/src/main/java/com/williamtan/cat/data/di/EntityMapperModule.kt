package com.williamtan.cat.data.di

import com.williamtan.cat.data.mapper.BreedMapper
import com.williamtan.cat.data.mapper.BreedMapperImpl
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
    fun provideBreedMapper(): BreedMapper = BreedMapperImpl()
}