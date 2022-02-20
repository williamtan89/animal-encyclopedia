package com.williamtan.animalencyclopedia.breeddetail.di

import com.williamtan.animalencyclopedia.breeddetail.mapper.BreedDetailMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class MapperModule {
    @Provides
    @ViewModelScoped
    fun provideBreedDetailMapper() = BreedDetailMapper()
}