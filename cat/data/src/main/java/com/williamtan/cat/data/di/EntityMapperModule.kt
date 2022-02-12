package com.williamtan.cat.data.di

import com.williamtan.cat.data.mapper.BreedMapper
import com.williamtan.cat.data.mapper.BreedMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object EntityMapperModule {
    @Provides
    fun provideBreedMapper(): BreedMapper = BreedMapperImpl()
}