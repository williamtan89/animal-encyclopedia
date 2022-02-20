package com.williamtan.animalencyclopedia.promoted.di

import com.williamtan.animalencyclopedia.promoted.mapper.PromotedBreedMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class PromotedModule {
    @Provides
    @ViewModelScoped
    fun providePromotedBreedMapper() = PromotedBreedMapper()
}