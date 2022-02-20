package com.williamtan.animalencyclopedia.breed.di

import com.williamtan.animalencyclopedia.breed.mapper.BreedMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class BreedMapperModule {
    @Provides
    @ViewModelScoped
    fun provideBreedMapper() = BreedMapper()
}